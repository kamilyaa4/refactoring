package fr.efrei.transfret.vehicule;

import fr.efrei.transfret.Colis;
import fr.efrei.transfret.ModeTransport;

import java.util.List;

public class Peniche extends VehiculeBase {

    public Peniche(String immatriculation, double capaciteMax) {
        super(immatriculation, capaciteMax, ModeTransport.FLUVIAL);
    }

    @Override
    protected boolean accepte(List<Colis> colis) {
        return true;
    }

    @Override
    public void partir(int distanceKm) {
        fairePlein();
        System.out.println("La péniche " + immatriculation + " descend le fleuve sur " + distanceKm + " km");
    }
}
