package fr.efrei.transfret.delai;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DelaiMerTest {

    @Test
    void deux_jours_de_plus_que_le_trajet() {
        assertEquals(11, new DelaiMer().jours(5850, false)); // 8,125 -> 9 + 2
    }
}
