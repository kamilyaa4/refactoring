package fr.efrei.transfret.tarif;

import fr.efrei.transfret.DemandeTarif;

public class TarifMer implements TarifTransport {

    private static final double PRIX_KM = 0.5;
    private static final double PRIX_KG = 0.03;
    private static final double MINIMUM = 500;

    @Override
    public double prixDeBase(DemandeTarif demande) {
        return Math.max(MINIMUM, demande.distance() * PRIX_KM + demande.poidsTotal() * PRIX_KG);
    }
}
