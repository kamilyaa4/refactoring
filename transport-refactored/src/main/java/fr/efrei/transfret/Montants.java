package fr.efrei.transfret;

/** Fonctions de calcul sur les montants en euros. */
public final class Montants {

    public static final double TAUX_TVA = 0.20;

    private Montants() {
    }

    /** Arrondit au centime. */
    public static double arrondir(double montant) {
        return Math.round(montant * 100.0) / 100.0;
    }

    public static double ttc(double ht) {
        return arrondir(ht * (1 + TAUX_TVA));
    }
}
