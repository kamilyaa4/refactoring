package fr.efrei.transfret.prix;

import fr.efrei.transfret.DemandeTarif;
import fr.efrei.transfret.TypeMarchandise;

/** Majoration appliquée une fois si au moins un colis est du type concerné. */
public abstract class SupplementMarchandise extends DecorateurPrix {

    private final TypeMarchandise type;
    private final double coefficient;

    protected SupplementMarchandise(CalculPrix enveloppe, TypeMarchandise type, double coefficient) {
        super(enveloppe);
        this.type = type;
        this.coefficient = coefficient;
    }

    @Override
    protected double ajuster(double prix, DemandeTarif demande) {
        return demande.contient(type) ? prix * coefficient : prix;
    }
}
