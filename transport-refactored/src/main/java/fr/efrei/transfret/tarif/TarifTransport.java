package fr.efrei.transfret.tarif;

import fr.efrei.transfret.DemandeTarif;

/** Stratégie : prix de base HT d'un mode de transport, avant suppléments et remise. */
public interface TarifTransport {

    double prixDeBase(DemandeTarif demande);
}
