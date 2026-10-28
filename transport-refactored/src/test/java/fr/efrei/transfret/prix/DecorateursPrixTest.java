package fr.efrei.transfret.prix;

import fr.efrei.transfret.Colis;
import fr.efrei.transfret.DemandeTarif;
import fr.efrei.transfret.ModeTransport;
import fr.efrei.transfret.TypeClient;
import fr.efrei.transfret.TypeMarchandise;
import fr.efrei.transfret.remise.RemiseEntreprise;
import fr.efrei.transfret.remise.RemisePremium;
import fr.efrei.transfret.remise.SansRemise;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static fr.efrei.transfret.Demandes.colis;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** Chaque décorateur est testé sur un prix de base fixe de 1 000 €. */
class DecorateursPrixTest {

    private static final CalculPrix MILLE_EUROS = demande -> 1000;

    private static DemandeTarif demande(boolean urgent, boolean assurance, TypeClient client, Colis... colis) {
        return new DemandeTarif(List.of(colis), 100, ModeTransport.ROUTE, urgent, assurance, client);
    }

    private static DemandeTarif simple(TypeMarchandise... types) {
        Colis[] colis = new Colis[types.length];
        for (int i = 0; i < types.length; i++) {
            colis[i] = colis(10, types[i]);
        }
        return demande(false, false, TypeClient.PARTICULIER, colis);
    }

    @Test
    void fragile_plus_15_pourcent_une_seule_fois() {
        CalculPrix prix = new SupplementFragile(MILLE_EUROS);
        assertEquals(1150, prix.calculer(simple(TypeMarchandise.FRAGILE, TypeMarchandise.FRAGILE)), 0.001);
        assertEquals(1000, prix.calculer(simple(TypeMarchandise.STANDARD)), 0.001);
    }

    @Test
    void dangereux_plus_30_pourcent() {
        assertEquals(1300, new SupplementDangereux(MILLE_EUROS).calculer(simple(TypeMarchandise.DANGEREUX)), 0.001);
    }

    @Test
    void refrigere_plus_20_pourcent() {
        assertEquals(1200, new SupplementRefrigere(MILLE_EUROS).calculer(simple(TypeMarchandise.REFRIGERE)), 0.001);
    }

    @Test
    void les_supplements_s_enchainent() {
        CalculPrix prix = new SupplementRefrigere(new SupplementFragile(MILLE_EUROS));
        assertEquals(1000 * 1.15 * 1.20, prix.calculer(simple(TypeMarchandise.FRAGILE, TypeMarchandise.REFRIGERE)), 0.001);
    }

    @Test
    void urgence_fois_1_5() {
        CalculPrix prix = new SupplementUrgence(MILLE_EUROS);
        assertEquals(1500, prix.calculer(demande(true, false, TypeClient.PARTICULIER, colis(10, TypeMarchandise.STANDARD))), 0.001);
        assertEquals(1000, prix.calculer(simple(TypeMarchandise.STANDARD)), 0.001);
    }

    @Test
    void remise_selon_le_type_de_client() {
        CalculPrix prix = new Remise(MILLE_EUROS, Map.of(TypeClient.PARTICULIER, new SansRemise(),
                TypeClient.ENTREPRISE, new RemiseEntreprise(), TypeClient.PREMIUM, new RemisePremium()));
        assertEquals(1000, prix.calculer(demande(false, false, TypeClient.PARTICULIER, colis(10, TypeMarchandise.STANDARD))), 0.001);
        assertEquals(900, prix.calculer(demande(false, false, TypeClient.ENTREPRISE, colis(10, TypeMarchandise.STANDARD))), 0.001);
        assertEquals(800, prix.calculer(demande(false, false, TypeClient.PREMIUM, colis(10, TypeMarchandise.STANDARD))), 0.001);
    }

    @Test
    void assurance_2_pourcent_de_la_valeur_minimum_15_euros() {
        CalculPrix prix = new Assurance(MILLE_EUROS);
        assertEquals(1015, prix.calculer(demande(false, true, TypeClient.PARTICULIER, colis(10, TypeMarchandise.STANDARD))), 0.001);
        Colis precieux = new Colis("tableau", 10, 50, 40, 30, TypeMarchandise.STANDARD, 5000);
        assertEquals(1100, prix.calculer(demande(false, true, TypeClient.PARTICULIER, precieux)), 0.001);
        assertEquals(1000, prix.calculer(demande(false, false, TypeClient.PARTICULIER, precieux)), 0.001);
    }
}
