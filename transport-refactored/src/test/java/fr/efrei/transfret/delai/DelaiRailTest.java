package fr.efrei.transfret.delai;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DelaiRailTest {

    @Test
    void un_jour_de_plus_que_le_trajet() {
        assertEquals(3, new DelaiRail().jours(2000, false));
    }

    @Test
    void un_jour_de_moins_si_urgent() {
        assertEquals(2, new DelaiRail().jours(2000, true));
    }
}
