package fr.efrei.transfret.vehicule;

import fr.efrei.transfret.Colis;
import fr.efrei.transfret.ModeTransport;
import fr.efrei.transfret.TypeMarchandise;

import java.util.ArrayList;
import java.util.List;

/** Gestion commune du chargement et de la disponibilité. */
public abstract class VehiculeBase implements Vehicule {

    protected static final double TEMPERATURE_FROID = 4;

    protected final String immatriculation;
    private final double capaciteMax;
    private final ModeTransport mode;
    private final List<Colis> chargement = new ArrayList<>();
    private double chargeActuelle;
    private boolean disponible = true;

    protected VehiculeBase(String immatriculation, double capaciteMax, ModeTransport mode) {
        this.immatriculation = immatriculation;
        this.capaciteMax = capaciteMax;
        this.mode = mode;
    }

    @Override
    public String getImmatriculation() {
        return immatriculation;
    }

    @Override
    public boolean estDisponible() {
        return disponible;
    }

    @Override
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public final boolean peutPrendreEnCharge(List<Colis> colis, ModeTransport modeDemande) {
        double poids = colis.stream().mapToDouble(Colis::getPoids).sum();
        return mode == modeDemande && capaciteMax - chargeActuelle >= poids && accepte(colis);
    }

    /** Règle propre au véhicule sur la nature des marchandises. */
    protected abstract boolean accepte(List<Colis> colis);

    @Override
    public void charger(Colis colis) {
        if (chargeActuelle + colis.getPoids() > capaciteMax) {
            System.out.println("ERREUR : capacité dépassée pour " + immatriculation);
            return;
        }
        chargement.add(colis);
        chargeActuelle = chargeActuelle + colis.getPoids();
    }

    @Override
    public void decharger(Colis colis) {
        if (chargement.remove(colis)) {
            chargeActuelle = chargeActuelle - colis.getPoids();
        }
    }

    protected boolean transporte(TypeMarchandise type) {
        return chargement.stream().anyMatch(c -> c.estDeType(type));
    }

    protected static boolean contient(List<Colis> colis, TypeMarchandise type) {
        return colis.stream().anyMatch(c -> c.estDeType(type));
    }

    protected void fairePlein() {
        System.out.println("Plein effectué pour " + immatriculation);
    }
}
