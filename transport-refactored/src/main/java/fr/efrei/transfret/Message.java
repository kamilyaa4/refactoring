package fr.efrei.transfret;

/** Contenu d'une notification : version longue (email) et version courte (SMS). */
public record Message(String sujet, String corps, String texteCourt) {
}
