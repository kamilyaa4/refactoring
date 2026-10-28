package fr.efrei.transfret;

/** Prévient un client par email, et aussi par SMS s'il est PREMIUM. */
public class Notificateur {

    public void notifier(Client client, String sujet, String corps, String sms) {
        EmailService.envoyer(client.getEmail(), sujet, corps);
        if (client.getType() == TypeClient.PREMIUM) {
            new SmsService().envoyerSms(client.getTelephone(), sms);
        }
    }
}
