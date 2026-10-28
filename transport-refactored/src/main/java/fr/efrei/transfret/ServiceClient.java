package fr.efrei.transfret;

import java.util.Optional;

/** Inscrit un nouveau client : validation, enregistrement, email de bienvenue. */
public class ServiceClient {

    private final ValidateurClient validateur;
    private final ClientRepository clients;
    private final Notificateur notificateur;

    public ServiceClient(ValidateurClient validateur, ClientRepository clients, Notificateur notificateur) {
        this.validateur = validateur;
        this.clients = clients;
        this.notificateur = notificateur;
    }

    public Optional<Client> creerClient(String nom, String email, String telephone, String adresse, TypeClient type, String siret) {
        Optional<String> refus = validateur.valider(nom, email, type, siret);
        if (refus.isPresent()) {
            System.out.println("ERREUR : " + refus.get());
            return Optional.empty();
        }
        Client c = new Client(nom, email, telephone, adresse, type, siret);
        clients.sauvegarder(c);
        String corps = "Bonjour " + c.getNom() + ", votre compte " + c.getType().name().toLowerCase() + " a été créé.";
        notificateur.notifierParEmail(c, new Message("Bienvenue chez TransFret", corps, corps));
        return Optional.of(c);
    }
}
