package fr.efrei.transfret;

/** Ce qu'un mode de transport accepte. */
public record ContraintesMode(boolean dangereuxAutorise, boolean refrigereAutorise, boolean urgenceAutorisee) {
}
