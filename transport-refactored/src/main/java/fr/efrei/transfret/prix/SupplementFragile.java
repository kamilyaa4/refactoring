package fr.efrei.transfret.prix;

import fr.efrei.transfret.TypeMarchandise;

/** +15 % si au moins un colis est fragile. */
public class SupplementFragile extends SupplementMarchandise {

    private static final double COEFFICIENT = 1.15;

    public SupplementFragile(CalculPrix enveloppe) {
        super(enveloppe, TypeMarchandise.FRAGILE, COEFFICIENT);
    }
}
