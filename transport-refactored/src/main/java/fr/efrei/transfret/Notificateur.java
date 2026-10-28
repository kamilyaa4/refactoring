package fr.efrei.transfret;

/** Prévient un client par email, et aussi par SMS s'il est PREMIUM. */
public class Notificateur {

    private final CanalNotification email;
    private final CanalNotification sms;

    public Notificateur(CanalNotification email, CanalNotification sms) {
        this.email = email;
        this.sms = sms;
    }

    public void notifier(Client client, Message message) {
        email.envoyer(client, message);
        if (client.getType().notifieParSms()) {
            sms.envoyer(client, message);
        }
    }

    public void notifierParEmail(Client client, Message message) {
        email.envoyer(client, message);
    }
}
