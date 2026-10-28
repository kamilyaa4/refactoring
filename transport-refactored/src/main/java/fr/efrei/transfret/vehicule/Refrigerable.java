package fr.efrei.transfret.vehicule;

/** Seuls les véhicules équipés d'un espace froid implémentent cette interface. */
public interface Refrigerable {

    void activerRefrigeration(double temperature);
}
