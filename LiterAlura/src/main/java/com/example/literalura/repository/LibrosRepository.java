package com.example.literalura.repository;

import com.example.literalura.model.Idiomas;
import com.example.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibrosRepository extends JpaRepository<Libro,Long> {
    Optional<Libro> findByTituloContainsIgnoreCase(String tituloLibro);
    List<Libro> findByIdiomas(Idiomas Idiomas);
}
