import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LectorCasos {

    private static final String SEPARADOR = "::";

    public static List<Caso> leer(String rutaArchivo) throws IOException {
        List<Caso> casos = new ArrayList<>();

        for (String linea : Files.readAllLines(Path.of(rutaArchivo))) {
            String limpia = linea.trim();
            if (limpia.isEmpty() || limpia.startsWith("#")) {
                continue;
            }

            int pos = limpia.indexOf(SEPARADOR);
            if (pos < 0) {
                throw new IllegalArgumentException(
                        "Linea sin separador '" + SEPARADOR + "': " + linea);
            }

            String regex = limpia.substring(0, pos).trim();
            String cadena = limpia.substring(pos + SEPARADOR.length()).trim();
            casos.add(new Caso(regex, cadena));
        }

        return casos;
    }
}
