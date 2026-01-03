package com.example.literalura.principal;

import com.example.literalura.model.*;
import com.example.literalura.repository.AutoresRepository;
import com.example.literalura.repository.LibrosRepository;
import com.example.literalura.service.ConsumoAPI;
import com.example.literalura.service.ConvierteDatos;


import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


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
                    6 - genera estadisticas de las descargas de todos los libros de la base de datos
                    7 - top 10 libros más decargados
                    8 - buscar autor por nombre
                    
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
                case 6:
                    generandoEstadisticas();
                    break;
                case 7:
                    top10MasDescargados();
                    break;
                case 8:
                    buscarAutorPorNombre();
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
            return;
        }

        var datosLibro = datos.listaLibros().get(0);

        // Verificar si el libro ya existe
        Optional<Libro> libroExistente = librosRepository.findByTituloContainsIgnoreCase(datosLibro.titulo());

        if (libroExistente.isPresent()){
            System.out.println("El libro '" + datosLibro.titulo() + "' ya existe en la base de datos :) ");
            return;
        }

        // Verificar que el libro tenga al menos un autor
        if (datosLibro.infoAutor() == null || datosLibro.infoAutor().isEmpty()) {
            System.out.println("El libro no tiene información de autor");
            return;
        }

        // Obtener el primer autor del libro
        var datosAutor = datosLibro.infoAutor().get(0);

        // Buscar si el autor ya existe en la base de datos
        Optional<Autor> autorExistente = autoresRepository.findByNombreContainsIgnoreCase(datosAutor.nombre());

        Autor autor;
        if (autorExistente.isPresent()) {
            // Si el autor ya existe, usarlo
            autor = autorExistente.get();
            System.out.println("Autor existente encontrado: " + autor.getNombre());
        } else {
            // Si no existe, crear un nuevo autor
            autor = new Autor(datosAutor);
            autor = autoresRepository.save(autor);
            System.out.println("Nuevo autor guardado: " + autor.getNombre());
        }

        // Crear el libro y asignarle el autor
        Libro libro = new Libro(datosLibro);
        libro.setAutor(autor);

        // Guardar el libro
        librosRepository.save(libro);

        System.out.println("\n=== Libro guardado exitosamente ===");
        System.out.println(libro);

    }

    private void listarLibrosEnBD() {
        List<Libro> datos =  librosRepository.findAll();
        if (datos.isEmpty()){
            System.out.println("No existen registros en la base de Datos");
        } else {
            datos.forEach(System.out::println);
        }
    }

    private void listarAutoresEnBD() {
        List<Autor> datos = autoresRepository.findAll();
        if (datos.isEmpty()){
            System.out.println("No existen registros en la base de Datos");
        } else {
            datos.forEach(System.out::println);
        }
    }

    private void listarAutoresPorFecha() {
        System.out.println("Consulte los autores vivos hasta un año específico");
        Integer año = scanner.nextInt();
        scanner.nextLine();
        List<Autor> autores = autoresRepository.autorPorNacimiento(año);

        if (año < 0 || año > LocalDate.now().getYear()){
            System.out.println("Año invalido, vuelva a ingresar un año VALIDO");
        }

        if (autores.isEmpty()){
            System.out.println("No hay autores vivos registrados en ese año");
        }

        autores.forEach(System.out::println);
    }

    private void listarLibrosPorIdiomaEnBD() {
        String menu = """
                Escriba el idima que le interesa leer: 
                1)        en (Inglés)
                2)        es (Español)
                3)        fr (Francés)
                4)        pt (Portugués)
                5)        it (Italiano)
                """;
        System.out.println(menu);
        String idioma = scanner.nextLine().toLowerCase().trim();

        System.out.println("---- Resultados ----");

        try {
            Idiomas dbIdioma = Idiomas.fromAPI(idioma);
            List<Libro> libros = librosRepository.findByIdiomas(dbIdioma);
            if (libros.isEmpty()){
                System.out.println("No hay libros en ese idioma en la base de datos");
            }
            libros.forEach(System.out::println);
        } catch (IllegalArgumentException e) {
            System.out.println("Idioma no valido ingrese por favor en, es, fr, pt o it");
        }

    }

    private void generandoEstadisticas() {
        List<Libro> libros = librosRepository.findAll();
        DoubleSummaryStatistics dst = libros.stream()
                .filter(book -> book.getDescargas() > 0)
                .mapToDouble(Libro::getDescargas)
                .summaryStatistics();

        System.out.printf("""
                ------------------ Estadísticas ------------------
                Media de descargas: %.2f
                Máxima cantidad de descargas: %.2f [%s]
                Minima cantidad de descargas: %.2f [%s]
                Cantidad de libros en la base de datos: %d
                --------------------------------------------------
                """, dst.getAverage(),
                dst.getMax(),
                libros.stream()
                .filter(book -> book.getDescargas() == dst.getMax())
                .map(Libro::getTitulo)
                        .collect(Collectors.joining(", ")),

                dst.getMin(),
                libros.stream()
                        .filter(book -> book.getDescargas() == dst.getMin())
                        .map(Libro::getTitulo)
                        .collect(Collectors.joining(", ")),
                dst.getCount());
    }

    private void top10MasDescargados() {
        List<Libro> libros = librosRepository.findAll();
        List<Libro> top10 =
                libros.stream()
                        .sorted(Comparator.comparing(Libro::getDescargas).reversed())
                        .limit(10)
                        .collect(Collectors.toList());

        System.out.println("\n===== TOP 10 LIBROS MÁS DESCARGADOS =====\n");
        top10.forEach(libro ->
                System.out.printf("%s - %d descargas\n",
                        libro.getTitulo(),
                        libro.getDescargas()));

    }

    private void buscarAutorPorNombre() {
        System.out.println("Ingrese el nombre del autor que desea encontrar: ");
        String nombre = scanner.nextLine();
        Optional<Autor> autor = autoresRepository.findByNombreContainsIgnoreCase(nombre);
        if (autor.isPresent()){
            System.out.println(autor.get());
        }
        System.out.println("Autor no encontrado en la base de datos");

    }

}
