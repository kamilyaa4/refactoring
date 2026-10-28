package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/** Le mode FLUVIAL de bout en bout, sur l'application assemblée. */
class FluvialTest {

    private final TransFret app = new TransFret();
    private final Client client = app.serviceClient().creerClient("Jean Dupont", "jean.dupont@mail.fr",
            "0601020304", "Paris", TypeClient.PARTICULIER, null).orElseThrow();

    private Expedition expedier(TypeMarchandise type, boolean urgent) {
        return app.serviceExpedition().creerExpedition(client, List.of(Demandes.colis(20000, type)),
                "Paris", "Le Havre", ModeTransport.FLUVIAL, urgent, false);
    }

    @Test
    void une_expedition_fluviale_part_sur_la_peniche() {
        Expedition e = expedier(TypeMarchandise.STANDARD, false);
        assertNotNull(e);
        assertEquals("PEN-0001", e.getVehicule().getImmatriculation());
        assertEquals(546.0, e.getPrixHT(), 0.001); // 365 x 0,40 + 20 000 x 0,02
    }

    @Test
    void pas_de_refrigere_ni_d_urgence_en_fluvial() {
        assertNull(expedier(TypeMarchandise.REFRIGERE, false));
        assertNull(expedier(TypeMarchandise.STANDARD, true));
    }
}
