package fr.efrei.transfret.vehicule;

import fr.efrei.transfret.Colis;
import fr.efrei.transfret.ModeTransport;
import fr.efrei.transfret.TypeMarchandise;

import java.util.List;

/** Réservé aux expéditions réfrigérées. N'hérite plus de Camion : il n'est pas utilisable partout où un camion l'est. */
public class CamionFrigorifique extends VehiculeBase implements Refrigerable {

    public CamionFrigorifique(String immatriculation, double capaciteMax) {
        super(immatriculation, capaciteMax, ModeTransport.ROUTE);
    }

    @Override
    protected boolean accepte(List<Colis> colis) {
        return contient(colis, TypeMarchandise.REFRIGERE);
    }

    @Override
    public void partir(int distanceKm) {
        fairePlein();
        System.out.println("Le camion " + immatriculation + " roule sur " + distanceKm + " km");
        activerRefrigeration(TEMPERATURE_FROID);
    }

    @Override
    public void activerRefrigeration(double temperature) {
        System.out.println("Réfrigération activée à " + temperature + "°C sur " + immatriculation);
    }
}
