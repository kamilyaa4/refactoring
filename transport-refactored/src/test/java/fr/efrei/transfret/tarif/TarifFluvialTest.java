package fr.efrei.transfret.tarif;

import fr.efrei.transfret.ModeTransport;
import fr.efrei.transfret.TypeMarchandise;
import org.junit.jupiter.api.Test;

import static fr.efrei.transfret.Demandes.colis;
import static fr.efrei.transfret.Demandes.demande;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TarifFluvialTest {

    @Test
    void quarante_centimes_du_km_et_deux_centimes_du_kg() {
        double prix = new TarifFluvial().prixDeBase(demande(ModeTransport.FLUVIAL, 365, colis(20000, TypeMarchandise.STANDARD)));
        assertEquals(365 * 0.40 + 20000 * 0.02, prix, 0.001);
    }

    @Test
    void minimum_300_euros() {
        assertEquals(300, new TarifFluvial().prixDeBase(demande(ModeTransport.FLUVIAL, 365, colis(10, TypeMarchandise.STANDARD))), 0.001);
    }
}
