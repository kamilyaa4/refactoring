package fr.efrei.transfret;

import fr.efrei.transfret.vehicule.Avion;
import fr.efrei.transfret.vehicule.Bateau;
import fr.efrei.transfret.vehicule.Camion;
import fr.efrei.transfret.vehicule.CamionFrigorifique;
import fr.efrei.transfret.vehicule.Train;
import fr.efrei.transfret.vehicule.Vehicule;

import java.util.ArrayList;
import java.util.List;

/** Les véhicules de l'entreprise. */
public class Flotte {

    private final List<Vehicule> vehicules;

    public Flotte(List<Vehicule> vehicules) {
        this.vehicules = new ArrayList<>(vehicules);
    }

    public static Flotte parDefaut() {
        return new Flotte(List.of(
                new Camion("AB-123-CD", 19000, false),
                new Camion("EF-456-GH", 12000, true),
                new CamionFrigorifique("IJ-789-KL", 8000),
                new Train("TR-0001", 600000),
                new Bateau("CMA-CGM-01", 20000000, true),
                new Avion("F-GTXA", 100000)));
    }

    public List<Vehicule> vehicules() {
        return List.copyOf(vehicules);
    }
}
