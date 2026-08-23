import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LectorCasos {

    public static List<Caso> leer(String rutaArchivo) throws IOException {
        List<String> lineas = new ArrayList<>();

        for (String linea : Files.readAllLines(Path.of(rutaArchivo))) {
            String limpia = linea.trim();
            if (!limpia.isEmpty() && !limpia.startsWith("#")) {
                lineas.add(limpia);
            }
        }

        if (lineas.size() % 2 != 0) {
            throw new IllegalArgumentException(
                    "Falta la cadena de la expresión '" + lineas.get(lineas.size() - 1) + "'"
                            + " (cada expresión lleva su cadena en la línea siguiente)");
        }

        List<Caso> casos = new ArrayList<>();
        for (int i = 0; i < lineas.size(); i += 2) {
            String regex = lineas.get(i);
            String cadena = lineas.get(i + 1);
            if (cadena.equals("ε") || cadena.equals("E")) {
                cadena = "";
            }
            casos.add(new Caso(regex, cadena));
        }

        return casos;
    }
}
