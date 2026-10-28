package fr.efrei.transfret;

import java.util.List;
import java.util.Optional;

public interface ExpeditionRepository {

    void sauvegarder(Expedition expedition);

    Optional<Expedition> trouver(String id);

    List<Expedition> toutes();
}
