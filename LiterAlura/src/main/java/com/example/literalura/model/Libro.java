package com.example.literalura.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne
    private Autor autor;

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }


    @Enumerated(EnumType.STRING)
    private List<Idiomas> idiomas;

    private Integer descargas;
    public Libro() {}

    public Libro(DatosLibros libro) {
        this.titulo = libro.titulo();
        this.descargas = libro.descargas();
        this.idiomas = Idiomas.fromAPI(libro.idiomas());
    }

    public String getTitulo() {
        return titulo;
    }

    public List<Idiomas> getIdiomas() {
        return idiomas;
    }



    @Override
    public String toString() {
        return "----- LIBRO -----" + "\n" +
                "Título: " + titulo + "\n" +
                "Autor: " + autor + "\n" +
                "Idioma: " + idiomas + "\n" +
                "Descargas: " + descargas + "\n" +
                "-----------------\n";
    }
}
