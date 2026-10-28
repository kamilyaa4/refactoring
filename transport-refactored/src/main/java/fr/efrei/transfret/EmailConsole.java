package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.List;

/** Envoi d'email simulé sur la console. */
public class EmailConsole implements CanalNotification {

    private static final String EXPEDITEUR = "noreply@transfret.fr";

    private final List<MessageEnvoye> historique = new ArrayList<>();

    @Override
    public void envoyer(Client client, Message message) {
        String destinataire = client.getEmail();
        if (destinataire == null || !destinataire.contains("@")) {
            return;
        }
        System.out.println("[EMAIL] " + EXPEDITEUR + " -> " + destinataire + " | " + message.sujet());
        System.out.println("        " + message.corps());
        historique.add(new MessageEnvoye(destinataire, message.sujet()));
    }

    @Override
    public List<MessageEnvoye> historique() {
        return List.copyOf(historique);
    }
}
