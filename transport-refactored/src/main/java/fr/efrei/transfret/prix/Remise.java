package fr.efrei.transfret.prix;

import fr.efrei.transfret.DemandeTarif;
import fr.efrei.transfret.TypeClient;
import fr.efrei.transfret.remise.PolitiqueRemise;

import java.util.Map;

/** Applique la politique de remise du type de client, sur le prix après suppléments et urgence. */
public class Remise extends DecorateurPrix {

    private final Map<TypeClient, PolitiqueRemise> politiques;

    public Remise(CalculPrix enveloppe, Map<TypeClient, PolitiqueRemise> politiques) {
        super(enveloppe);
        this.politiques = Map.copyOf(politiques);
    }

    @Override
    protected double ajuster(double prix, DemandeTarif demande) {
        return politiques.get(demande.typeClient()).appliquer(prix);
    }
}
