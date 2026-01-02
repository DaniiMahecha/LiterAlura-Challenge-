package com.example.literalura.repository;

import com.example.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutoresRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombreContainsIgnoreCase(String nombreAutores);

    @Query(value = "SELECT a FROM Autor a WHERE a.nacimiento <= :año AND (a.fallecimiento IS NULL OR a.fallecimiento >= :año)")
    List<Autor> autorPorNacimiento(Integer año);
}
