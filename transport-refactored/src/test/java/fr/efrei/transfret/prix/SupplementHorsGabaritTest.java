package fr.efrei.transfret.prix;

import fr.efrei.transfret.Colis;
import fr.efrei.transfret.DemandeTarif;
import fr.efrei.transfret.ModeTransport;
import fr.efrei.transfret.TypeClient;
import fr.efrei.transfret.TypeMarchandise;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SupplementHorsGabaritTest {

    private static final CalculPrix MILLE_EUROS = demande -> 1000;

    private static DemandeTarif demande(double longueur, double largeur, double hauteur) {
        Colis colis = new Colis("colis", 100, longueur, largeur, hauteur, TypeMarchandise.STANDARD, 100);
        return new DemandeTarif(List.of(colis), 100, ModeTransport.ROUTE, false, false, TypeClient.PARTICULIER);
    }

    @Test
    void plus_10_pourcent_si_une_dimension_depasse_200_cm() {
        assertEquals(1100, new SupplementHorsGabarit(MILLE_EUROS).calculer(demande(240, 120, 150)), 0.001);
        assertEquals(1100, new SupplementHorsGabarit(MILLE_EUROS).calculer(demande(100, 100, 201)), 0.001);
    }

    @Test
    void rien_jusqu_a_200_cm() {
        assertEquals(1000, new SupplementHorsGabarit(MILLE_EUROS).calculer(demande(200, 200, 200)), 0.001);
    }
}
