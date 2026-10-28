package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ExpeditionRepositoryEnMemoire implements ExpeditionRepository {

    private final Map<String, Expedition> expeditions = new LinkedHashMap<>();

    @Override
    public void sauvegarder(Expedition expedition) {
        expeditions.put(expedition.getId(), expedition);
    }

    @Override
    public Optional<Expedition> trouver(String id) {
        return Optional.ofNullable(expeditions.get(id));
    }

    @Override
    public List<Expedition> toutes() {
        return new ArrayList<>(expeditions.values());
    }
}
