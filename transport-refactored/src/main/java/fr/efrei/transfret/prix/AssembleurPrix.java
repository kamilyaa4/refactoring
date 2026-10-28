package fr.efrei.transfret.prix;

import fr.efrei.transfret.ModeTransport;
import fr.efrei.transfret.TypeClient;
import fr.efrei.transfret.remise.PolitiqueRemise;
import fr.efrei.transfret.tarif.TarifTransport;

import java.util.Map;

/**
 * Empile les maillons dans l'ordre imposé par les règles métier :
 * tarif de base, suppléments marchandises, urgence, remise (sur ce prix), puis assurance.
 */
public class AssembleurPrix {

    private final Map<ModeTransport, TarifTransport> tarifs;
    private final Map<TypeClient, PolitiqueRemise> remises;

    public AssembleurPrix(Map<ModeTransport, TarifTransport> tarifs, Map<TypeClient, PolitiqueRemise> remises) {
        this.tarifs = tarifs;
        this.remises = remises;
    }

    public CalculPrix assembler() {
        CalculPrix prix = new TarifDeBase(tarifs);
        prix = new SupplementFragile(prix);
        prix = new SupplementDangereux(prix);
        prix = new SupplementRefrigere(prix);
        prix = new SupplementUrgence(prix);
        prix = new Remise(prix, remises);
        prix = new Assurance(prix);
        return prix;
    }
}
