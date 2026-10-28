package fr.efrei.transfret.delai;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DelaiFluvialTest {

    @Test
    void un_jour_de_plus_que_le_trajet() {
        assertEquals(3, new DelaiFluvial().jours(365, false)); // 1,01 -> 2 + 1
    }
}
