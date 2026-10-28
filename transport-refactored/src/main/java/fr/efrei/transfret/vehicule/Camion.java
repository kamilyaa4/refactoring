package fr.efrei.transfret.vehicule;

import fr.efrei.transfret.Colis;
import fr.efrei.transfret.ModeTransport;
import fr.efrei.transfret.TypeMarchandise;

import java.util.List;

/** Camion classique : pas de réfrigéré, matières dangereuses seulement s'il est certifié ADR. */
public class Camion extends VehiculeBase {

    private final boolean certifieAdr;

    public Camion(String immatriculation, double capaciteMax, boolean certifieAdr) {
        super(immatriculation, capaciteMax, ModeTransport.ROUTE);
        this.certifieAdr = certifieAdr;
    }

    @Override
    protected boolean accepte(List<Colis> colis) {
        return !contient(colis, TypeMarchandise.REFRIGERE)
                && (certifieAdr || !contient(colis, TypeMarchandise.DANGEREUX));
    }

    @Override
    public void partir(int distanceKm) {
        fairePlein();
        System.out.println("Le camion " + immatriculation + " roule sur " + distanceKm + " km");
    }
}
