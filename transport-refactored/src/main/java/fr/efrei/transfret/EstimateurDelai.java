package fr.efrei.transfret;

import fr.efrei.transfret.delai.DelaiTransport;

import java.util.Map;

/** Estime le délai de livraison en déléguant au délai du mode choisi. */
public class EstimateurDelai {

    private static final int DELAI_MINIMUM_JOURS = 1;

    private final Map<ModeTransport, DelaiTransport> delais;

    public EstimateurDelai(Map<ModeTransport, DelaiTransport> delais) {
        this.delais = Map.copyOf(delais);
    }

    public int estimer(int distance, ModeTransport mode, boolean urgent) {
        return Math.max(DELAI_MINIMUM_JOURS, delais.get(mode).jours(distance, urgent));
    }
}
