package fr.efrei.transfret.prix;

import fr.efrei.transfret.DemandeTarif;

/** Enveloppe un autre calcul et modifie son résultat. */
public abstract class DecorateurPrix implements CalculPrix {

    private final CalculPrix enveloppe;

    protected DecorateurPrix(CalculPrix enveloppe) {
        this.enveloppe = enveloppe;
    }

    @Override
    public final double calculer(DemandeTarif demande) {
        return ajuster(enveloppe.calculer(demande), demande);
    }

    /** @return le prix après ce maillon */
    protected abstract double ajuster(double prix, DemandeTarif demande);
}
