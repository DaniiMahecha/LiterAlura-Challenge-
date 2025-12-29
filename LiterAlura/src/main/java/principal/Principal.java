package principal;

import service.ConsumoAPI;

import java.util.Scanner;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();

    public void prueba(){
        System.out.println(consumoAPI.obtenerDatos("gutendex.com/books?search=pride"));
    }


}
