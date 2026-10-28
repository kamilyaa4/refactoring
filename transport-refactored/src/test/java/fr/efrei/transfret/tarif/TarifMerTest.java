package fr.efrei.transfret.tarif;

import fr.efrei.transfret.ModeTransport;
import fr.efrei.transfret.TypeMarchandise;
import org.junit.jupiter.api.Test;

import static fr.efrei.transfret.Demandes.colis;
import static fr.efrei.transfret.Demandes.demande;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TarifMerTest {

    @Test
    void tarif_au_km_et_au_kg() {
        double prix = new TarifMer().prixDeBase(demande(ModeTransport.MER, 5000, colis(10000, TypeMarchandise.STANDARD)));
        assertEquals(5000 * 0.5 + 10000 * 0.03, prix, 0.001);
    }

    @Test
    void minimum_500_euros() {
        assertEquals(500, new TarifMer().prixDeBase(demande(ModeTransport.MER, 100, colis(10, TypeMarchandise.STANDARD))), 0.001);
    }
}
