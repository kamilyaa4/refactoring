package fr.efrei.transfret;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.OptionalInt;

/** Distances routières connues entre deux villes, dans les deux sens, sans tenir compte de la casse. */
public class TableDistances {

    private final Map<String, Integer> distances = new HashMap<>();

    public TableDistances() {
        ajouter("Paris", "Lyon", 465);
        ajouter("Paris", "Marseille", 775);
        ajouter("Paris", "Lille", 225);
        ajouter("Paris", "Bordeaux", 585);
        ajouter("Lyon", "Marseille", 315);
        ajouter("Le Havre", "New York", 5850);
        ajouter("Marseille", "Alger", 760);
        ajouter("Paris", "New York", 5840);
        ajouter("Paris", "Le Havre", 365);
    }

    public final void ajouter(String villeA, String villeB, int km) {
        distances.put(cle(villeA, villeB), km);
        distances.put(cle(villeB, villeA), km);
    }

    public OptionalInt distance(String villeA, String villeB) {
        Integer km = distances.get(cle(villeA, villeB));
        return km == null ? OptionalInt.empty() : OptionalInt.of(km);
    }

    private static String cle(String villeA, String villeB) {
        return villeA.toUpperCase(Locale.ROOT) + "|" + villeB.toUpperCase(Locale.ROOT);
    }
}
