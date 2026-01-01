package com.example.literalura.principal;

import com.example.literalura.model.Autor;
import com.example.literalura.model.DatosResults;
import com.example.literalura.model.DatosLibros;
import com.example.literalura.model.Libro;
import com.example.literalura.repository.AutoresRepository;
import com.example.literalura.repository.LibrosRepository;
import com.example.literalura.service.ConsumoAPI;
import com.example.literalura.service.ConvierteDatos;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Principal {
    private  Scanner scanner = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/?";
    private LibrosRepository librosRepository;
    private AutoresRepository autoresRepository;
    private String libroInput;
    private List<DatosLibros> libroList = new ArrayList<>();

    public Principal(AutoresRepository autoresRepository,
                     LibrosRepository librosRepository) {
        this.autoresRepository = autoresRepository;
        this.librosRepository = librosRepository;
    }

    public void menu(){
        var opcion = -1;
        while (opcion != 0){
            var menu = """
                    1 - buscar libro por título
                    2 - listar libros registrados en la base de datos
                    3 - listar autores registrados
                    4 - listar autores vivos en un determinado año
                    5 - listar libros por idioma
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosEnBD();
                    break;
                case 3:
                    listarAutoresEnBD();
                    break;
                case 4:
                    listarAutoresPorFecha();
                    break;
                case 5:
                    listarLibrosPorIdiomaEnBD();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción invalida");



            }
        }

    }


    private DatosResults datosLibro() {
        System.out.println("Ingrese el nombre del libro que desea buscar");
        libroInput = scanner.nextLine();
        var json = consumoAPI.obtenerDatos((URL_BASE + "search=" + libroInput.toLowerCase()).replace(" ", "%20"));
        DatosResults datos = convierteDatos.obtenerDatos(json, DatosResults.class);
        return datos;

    }

    private void buscarLibroPorTitulo() {
        var datos = datosLibro();
        if (datos.listaLibros().isEmpty()){
            System.out.println("Libro no encontrado!!");
        }

        var datosLibro = datos.listaLibros().get(0);
        Optional<Libro> libroExitente = librosRepository.findByTituloContainsIgnoreCase(datosLibro.titulo());
        if (!libroExitente.isPresent()){
            Libro libro = new Libro(datosLibro);
            Autor autor = libro.getAutor();
            librosRepository.save(libro);
            autoresRepository.save(autor);
        }

        if (libroExitente.isPresent()){
            System.out.println(datosLibro + " ya existe en la base de datos :) ");
        }



    }

    private void listarLibrosEnBD() {
        System.out.println("2");
    }

    private void listarAutoresEnBD() {
        System.out.println("3");
    }

    private void listarAutoresPorFecha() {
        System.out.println("4");
    }

    private void listarLibrosPorIdiomaEnBD() {
        System.out.println("5");
    }




}
