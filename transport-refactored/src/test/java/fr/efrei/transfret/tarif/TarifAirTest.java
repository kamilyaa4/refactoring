package fr.efrei.transfret.tarif;

import fr.efrei.transfret.Colis;
import fr.efrei.transfret.ModeTransport;
import fr.efrei.transfret.TypeMarchandise;
import org.junit.jupiter.api.Test;

import static fr.efrei.transfret.Demandes.colis;
import static fr.efrei.transfret.Demandes.demande;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TarifAirTest {

    @Test
    void au_poids_reel_quand_il_depasse_le_poids_volumetrique() {
        // 50 x 40 x 30 cm = 0,06 m³ -> 10,02 kg volumétriques < 100 kg
        double prix = new TarifAir().prixDeBase(demande(ModeTransport.AIR, 1000, colis(100, TypeMarchandise.STANDARD)));
        assertEquals(1000 * 0.6 + 100 * 4.5, prix, 0.001);
    }

    @Test
    void au_poids_volumetrique_pour_un_colis_leger_et_encombrant() {
        Colis carton = new Colis("carton", 5, 100, 100, 100, TypeMarchandise.STANDARD, 50); // 1 m³ -> 167 kg
        double prix = new TarifAir().prixDeBase(demande(ModeTransport.AIR, 1000, carton));
        assertEquals(1000 * 0.6 + 167 * 4.5, prix, 0.001);
    }
}
