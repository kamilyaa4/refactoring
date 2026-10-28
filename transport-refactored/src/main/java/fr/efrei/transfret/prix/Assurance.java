package fr.efrei.transfret.prix;

import fr.efrei.transfret.DemandeTarif;

/** Si demandée : + 2 % de la valeur déclarée totale, minimum 15 €. */
public class Assurance extends DecorateurPrix {

    private static final double TAUX = 0.02;
    private static final double MINIMUM = 15;

    public Assurance(CalculPrix enveloppe) {
        super(enveloppe);
    }

    @Override
    protected double ajuster(double prix, DemandeTarif demande) {
        return demande.assurance() ? prix + Math.max(MINIMUM, demande.valeurTotale() * TAUX) : prix;
    }
}
