public class Nodo {

    private static int siguienteId = 1;

    public final int id;
    public final String valor;
    public final Nodo izquierdo;
    public final Nodo derecho;

    public Nodo(String valor) {
        this(valor, null, null);
    }

    public Nodo(String valor, Nodo izquierdo, Nodo derecho) {
        this.id = siguienteId++;
        this.valor = valor;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }

    public boolean esUnario() {
        return izquierdo != null && derecho == null;
    }

    public Nodo copia() {
        if (esHoja()) {
            return new Nodo(valor);
        }
        return new Nodo(valor,
                izquierdo == null ? null : izquierdo.copia(),
                derecho == null ? null : derecho.copia());
    }

    public int altura() {
        int izq = izquierdo == null ? 0 : izquierdo.altura();
        int der = derecho == null ? 0 : derecho.altura();
        return 1 + Math.max(izq, der);
    }

    public int cantidadNodos() {
        int izq = izquierdo == null ? 0 : izquierdo.cantidadNodos();
        int der = derecho == null ? 0 : derecho.cantidadNodos();
        return 1 + izq + der;
    }

    @Override
    public String toString() {
        return valor;
    }
}
