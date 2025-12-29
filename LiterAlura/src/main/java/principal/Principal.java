package principal;

import service.ConsumoAPI;

import java.util.Scanner;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private static String URL_BASE = "http:///books?search=";

    public void prueba(){
        System.out.println(consumoAPI.obtenerDatos(URL_BASE));
    }


}
