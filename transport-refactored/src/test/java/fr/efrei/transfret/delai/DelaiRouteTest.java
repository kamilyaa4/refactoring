package fr.efrei.transfret.delai;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DelaiRouteTest {

    @Test
    void neuf_heures_de_conduite_par_jour() {
        assertEquals(2, new DelaiRoute().jours(775, false)); // 11,07 h
    }

    @Test
    void dix_huit_heures_si_urgent() {
        assertEquals(1, new DelaiRoute().jours(775, true));
    }
}
