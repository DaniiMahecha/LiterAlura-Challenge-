package com.example.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String titulo;

    @ManyToMany(mappedBy = "libros")
    private List<Autor> autores = new ArrayList<>();

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Idiomas> idiomas = new ArrayList<>();

    public Libro() {}

    public Libro(DatosLibros libro) {
        this.titulo = libro.titulo();
        this.idiomas = Idiomas.fromAPI(libro.idiomas());
    }

    public String getTitulo() {
        return titulo;
    }


    public List<Autor> getAutores() {
        return autores;
    }


    public List<Idiomas> getIdiomas() {
        return idiomas;
    }


    public void addAutor(Autor autor) {
        this.autores.add(autor);
        autor.getLibros().add(this);
    }


    @Override
    public String toString() {
        return "-------" + titulo.toUpperCase() + "-------\n"
                + "Autor(es): " + autores + "\n"
                + "Idioma(s): " + idiomas + "\n"
                + "_________________________________________";
    }
}
