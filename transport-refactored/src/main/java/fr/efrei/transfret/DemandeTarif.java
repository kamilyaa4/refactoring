package fr.efrei.transfret;

import java.util.List;

/** Tout ce qu'il faut savoir pour chiffrer un transport. */
public record DemandeTarif(List<Colis> colis, int distance, ModeTransport mode, boolean urgent, boolean assurance,
                           TypeClient typeClient) {

    public double poidsTotal() {
        return colis.stream().mapToDouble(Colis::getPoids).sum();
    }

    public double volumeTotal() {
        return colis.stream().mapToDouble(Colis::getVolume).sum();
    }

    public double valeurTotale() {
        return colis.stream().mapToDouble(Colis::getValeurDeclaree).sum();
    }

    public boolean contient(TypeMarchandise type) {
        return colis.stream().anyMatch(c -> c.estDeType(type));
    }
}
