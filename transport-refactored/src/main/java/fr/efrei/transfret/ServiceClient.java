package fr.efrei.transfret;

import java.util.Optional;

/** Inscrit un nouveau client (validation déléguée, enregistrement, email de bienvenue). */
public class ServiceClient {

    private final ValidateurClient validateur = new ValidateurClient();

    public Client creerClient(String nom, String email, String telephone, String adresse, TypeClient type, String siret) {
        Optional<String> refus = validateur.valider(nom, email, type, siret);
        if (refus.isPresent()) {
            System.out.println("ERREUR : " + refus.get());
            return null;
        }
        Client c = new Client(nom, email, telephone, adresse, type, siret);
        Database.getInstance().sauvegarderClient(c);
        EmailService.envoyer(c.getEmail(), "Bienvenue chez TransFret", "Bonjour " + c.getNom() + ", votre compte " + c.getType().name().toLowerCase() + " a été créé.");
        return c;
    }
}
