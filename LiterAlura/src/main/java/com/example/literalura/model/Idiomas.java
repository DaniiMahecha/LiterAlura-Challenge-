package com.example.literalura.model;

import java.util.ArrayList;
import java.util.List;

public enum Idiomas {

        ENGLISH("en"),
        SPANISH("es"),
        FRENCH("fr"),
        PORTUGUESE("pt"),
        ITALIAN("it");

        private final String code;

        Idiomas(String code) {
            this.code = code;
        }

        public static List<Idiomas> fromAPI(List<String> codes) {
            List<Idiomas> result = new ArrayList<>();

            for (String code : codes) {
                for (Idiomas idioma : Idiomas.values()) {
                    if (idioma.code.equalsIgnoreCase(code)) {
                        result.add(idioma);
                        break;
                    }
                }
            }

            return result;
        }
}


