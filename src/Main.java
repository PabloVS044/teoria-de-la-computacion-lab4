import java.io.IOException;
import java.util.List;

public class Main {
    private static final String ARCHIVO_POR_DEFECTO = "casos.txt";

    public static void main(String[] args) {
        String ruta = args.length > 0 ? args[0] : ARCHIVO_POR_DEFECTO;

        System.out.println("Laboratorio 4 Teoría de la Computación");
        System.out.println("Leyendo casos de: " + ruta);
        System.out.println();

        List<Caso> casos;
        try {
            casos = LectorCasos.leer(ruta);
        } catch (IOException e) {
            System.out.println("Error: no se pudo leer el archivo '" + ruta + "': " + e.getMessage());
            return;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        if (casos.isEmpty()) {
            System.out.println("El archivo no tiene casos.");
            return;
        }

        for (Caso caso : casos) {
            procesar(caso);
        }
    }

    private static void procesar(Caso caso) {
        System.out.println("Expresión: " + caso.regex);
        System.out.println("Cadena:    " + (caso.cadena.isEmpty() ? "ε (vacía)" : caso.cadena));

        try {
            List<String> postfija = RegexShuntingYard.aPostfija(caso.regex);
            System.out.println("Postfija:  " + RegexShuntingYard.postfijaComoTexto(postfija));
        } catch (RuntimeException e) {
            System.out.println("Error en la expresión: " + e.getMessage());
        }

        System.out.println();
    }
}
