import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ArbolSintactico {

    public static final String EPSILON = "ε";

    public static Nodo construir(List<String> postfix, boolean expandir) {
        Stack<Nodo> pila = new Stack<>();

        for (String token : postfix) {
            char c = token.charAt(0);

            if (c == '.' || c == '|') {
                Nodo derecho = desapilar(pila, token);
                Nodo izquierdo = desapilar(pila, token);
                pila.push(new Nodo(token, izquierdo, derecho));
            } else if (c == '*') {
                pila.push(new Nodo("*", desapilar(pila, token), null));
            } else if (c == '+') {
                Nodo hijo = desapilar(pila, token);
                pila.push(expandir ? expandirMas(hijo) : new Nodo("+", hijo, null));
            } else if (c == '?') {
                Nodo hijo = desapilar(pila, token);
                pila.push(expandir ? expandirOpcional(hijo) : new Nodo("?", hijo, null));
            } else {
                pila.push(new Nodo(normalizar(token)));
            }
        }

        if (pila.isEmpty()) {
            throw new IllegalArgumentException("Expresión vacía: no hay nada que construir");
        }
        if (pila.size() != 1) {
            throw new IllegalArgumentException("Expresión mal formada: sobran operandos");
        }
        return pila.pop();
    }

    // r+ = r . r*
    private static Nodo expandirMas(Nodo r) {
        return new Nodo(".", r, new Nodo("*", r.copia(), null));
    }

    // r? = r | ε
    private static Nodo expandirOpcional(Nodo r) {
        return new Nodo("|", r, new Nodo(EPSILON));
    }

    private static Nodo desapilar(Stack<Nodo> pila, String operador) {
        if (pila.isEmpty()) {
            throw new IllegalArgumentException("Faltan operandos para el operador '" + operador + "'");
        }
        return pila.pop();
    }

    public static String normalizar(String token) {
        return token.equals("E") ? EPSILON : token;
    }

    public static List<Nodo> hijosDe(Nodo nodo) {
        List<Nodo> hijos = new ArrayList<>();
        if (nodo.izquierdo != null) {
            hijos.add(nodo.izquierdo);
        }
        if (nodo.derecho != null) {
            hijos.add(nodo.derecho);
        }
        return hijos;
    }

    public static String postorden(Nodo nodo) {
        StringBuilder sb = new StringBuilder();
        postorden(nodo, sb);
        return sb.toString();
    }

    private static void postorden(Nodo nodo, StringBuilder sb) {
        for (Nodo hijo : hijosDe(nodo)) {
            postorden(hijo, sb);
        }
        sb.append(nodo.valor);
    }
}
