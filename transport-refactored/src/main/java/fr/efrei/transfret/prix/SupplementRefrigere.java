package fr.efrei.transfret.prix;

import fr.efrei.transfret.TypeMarchandise;

/** +20 % si au moins un colis est réfrigéré. */
public class SupplementRefrigere extends SupplementMarchandise {

    private static final double COEFFICIENT = 1.20;

    public SupplementRefrigere(CalculPrix enveloppe) {
        super(enveloppe, TypeMarchandise.REFRIGERE, COEFFICIENT);
    }
}
