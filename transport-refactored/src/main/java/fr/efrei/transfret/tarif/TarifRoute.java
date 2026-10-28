package fr.efrei.transfret.tarif;

import fr.efrei.transfret.DemandeTarif;

public class TarifRoute implements TarifTransport {

    private static final double PRIX_KM = 1.2;
    private static final double PRIX_KG = 0.10;

    @Override
    public double prixDeBase(DemandeTarif demande) {
        return demande.distance() * PRIX_KM + demande.poidsTotal() * PRIX_KG;
    }
}
