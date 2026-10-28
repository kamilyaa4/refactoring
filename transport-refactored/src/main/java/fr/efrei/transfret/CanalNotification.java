package fr.efrei.transfret;

import java.util.List;

/** Un moyen de prévenir un client (email, SMS…). */
public interface CanalNotification {

    void envoyer(Client client, Message message);

    /** Messages envoyés par ce canal, dans l'ordre. */
    List<MessageEnvoye> historique();
}
