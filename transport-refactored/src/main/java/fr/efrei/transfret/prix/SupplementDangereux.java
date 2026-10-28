package fr.efrei.transfret.prix;

import fr.efrei.transfret.TypeMarchandise;

/** +30 % si au moins un colis est dangereux. */
public class SupplementDangereux extends SupplementMarchandise {

    private static final double COEFFICIENT = 1.30;

    public SupplementDangereux(CalculPrix enveloppe) {
        super(enveloppe, TypeMarchandise.DANGEREUX, COEFFICIENT);
    }
}
