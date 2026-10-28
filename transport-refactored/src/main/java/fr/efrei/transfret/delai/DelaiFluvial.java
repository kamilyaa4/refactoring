package fr.efrei.transfret.delai;

/** distance / 360 arrondi au supérieur, + 1 jour. */
public class DelaiFluvial implements DelaiTransport {

    private static final double KM_PAR_JOUR = 360.0;
    private static final int JOURS_EN_PLUS = 1;

    @Override
    public int jours(int distance, boolean urgent) {
        return (int) Math.ceil(distance / KM_PAR_JOUR) + JOURS_EN_PLUS;
    }
}
