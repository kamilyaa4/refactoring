package fr.efrei.transfret;

import java.util.Random;

/** Construit le graphe d'objets de l'application (seul endroit où l'on fait des « new » de services). */
public class TransFret {

    private final ClientRepository clients = new ClientRepositoryEnMemoire();
    private final ExpeditionRepository expeditions = new ExpeditionRepositoryEnMemoire();
    private final CanalNotification email = new EmailConsole();
    private final CanalNotification sms = new SmsConsole();
    private final ServiceClient serviceClient;
    private final ServiceExpedition serviceExpedition;

    public TransFret() {
        Notificateur notificateur = new Notificateur(email, sms);
        serviceClient = new ServiceClient(new ValidateurClient(), clients, notificateur);
        serviceExpedition = new ServiceExpedition(expeditions, new CalculateurPrix(),
                new AffectationVehicule(Flotte.parDefaut()), new ValidateurExpedition(), new EstimateurDelai(),
                notificateur, new TableDistances(), new GenerateurIdentifiant(new Random()));
    }

    public ServiceClient serviceClient() {
        return serviceClient;
    }

    public ServiceExpedition serviceExpedition() {
        return serviceExpedition;
    }

    public ClientRepository clients() {
        return clients;
    }

    public CanalNotification email() {
        return email;
    }

    public CanalNotification sms() {
        return sms;
    }
}
