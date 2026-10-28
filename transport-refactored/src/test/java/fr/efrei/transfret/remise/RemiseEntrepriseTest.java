package fr.efrei.transfret.remise;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RemiseEntrepriseTest {

    @Test
    void dix_pourcent_jusqu_a_5000_euros() {
        assertEquals(4500, new RemiseEntreprise().appliquer(5000), 0.001);
    }

    @Test
    void quinze_pourcent_au_dela_de_5000_euros() {
        assertEquals(5100, new RemiseEntreprise().appliquer(6000), 0.001);
    }
}
