package fr.efrei.transfret;

/** Trace d'un message parti : destinataire et objet (sujet de l'email ou texte du SMS). */
public record MessageEnvoye(String destinataire, String objet) {
}
