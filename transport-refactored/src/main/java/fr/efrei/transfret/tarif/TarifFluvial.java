package fr.efrei.transfret.tarif;

import fr.efrei.transfret.DemandeTarif;

public class TarifFluvial implements TarifTransport {

    private static final double PRIX_KM = 0.40;
    private static final double PRIX_KG = 0.02;
    private static final double MINIMUM = 300;

    @Override
    public double prixDeBase(DemandeTarif demande) {
        return Math.max(MINIMUM, demande.distance() * PRIX_KM + demande.poidsTotal() * PRIX_KG);
    }
}
