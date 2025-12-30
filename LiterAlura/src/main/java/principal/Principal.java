package principal;

import service.ConsumoAPI;

import java.util.Scanner;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private static String URL_BASE = "http:///books?";

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
                default:
                    System.out.println("Opción invalida");
                    break;


            }
        }

    }


}
