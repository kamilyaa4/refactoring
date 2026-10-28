package fr.efrei.transfret.tarif;

import fr.efrei.transfret.DemandeTarif;

public class TarifAir implements TarifTransport {

    private static final double PRIX_KM = 0.6;
    private static final double PRIX_KG_TAXABLE = 4.5;
    private static final double KG_PAR_M3 = 167;

    @Override
    public double prixDeBase(DemandeTarif demande) {
        return demande.distance() * PRIX_KM + poidsTaxable(demande) * PRIX_KG_TAXABLE;
    }

    /** Le plus grand du poids réel et du poids volumétrique. */
    double poidsTaxable(DemandeTarif demande) {
        return Math.max(demande.poidsTotal(), demande.volumeTotal() * KG_PAR_M3);
    }
}
