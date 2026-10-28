package fr.efrei.transfret;

import fr.efrei.transport.contrat.Canal;
import fr.efrei.transport.contrat.ColisDto;
import fr.efrei.transport.contrat.DemandeClient;
import fr.efrei.transport.contrat.DemandeDevis;
import fr.efrei.transport.contrat.DemandeExpedition;
import fr.efrei.transport.contrat.EtatExpedition;
import fr.efrei.transport.contrat.Notification;
import fr.efrei.transport.contrat.Statut;
import fr.efrei.transport.contrat.SystemeTransport;

import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 * Adaptateur entre l'interface neutre des tests de contrat et l'implémentation refactorée.
 * Les tests ne dépendent ainsi pas des classes internes de TransFret.
 */
public class NouveauSystemeAdapter implements SystemeTransport {

    private TransFret application;

    @Override
    public void reinitialiser() {
        // Une nouvelle application recrée les dépôts, les canaux de notification et les services.
        // false conserve les règles tarifaires d'origine utilisées par les tests de contrat.
        application = new TransFret(false);
    }

    @Override
    public Optional<String> creerClient(DemandeClient demande) {
        return application.serviceClient()
                .creerClient(demande.nom(), demande.email(), demande.telephone(), demande.adresse(),
                        TypeClient.valueOf(demande.type().name()), demande.siret())
                .map(Client::getEmail);
    }

    @Override
    public Optional<String> creerExpedition(DemandeExpedition demande) {
        Client client = application.clients().trouverParEmail(demande.refClient()).orElse(null);
        if (client == null) {
            return Optional.empty();
        }

        Expedition expedition = application.serviceExpedition().creerExpedition(
                client, versColis(demande.colis()), demande.villeDepart(), demande.villeArrivee(),
                ModeTransport.valueOf(demande.mode().name()), demande.urgent(), demande.assurance());

        return Optional.ofNullable(expedition).map(Expedition::getId);
    }

    @Override
    public Optional<EtatExpedition> consulter(String id) {
        return application.serviceExpedition().trouver(id).map(e -> {
            int delai = (int) ChronoUnit.DAYS.between(jour(e.getDateCreation()), jour(e.getDateLivraisonPrevue()));
            return new EtatExpedition(e.getId(), Statut.valueOf(e.getStatut().name()),
                    e.getPrixHT(), e.getPrixTTC(), delai,
                    e.getVehicule().getImmatriculation(), e.getFraisAnnulation());
        });
    }

    @Override
    public OptionalDouble devisTTC(DemandeDevis demande) {
        double devis = application.serviceExpedition().calculerDevis(
                versColis(demande.colis()), demande.villeDepart(), demande.villeArrivee(),
                ModeTransport.valueOf(demande.mode().name()), demande.urgent(), demande.assurance(),
                TypeClient.valueOf(demande.typeClient().name()));
        return devis < 0 ? OptionalDouble.empty() : OptionalDouble.of(devis);
    }

    @Override
    public boolean demarrer(String id) {
        return application.serviceExpedition().demarrerExpedition(id);
    }

    @Override
    public boolean livrer(String id) {
        return application.serviceExpedition().livrerExpedition(id);
    }

    @Override
    public boolean annuler(String id) {
        return application.serviceExpedition().annulerExpedition(id);
    }

    @Override
    public Optional<String> facture(String id) {
        return application.serviceExpedition().trouver(id).map(new GenerateurFacture()::generer);
    }

    @Override
    public List<Notification> notifications() {
        List<Notification> resultat = new ArrayList<>();
        for (MessageEnvoye message : application.email().historique()) {
            resultat.add(new Notification(Canal.EMAIL, message.destinataire(), message.objet()));
        }
        for (MessageEnvoye message : application.sms().historique()) {
            resultat.add(new Notification(Canal.SMS, message.destinataire(), message.objet()));
        }
        return resultat;
    }

    private static java.time.LocalDate jour(java.util.Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private static List<Colis> versColis(List<ColisDto> colis) {
        return colis.stream().map(NouveauSystemeAdapter::versColis).collect(Collectors.toCollection(ArrayList::new));
    }

    private static Colis versColis(ColisDto colis) {
        return new Colis(colis.description(), colis.poidsKg(), colis.longueurCm(), colis.largeurCm(),
                colis.hauteurCm(), TypeMarchandise.valueOf(colis.type().name()), colis.valeurDeclaree());
    }
}
