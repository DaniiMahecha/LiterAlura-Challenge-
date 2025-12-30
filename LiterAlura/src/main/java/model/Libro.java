package model;

import java.util.List;
import java.util.stream.Collectors;

public class Libro {
    private String titulo;
    private List<Autor> autor;
    private List<Idiomas> idiomas;

    public Libro() {}

    public Libro(DatosLibros libro) {
        this.titulo = libro.titulo();
        this.idiomas = Idiomas.fromAPI(libro.idiomas());
    }
}
