package fr.efrei.transfret.tarif;

import fr.efrei.transfret.ModeTransport;
import fr.efrei.transfret.TypeMarchandise;
import org.junit.jupiter.api.Test;

import static fr.efrei.transfret.Demandes.colis;
import static fr.efrei.transfret.Demandes.demande;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TarifRouteTest {

    @Test
    void un_euro_vingt_du_km_et_dix_centimes_du_kg() {
        double prix = new TarifRoute().prixDeBase(demande(ModeTransport.ROUTE, 100, colis(50, TypeMarchandise.STANDARD)));
        assertEquals(100 * 1.2 + 50 * 0.10, prix, 0.001);
    }
}
