package fr.efrei.transfret.remise;

public class SansRemise implements PolitiqueRemise {

    @Override
    public double appliquer(double prix) {
        return prix;
    }
}
