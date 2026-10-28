package fr.efrei.transfret.vehicule;

import fr.efrei.transfret.Colis;
import fr.efrei.transfret.ModeTransport;
import fr.efrei.transfret.TypeMarchandise;

import java.util.List;

/** Navire : le réfrigéré n'est possible qu'avec des conteneurs frigorifiques. */
public class Bateau extends VehiculeBase implements Refrigerable {

    private static final double KM_PAR_MILLE_NAUTIQUE = 1.852;

    private final boolean conteneursRefrigeres;

    public Bateau(String immatriculation, double capaciteMax, boolean conteneursRefrigeres) {
        super(immatriculation, capaciteMax, ModeTransport.MER);
        this.conteneursRefrigeres = conteneursRefrigeres;
    }

    @Override
    protected boolean accepte(List<Colis> colis) {
        return conteneursRefrigeres || !contient(colis, TypeMarchandise.REFRIGERE);
    }

    @Override
    public void partir(int distanceKm) {
        fairePlein();
        int milles = (int) (distanceKm / KM_PAR_MILLE_NAUTIQUE);
        System.out.println("Le navire " + immatriculation + " navigue sur " + milles + " milles nautiques");
        if (transporte(TypeMarchandise.REFRIGERE)) {
            activerRefrigeration(TEMPERATURE_FROID);
        }
    }

    @Override
    public void activerRefrigeration(double temperature) {
        System.out.println("Conteneurs frigorifiques réglés à " + temperature + "°C");
    }
}
