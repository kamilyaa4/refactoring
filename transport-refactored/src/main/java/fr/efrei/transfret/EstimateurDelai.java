package fr.efrei.transfret;

/** Estime le délai de livraison, en jours. */
public class EstimateurDelai {

    private static final double ROUTE_VITESSE_KMH = 70.0;
    private static final double ROUTE_HEURES_PAR_JOUR = 9;
    private static final double ROUTE_HEURES_PAR_JOUR_URGENT = 18;
    private static final double RAIL_KM_PAR_JOUR = 1440.0;
    private static final int RAIL_JOURS_SUPPLEMENTAIRES = 1;
    private static final double MER_KM_PAR_JOUR = 720.0;
    private static final int MER_JOURS_SUPPLEMENTAIRES = 2;
    private static final int AIR_SEUIL_LONG_COURRIER_KM = 3000;
    private static final int DELAI_MINIMUM_JOURS = 1;

    public int estimer(int distance, ModeTransport mode, boolean urgent) {
        int jours = switch (mode) {
            case ROUTE -> {
                double heures = distance / ROUTE_VITESSE_KMH;
                yield (int) Math.ceil(heures / (urgent ? ROUTE_HEURES_PAR_JOUR_URGENT : ROUTE_HEURES_PAR_JOUR));
            }
            case RAIL -> (int) Math.ceil(distance / RAIL_KM_PAR_JOUR) + RAIL_JOURS_SUPPLEMENTAIRES - (urgent ? 1 : 0);
            case MER -> (int) Math.ceil(distance / MER_KM_PAR_JOUR) + MER_JOURS_SUPPLEMENTAIRES;
            case AIR -> (urgent || distance <= AIR_SEUIL_LONG_COURRIER_KM) ? 1 : 2;
        };
        return Math.max(DELAI_MINIMUM_JOURS, jours);
    }
}
