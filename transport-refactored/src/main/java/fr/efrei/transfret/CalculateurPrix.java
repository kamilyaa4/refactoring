package fr.efrei.transfret;

import fr.efrei.transfret.remise.PolitiqueRemise;
import fr.efrei.transfret.tarif.TarifTransport;

import java.util.Map;

public class CalculateurPrix {

    private static final double MAJORATION_FRAGILE = 1.15;
    private static final double MAJORATION_DANGEREUX = 1.30;
    private static final double MAJORATION_REFRIGERE = 1.20;
    private static final double MAJORATION_URGENCE = 1.5;
    private static final double ASSURANCE_TAUX = 0.02;
    private static final double ASSURANCE_MINIMUM = 15;

    private final Map<ModeTransport, TarifTransport> tarifs;
    private final Map<TypeClient, PolitiqueRemise> remises;

    public CalculateurPrix(Map<ModeTransport, TarifTransport> tarifs, Map<TypeClient, PolitiqueRemise> remises) {
        this.tarifs = Map.copyOf(tarifs);
        this.remises = Map.copyOf(remises);
    }

    public double calculerPrixHT(DemandeTarif d) {
        double prix = tarifs.get(d.mode()).prixDeBase(d);

        if (d.contient(TypeMarchandise.FRAGILE)) prix = prix * MAJORATION_FRAGILE;
        if (d.contient(TypeMarchandise.DANGEREUX)) prix = prix * MAJORATION_DANGEREUX;
        if (d.contient(TypeMarchandise.REFRIGERE)) prix = prix * MAJORATION_REFRIGERE;
        if (d.urgent()) prix = prix * MAJORATION_URGENCE;

        prix = remises.get(d.typeClient()).appliquer(prix);

        if (d.assurance()) {
            prix = prix + Math.max(ASSURANCE_MINIMUM, d.valeurTotale() * ASSURANCE_TAUX);
        }
        return Montants.arrondir(prix);
    }
}
