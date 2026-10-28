package fr.efrei.transfret;

import fr.efrei.transfret.prix.CalculPrix;

/** Prix HT arrondi au centime, calculé par la chaîne de décorateurs. */
public class CalculateurPrix {

    private final CalculPrix chaine;

    public CalculateurPrix(CalculPrix chaine) {
        this.chaine = chaine;
    }

    public double calculerPrixHT(DemandeTarif demande) {
        return Montants.arrondir(chaine.calculer(demande));
    }
}
