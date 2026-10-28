package fr.efrei.transfret;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Vérifie qu'une demande d'expédition respecte les règles d'acceptation. */
public class ValidateurExpedition {

    private final Map<ModeTransport, ContraintesMode> contraintes;

    public ValidateurExpedition(Map<ModeTransport, ContraintesMode> contraintes) {
        this.contraintes = Map.copyOf(contraintes);
    }

    /** @return le motif du refus, ou vide si la demande est acceptable */
    public Optional<String> valider(Client client, List<Colis> colis, ModeTransport mode, boolean urgent) {
        if (client == null) {
            return Optional.of("client obligatoire");
        }
        if (colis == null || colis.isEmpty()) {
            return Optional.of("au moins un colis est nécessaire");
        }
        for (Colis c : colis) {
            if (c.getPoids() <= 0) {
                return Optional.of("poids invalide pour " + c.getDescription());
            }
        }
        ContraintesMode regles = contraintes.get(mode);
        if (!regles.dangereuxAutorise() && contient(colis, TypeMarchandise.DANGEREUX)) {
            return Optional.of("matières dangereuses interdites en " + mode);
        }
        if (!regles.refrigereAutorise() && contient(colis, TypeMarchandise.REFRIGERE)) {
            return Optional.of("transport réfrigéré impossible en " + mode);
        }
        if (!regles.urgenceAutorisee() && urgent) {
            return Optional.of("pas d'option urgente en " + mode);
        }
        return Optional.empty();
    }

    private static boolean contient(List<Colis> colis, TypeMarchandise type) {
        return colis.stream().anyMatch(c -> c.estDeType(type));
    }
}
