package fr.efrei.transfret.delai;

/** 1 jour, 2 au-delà de 3 000 km, 1 si urgent. */
public class DelaiAir implements DelaiTransport {

    private static final int SEUIL_LONG_COURRIER_KM = 3000;

    @Override
    public int jours(int distance, boolean urgent) {
        return (urgent || distance <= SEUIL_LONG_COURRIER_KM) ? 1 : 2;
    }
}
