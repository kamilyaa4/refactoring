package fr.efrei.transfret.delai;

/** distance / 1 440 arrondi au supérieur, + 1 jour (− 1 si urgent). */
public class DelaiRail implements DelaiTransport {

    private static final double KM_PAR_JOUR = 1440.0;
    private static final int JOURS_EN_PLUS = 1;

    @Override
    public int jours(int distance, boolean urgent) {
        return (int) Math.ceil(distance / KM_PAR_JOUR) + JOURS_EN_PLUS - (urgent ? 1 : 0);
    }
}
