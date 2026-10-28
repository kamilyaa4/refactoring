package fr.efrei.transfret;

import fr.efrei.transfret.delai.DelaiAir;
import fr.efrei.transfret.delai.DelaiMer;
import fr.efrei.transfret.delai.DelaiRail;
import fr.efrei.transfret.delai.DelaiRoute;
import fr.efrei.transfret.remise.RemiseEntreprise;
import fr.efrei.transfret.remise.RemisePremium;
import fr.efrei.transfret.remise.SansRemise;
import fr.efrei.transfret.tarif.TarifAir;
import fr.efrei.transfret.tarif.TarifMer;
import fr.efrei.transfret.tarif.TarifRail;
import fr.efrei.transfret.tarif.TarifRoute;

import java.util.Map;
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
        CalculateurPrix calculateur = new CalculateurPrix(
                Map.of(ModeTransport.ROUTE, new TarifRoute(),
                        ModeTransport.RAIL, new TarifRail(),
                        ModeTransport.MER, new TarifMer(),
                        ModeTransport.AIR, new TarifAir()),
                Map.of(TypeClient.PARTICULIER, new SansRemise(),
                        TypeClient.ENTREPRISE, new RemiseEntreprise(),
                        TypeClient.PREMIUM, new RemisePremium()));
        EstimateurDelai estimateurDelai = new EstimateurDelai(
                Map.of(ModeTransport.ROUTE, new DelaiRoute(),
                        ModeTransport.RAIL, new DelaiRail(),
                        ModeTransport.MER, new DelaiMer(),
                        ModeTransport.AIR, new DelaiAir()));
        serviceExpedition = new ServiceExpedition(expeditions, calculateur,
                new AffectationVehicule(Flotte.parDefaut()), new ValidateurExpedition(Map.of(
                        ModeTransport.ROUTE, new ContraintesMode(true, true, true),
                        ModeTransport.RAIL, new ContraintesMode(true, false, true),
                        ModeTransport.MER, new ContraintesMode(true, true, false),
                        ModeTransport.AIR, new ContraintesMode(false, false, true))),
                estimateurDelai,
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
