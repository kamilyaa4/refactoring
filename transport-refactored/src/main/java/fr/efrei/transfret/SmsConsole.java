package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.List;

/** Envoi de SMS simulé sur la console. */
public class SmsConsole implements CanalNotification {

    private static final int LONGUEUR_MAX = 160;
    private static final String COUPURE = "...";

    private final List<MessageEnvoye> historique = new ArrayList<>();

    @Override
    public void envoyer(Client client, Message message) {
        String texte = message.texteCourt();
        if (texte.length() > LONGUEUR_MAX) {
            texte = texte.substring(0, LONGUEUR_MAX - COUPURE.length()) + COUPURE;
        }
        System.out.println("[SMS] -> " + client.getTelephone() + " : " + texte);
        historique.add(new MessageEnvoye(client.getTelephone(), texte));
    }

    @Override
    public List<MessageEnvoye> historique() {
        return List.copyOf(historique);
    }
}
