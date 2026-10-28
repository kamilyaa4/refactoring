package fr.efrei.transfret;

import fr.efrei.transfret.vehicule.Vehicule;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class ServiceExpedition {

    private static final double FRAIS_ANNULATION_EN_COURS = 0.5;

    private final ExpeditionRepository expeditions;
    private final CalculateurPrix calculateur;
    private final AffectationVehicule affectation;
    private final ValidateurExpedition validateur;
    private final EstimateurDelai estimateurDelai;
    private final Notificateur notificateur;
    private final TableDistances distances;
    private final GenerateurIdentifiant identifiants;

    public ServiceExpedition(ExpeditionRepository expeditions, CalculateurPrix calculateur,
                             AffectationVehicule affectation, ValidateurExpedition validateur,
                             EstimateurDelai estimateurDelai, Notificateur notificateur,
                             TableDistances distances, GenerateurIdentifiant identifiants) {
        this.expeditions = expeditions;
        this.calculateur = calculateur;
        this.affectation = affectation;
        this.validateur = validateur;
        this.estimateurDelai = estimateurDelai;
        this.notificateur = notificateur;
        this.distances = distances;
        this.identifiants = identifiants;
    }

    public Optional<Expedition> trouver(String id) {
        return expeditions.trouver(id);
    }

    public Expedition creerExpedition(Client client, List<Colis> colis, String villeDepart, String villeArrivee, ModeTransport mode, boolean urgent, boolean assurance) {
        Optional<String> refus = validateur.valider(client, colis, mode, urgent);
        if (refus.isPresent()) {
            System.out.println("ERREUR : " + refus.get());
            return null;
        }
        OptionalInt trajet = distances.distance(villeDepart, villeArrivee);
        if (trajet.isEmpty()) {
            System.out.println("ERREUR : trajet inconnu " + villeDepart + " -> " + villeArrivee);
            return null;
        }
        int distance = trajet.getAsInt();

        Vehicule v = affectation.trouverVehicule(colis, mode).orElse(null);
        if (v == null) {
            System.out.println("ERREUR : aucun véhicule disponible pour cette expédition");
            return null;
        }
        for (Colis c : colis) {
            v.charger(c);
        }

        Expedition e = new Expedition();
        e.setId(identifiants.suivant());
        e.setClient(client);
        e.setColis(colis);
        e.setVilleDepart(villeDepart);
        e.setVilleArrivee(villeArrivee);
        e.setDistance(distance);
        e.setMode(mode);
        e.setUrgent(urgent);
        e.setAssurance(assurance);
        e.setStatut(StatutExpedition.CREEE);
        e.setVehicule(v);
        e.setPrixHT(calculateur.calculerPrixHT(new DemandeTarif(colis, distance, mode, urgent, assurance, client.getType())));
        e.setPrixTTC(Montants.ttc(e.getPrixHT()));
        e.setDateCreation(new Date());
        Calendar cal = Calendar.getInstance();
        cal.setTime(e.getDateCreation());
        cal.add(Calendar.DAY_OF_MONTH, estimateurDelai.estimer(distance, mode, urgent));
        e.setDateLivraisonPrevue(cal.getTime());

        expeditions.sauvegarder(e);
        notificateur.notifier(client, new Message("Confirmation de votre expédition " + e.getId(),
                "Votre expédition " + villeDepart + " -> " + villeArrivee + " (" + mode + ") a été enregistrée. Montant : " + e.getPrixTTC() + " € TTC.",
                "TransFret : expédition " + e.getId() + " créée, livraison prévue le " + Dates.formater(e.getDateLivraisonPrevue())));
        return e;
    }

    // retourne le prix TTC, -1 si le trajet est inconnu
    public double calculerDevis(List<Colis> colis, String villeDepart, String villeArrivee, ModeTransport mode, boolean urgent, boolean assurance, TypeClient typeClient) {
        OptionalInt distance = distances.distance(villeDepart, villeArrivee);
        if (distance.isEmpty()) {
            return -1;
        }
        double ht = calculateur.calculerPrixHT(new DemandeTarif(colis, distance.getAsInt(), mode, urgent, assurance, typeClient));
        return Montants.ttc(ht);
    }

    public boolean demarrerExpedition(String id) {
        Expedition e = expeditions.trouver(id).orElse(null);
        if (e == null || e.getStatut() != StatutExpedition.CREEE) {
            System.out.println("ERREUR : impossible de démarrer l'expédition " + id);
            return false;
        }
        e.setStatut(StatutExpedition.EN_COURS);
        Vehicule v = e.getVehicule();
        v.setDisponible(false);
        v.partir(e.getDistance());
        notificateur.notifier(e.getClient(), new Message("Votre expédition " + e.getId() + " est en route",
                "Votre expédition a quitté " + e.getVilleDepart() + ".",
                "TransFret : expédition " + e.getId() + " partie de " + e.getVilleDepart()));
        return true;
    }

    public boolean livrerExpedition(String id) {
        Expedition e = expeditions.trouver(id).orElse(null);
        if (e == null || e.getStatut() != StatutExpedition.EN_COURS) {
            System.out.println("ERREUR : impossible de livrer l'expédition " + id);
            return false;
        }
        e.setStatut(StatutExpedition.LIVREE);
        libererVehicule(e);
        notificateur.notifier(e.getClient(), new Message("Votre expédition " + e.getId() + " est livrée",
                "Votre expédition est arrivée à " + e.getVilleArrivee() + ". Merci de votre confiance.",
                "TransFret : expédition " + e.getId() + " livrée à " + e.getVilleArrivee()));
        return true;
    }

    public boolean annulerExpedition(String id) {
        Expedition e = expeditions.trouver(id).orElse(null);
        if (e == null || e.getStatut() == StatutExpedition.LIVREE || e.getStatut() == StatutExpedition.ANNULEE) {
            System.out.println("ERREUR : impossible d'annuler l'expédition " + id);
            return false;
        }
        if (e.getStatut() == StatutExpedition.EN_COURS) {
            // 50% de frais si déjà partie
            e.setFraisAnnulation(Montants.arrondir(e.getPrixTTC() * FRAIS_ANNULATION_EN_COURS));
        }
        e.setStatut(StatutExpedition.ANNULEE);
        libererVehicule(e);
        notificateur.notifier(e.getClient(), new Message("Annulation de votre expédition " + e.getId(),
                "Votre expédition a été annulée. Frais d'annulation : " + e.getFraisAnnulation() + " €",
                "TransFret : expédition " + e.getId() + " annulée"));
        return true;
    }

    private void libererVehicule(Expedition e) {
        for (Colis c : e.getColis()) {
            e.getVehicule().decharger(c);
        }
        e.getVehicule().setDisponible(true);
    }
}
