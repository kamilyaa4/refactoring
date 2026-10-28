package fr.efrei.transfret;

import java.text.SimpleDateFormat;
import java.util.Date;

/** Mise en forme des dates. */
public final class Dates {

    private static final String FORMAT = "dd/MM/yyyy";

    private Dates() {
    }

    public static String formater(Date d) {
        if (d == null) {
            return "";
        }
        return new SimpleDateFormat(FORMAT).format(d);
    }
}
