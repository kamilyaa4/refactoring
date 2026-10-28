package fr.efrei.transfret;

public enum TypeClient {
    PARTICULIER(false, false),
    ENTREPRISE(true, false),
    PREMIUM(false, true);

    private final boolean siretObligatoire;
    private final boolean notifieParSms;

    TypeClient(boolean siretObligatoire, boolean notifieParSms) {
        this.siretObligatoire = siretObligatoire;
        this.notifieParSms = notifieParSms;
    }

    public boolean siretObligatoire() {
        return siretObligatoire;
    }

    public boolean notifieParSms() {
        return notifieParSms;
    }
}
