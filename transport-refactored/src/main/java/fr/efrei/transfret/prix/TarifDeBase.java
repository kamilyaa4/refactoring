package fr.efrei.transfret.prix;

import fr.efrei.transfret.DemandeTarif;
import fr.efrei.transfret.ModeTransport;
import fr.efrei.transfret.tarif.TarifTransport;

import java.util.Map;

/** Le cœur de la chaîne : délègue au tarif du mode demandé. */
public class TarifDeBase implements CalculPrix {

    private final Map<ModeTransport, TarifTransport> tarifs;

    public TarifDeBase(Map<ModeTransport, TarifTransport> tarifs) {
        this.tarifs = Map.copyOf(tarifs);
    }

    @Override
    public double calculer(DemandeTarif demande) {
        return tarifs.get(demande.mode()).prixDeBase(demande);
    }
}
