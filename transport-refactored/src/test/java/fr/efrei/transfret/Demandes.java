package fr.efrei.transfret;

import java.util.List;

/** Jeux de données pour les tests unitaires. */
public final class Demandes {

    private Demandes() {
    }

    public static Colis colis(double poidsKg, TypeMarchandise type) {
        return new Colis("colis", poidsKg, 50, 40, 30, type, 100);
    }

    public static DemandeTarif demande(ModeTransport mode, int distance, Colis... colis) {
        return new DemandeTarif(List.of(colis), distance, mode, false, false, TypeClient.PARTICULIER);
    }
}
