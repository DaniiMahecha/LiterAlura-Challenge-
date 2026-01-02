package com.example.literalura.model;


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

        public static Idiomas fromAPI(String language) {

            for (Idiomas idioma : Idiomas.values()) {
                if (idioma.code.equalsIgnoreCase(language)) {
                        return idioma;
                    }
                }
            throw new IllegalArgumentException("Ninguna categoria encontrada: " + language);
        }

}


