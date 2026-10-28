package fr.efrei.transfret.delai;

/** Stratégie : délai de livraison d'un mode de transport, en jours (avant le minimum d'un jour). */
public interface DelaiTransport {

    int jours(int distance, boolean urgent);
}
