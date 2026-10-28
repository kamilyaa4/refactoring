package fr.efrei.transfret;

import fr.efrei.transfret.vehicule.Vehicule;

import java.util.List;
import java.util.Optional;

/** Choisit le premier véhicule disponible capable de prendre l'expédition en charge. */
public class AffectationVehicule {

    private final Flotte flotte;

    public AffectationVehicule(Flotte flotte) {
        this.flotte = flotte;
    }

    public Optional<Vehicule> trouverVehicule(List<Colis> colis, ModeTransport mode) {
        return flotte.vehicules().stream()
                .filter(Vehicule::estDisponible)
                .filter(v -> v.peutPrendreEnCharge(colis, mode))
                .findFirst();
    }
}
