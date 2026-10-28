package fr.efrei.transfret;

import java.util.Random;

/** Fabrique les identifiants d'expédition : EXP-<numéro>-<4 chiffres>. */
public class GenerateurIdentifiant {

    private static final int ALEA_MIN = 1000;
    private static final int ALEA_AMPLITUDE = 9000;

    private final Random random;
    private int compteur;

    public GenerateurIdentifiant(Random random) {
        this.random = random;
    }

    public String suivant() {
        compteur++;
        return "EXP-" + compteur + "-" + (ALEA_MIN + random.nextInt(ALEA_AMPLITUDE));
    }
}
