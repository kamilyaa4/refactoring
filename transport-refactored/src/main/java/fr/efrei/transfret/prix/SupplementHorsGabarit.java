package fr.efrei.transfret.prix;

import fr.efrei.transfret.Colis;
import fr.efrei.transfret.DemandeTarif;

/** +10 % si un colis dépasse 200 cm sur une de ses dimensions. */
public class SupplementHorsGabarit extends DecorateurPrix {

    private static final double DIMENSION_MAX_CM = 200;
    private static final double COEFFICIENT = 1.10;

    public SupplementHorsGabarit(CalculPrix enveloppe) {
        super(enveloppe);
    }

    @Override
    protected double ajuster(double prix, DemandeTarif demande) {
        return demande.colis().stream().anyMatch(SupplementHorsGabarit::horsGabarit) ? prix * COEFFICIENT : prix;
    }

    private static boolean horsGabarit(Colis c) {
        return Math.max(c.getLongueur(), Math.max(c.getLargeur(), c.getHauteur())) > DIMENSION_MAX_CM;
    }
}
