package fr.efrei.transfret.tarif;

import fr.efrei.transfret.ModeTransport;
import fr.efrei.transfret.TypeMarchandise;
import org.junit.jupiter.api.Test;

import static fr.efrei.transfret.Demandes.colis;
import static fr.efrei.transfret.Demandes.demande;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TarifRailTest {

    @Test
    void tarif_au_km_et_au_kg() {
        double prix = new TarifRail().prixDeBase(demande(ModeTransport.RAIL, 500, colis(1000, TypeMarchandise.STANDARD)));
        assertEquals(500 * 0.8 + 1000 * 0.07, prix, 0.001);
    }

    @Test
    void minimum_150_euros() {
        assertEquals(150, new TarifRail().prixDeBase(demande(ModeTransport.RAIL, 10, colis(1, TypeMarchandise.STANDARD))), 0.001);
    }
}
