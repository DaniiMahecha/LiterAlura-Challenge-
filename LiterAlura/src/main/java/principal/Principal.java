package principal;

import model.DatosDeTodosLosLibros;
import model.DatosLibros;
import model.Libro;
import service.ConsumoAPI;
import service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private static String URL_BASE = "https://gutendex.com/books/?";
    private List<DatosLibros> libroList = new ArrayList<>();

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


    private void datosLibro() {
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var libroInput = scanner.nextLine();
        var json = consumoAPI.obtenerDatos((URL_BASE + "search=" + libroInput.toLowerCase()).replace(" ", "%20"));
        System.out.println("JSON crudo => [" + json + "]");
        DatosDeTodosLosLibros listaLibros = convierteDatos.obtenerDatos(json, DatosDeTodosLosLibros.class);
    }

    private void buscarLibroPorTitulo() {
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
