package fr.efrei.transfret.delai;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DelaiAirTest {

    @Test
    void un_jour_en_court_courrier() {
        assertEquals(1, new DelaiAir().jours(800, false));
    }

    @Test
    void deux_jours_au_dela_de_3000_km() {
        assertEquals(2, new DelaiAir().jours(5840, false));
    }

    @Test
    void un_jour_si_urgent() {
        assertEquals(1, new DelaiAir().jours(5840, true));
    }
}
