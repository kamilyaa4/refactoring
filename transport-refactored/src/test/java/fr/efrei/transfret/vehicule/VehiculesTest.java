package fr.efrei.transfret.vehicule;

import fr.efrei.transfret.Colis;
import fr.efrei.transfret.ModeTransport;
import fr.efrei.transfret.TypeMarchandise;
import org.junit.jupiter.api.Test;

import java.util.List;

import static fr.efrei.transfret.Demandes.colis;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VehiculesTest {

    private static final List<Colis> STANDARD = List.of(colis(100, TypeMarchandise.STANDARD));
    private static final List<Colis> DANGEREUX = List.of(colis(100, TypeMarchandise.DANGEREUX));
    private static final List<Colis> REFRIGERE = List.of(colis(100, TypeMarchandise.REFRIGERE));

    @Test
    void un_vehicule_ne_sert_que_pour_son_mode() {
        assertTrue(new Train("T", 1000).peutPrendreEnCharge(STANDARD, ModeTransport.RAIL));
        assertFalse(new Train("T", 1000).peutPrendreEnCharge(STANDARD, ModeTransport.ROUTE));
        assertTrue(new Avion("A", 1000).peutPrendreEnCharge(STANDARD, ModeTransport.AIR));
    }

    @Test
    void camion_dangereux_seulement_si_certifie_adr() {
        assertFalse(new Camion("C", 1000, false).peutPrendreEnCharge(DANGEREUX, ModeTransport.ROUTE));
        assertTrue(new Camion("C", 1000, true).peutPrendreEnCharge(DANGEREUX, ModeTransport.ROUTE));
    }

    @Test
    void camion_frigorifique_reserve_au_refrigere() {
        CamionFrigorifique frigo = new CamionFrigorifique("F", 1000);
        assertTrue(frigo.peutPrendreEnCharge(REFRIGERE, ModeTransport.ROUTE));
        assertFalse(frigo.peutPrendreEnCharge(STANDARD, ModeTransport.ROUTE));
        assertFalse(new Camion("C", 1000, true).peutPrendreEnCharge(REFRIGERE, ModeTransport.ROUTE));
    }

    @Test
    void bateau_refrigere_seulement_avec_conteneurs_frigorifiques() {
        assertTrue(new Bateau("B", 1000, true).peutPrendreEnCharge(REFRIGERE, ModeTransport.MER));
        assertFalse(new Bateau("B", 1000, false).peutPrendreEnCharge(REFRIGERE, ModeTransport.MER));
    }

    @Test
    void capacite_restante_respectee() {
        Camion camion = new Camion("C", 150, false);
        assertTrue(camion.peutPrendreEnCharge(STANDARD, ModeTransport.ROUTE));
        camion.charger(STANDARD.get(0));
        assertFalse(camion.peutPrendreEnCharge(STANDARD, ModeTransport.ROUTE));
    }
}
