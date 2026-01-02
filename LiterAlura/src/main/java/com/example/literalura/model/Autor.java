package com.example.literalura.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String nombre;

    private Integer nacimiento;
    private Integer fallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(){}

    public Autor(DatosAutor autor) {
        this.nombre = autor.nombre();
        this.nacimiento = autor.nacimiento();
        this.fallecimiento = autor.fallecimiento();
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Integer nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Integer getFallecimiento() {
        return fallecimiento;
    }

    public void setFallecimiento(Integer fallecimiento) {
        this.fallecimiento = fallecimiento;
    }


    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        libros.forEach(book -> book.setAutor(this));
        this.libros = libros;
    }

    @Override
    public String toString() {

        String titulosLibros = "";
        if (libros != null && !libros.isEmpty()) {
            titulosLibros = libros.stream()
                    .map(Libro::getTitulo)
                    .collect(Collectors.joining(", "));
        }

        return "=================" + '\n' +
                "Autor: "
                + nombre + '\n' +
                "Nacimiento: "
                + nacimiento + '\n' +
                "Fallecimiento: "
                + (fallecimiento != null ? fallecimiento : "No hay infomación")  + '\n' +
                "Libros: ["
                + titulosLibros + ']' + '\n' +
                "=================";
    }
}
