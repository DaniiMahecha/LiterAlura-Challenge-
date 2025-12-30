package model;

import java.util.ArrayList;
import java.util.List;

public enum Idiomas {
    english("en"),
    spanish("es"),
    french("fr"),
    portuguese("pt"),
    italian("it");

    private String language;

    Idiomas(String language) {
        this.language = language;
    }

    // Se realiza el Cast del String del API a alguna de las categorías del Enum
    public static List<Idiomas> fromAPI(List<String> language) {
        // Estamos recorriendo con "categoria" todos los valores (Constantes) del Enum
        for (Idiomas idioma : Idiomas.values()) {
            // Sí idioma.lenguage es igual a text, retornara el valor (Constante)
            // que corresponde a esa categoría.
            if (idioma.language.equalsIgnoreCase(language.get(0))) {
                List<Idiomas> idiomas = new ArrayList<>();
                idiomas.add(idioma);
                return idiomas;
            }
        }
        //De lo contrarió lanzará una excepción:
        throw new IllegalArgumentException("Ninguna categoria encontrada: " + language.get(0));
    }
}

