package fr.efrei.transfret.remise;

/** Stratégie : remise accordée selon le type de client. */
public interface PolitiqueRemise {

    double appliquer(double prix);
}
