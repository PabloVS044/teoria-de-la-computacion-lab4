import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AFN {

    public static class Transicion {
        public final String simbolo;
        public final int destino;

        public Transicion(String simbolo, int destino) {
            this.simbolo = simbolo;
            this.destino = destino;
        }
    }

    public static class Fragmento {
        public final int inicio;
        public final int fin;

        public Fragmento(int inicio, int fin) {
            this.inicio = inicio;
            this.fin = fin;
        }
    }

    public final List<List<Transicion>> transiciones = new ArrayList<>();
    public int inicio;
    public int aceptacion;

    public int nuevoEstado() {
        transiciones.add(new ArrayList<>());
        return transiciones.size() - 1;
    }

    public void agregarTransicion(int origen, String simbolo, int destino) {
        transiciones.get(origen).add(new Transicion(simbolo, destino));
    }

    public boolean acepta(String cadena) {
        Set<Integer> actuales = cerraduraEpsilon(Set.of(inicio));
        for (int i = 0; i < cadena.length(); i++) {
            String simbolo = String.valueOf(cadena.charAt(i));
            Set<Integer> siguientes = new HashSet<>();
            for (int estado : actuales) {
                for (Transicion t : transiciones.get(estado)) {
                    if (simbolo.equals(t.simbolo)) {
                        siguientes.add(t.destino);
                    }
                }
            }
            actuales = cerraduraEpsilon(siguientes);
        }
        return actuales.contains(aceptacion);
    }

    private Set<Integer> cerraduraEpsilon(Set<Integer> estados) {
        Set<Integer> cerradura = new HashSet<>(estados);
        Deque<Integer> pendientes = new ArrayDeque<>(estados);
        while (!pendientes.isEmpty()) {
            int estado = pendientes.pop();
            for (Transicion t : transiciones.get(estado)) {
                if (t.simbolo == null && cerradura.add(t.destino)) {
                    pendientes.push(t.destino);
                }
            }
        }
        return cerradura;
    }

    public Set<String> alfabeto() {
        Set<String> simbolos = new LinkedHashSet<>();
        for (List<Transicion> lista : transiciones) {
            for (Transicion t : lista) {
                if (t.simbolo != null) {
                    simbolos.add(t.simbolo);
                }
            }
        }
        return simbolos;
    }

    public String texto() {
        StringBuilder sb = new StringBuilder();
        sb.append("Estados:    ").append(transiciones.size()).append('\n');
        sb.append("Alfabeto:   ").append(alfabeto()).append('\n');
        sb.append("Inicio:     ").append(inicio).append('\n');
        sb.append("Aceptación: ").append(aceptacion).append('\n');
        sb.append("Transiciones:\n");
        for (int estado = 0; estado < transiciones.size(); estado++) {
            for (Transicion t : transiciones.get(estado)) {
                sb.append("  ").append(estado).append(" --")
                        .append(t.simbolo == null ? "ε" : t.simbolo)
                        .append("--> ").append(t.destino).append('\n');
            }
        }
        return sb.toString();
    }
}
