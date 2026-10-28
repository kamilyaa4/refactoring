package fr.efrei.transfret.vehicule;

import fr.efrei.transfret.Colis;
import fr.efrei.transfret.ModeTransport;

import java.util.List;

/** Ce que l'application attend de n'importe quel véhicule de la flotte. */
public interface Vehicule {

    String getImmatriculation();

    boolean estDisponible();

    void setDisponible(boolean disponible);

    /** Le véhicule peut-il prendre ces colis, pour ce mode, avec la place qui lui reste ? */
    boolean peutPrendreEnCharge(List<Colis> colis, ModeTransport mode);

    void charger(Colis colis);

    void decharger(Colis colis);

    /** Démarre le trajet, chaque véhicule à sa façon. */
    void partir(int distanceKm);
}
