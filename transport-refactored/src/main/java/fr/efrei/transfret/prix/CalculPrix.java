package fr.efrei.transfret.prix;

import fr.efrei.transfret.DemandeTarif;

/** Un maillon du calcul du prix HT : le tarif de base, ou un décorateur qui l'enveloppe. */
public interface CalculPrix {

    double calculer(DemandeTarif demande);
}
