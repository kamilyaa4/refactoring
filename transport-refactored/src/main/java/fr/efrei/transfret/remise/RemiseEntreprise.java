package fr.efrei.transfret.remise;

/** 10 %, ou 15 % au-delà de 5 000 €. */
public class RemiseEntreprise implements PolitiqueRemise {

    private static final double SEUIL_GROS_MONTANT = 5000;
    private static final double COEF_GROS_MONTANT = 0.85;
    private static final double COEF = 0.90;

    @Override
    public double appliquer(double prix) {
        return prix * (prix > SEUIL_GROS_MONTANT ? COEF_GROS_MONTANT : COEF);
    }
}
