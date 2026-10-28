package fr.efrei.transfret.remise;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SansRemiseTest {

    @Test
    void le_prix_ne_change_pas() {
        assertEquals(1234.5, new SansRemise().appliquer(1234.5), 0.001);
    }
}
