import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RegexShuntingYard {

    private static int precedencia(char op) {
        if (op == '|') {
            return 1;
        }
        if (op == '.') {
            return 2;
        }
        return 0;
    }

    private static boolean esBinario(char c) {
        return c == '|' || c == '.';
    }

    private static boolean esUnario(char c) {
        return c == '*' || c == '+' || c == '?';
    }

    private static boolean esSimbolo(char c) {
        return !esBinario(c) && !esUnario(c) && c != '(' && c != ')';
    }

    private static boolean cierraOperando(char c) {
        return esSimbolo(c) || esUnario(c) || c == ')';
    }

    private static boolean abreOperando(char c) {
        return esSimbolo(c) || c == '(';
    }

    public static List<String> tokenizar(String expresion) {
        List<String> tokens = new ArrayList<>();

        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);
            if (c == ' ') {
                continue;
            }
            if (!tokens.isEmpty()) {
                char anterior = tokens.get(tokens.size() - 1).charAt(0);
                if (cierraOperando(anterior) && abreOperando(c)) {
                    tokens.add(".");
                }
            }
            tokens.add(String.valueOf(c));
        }

        return tokens;
    }

    public static List<String> aPostfija(String expresion) {
        List<String> salida = new ArrayList<>();
        Stack<String> pila = new Stack<>();

        for (String token : tokenizar(expresion)) {
            char c = token.charAt(0);

            if (esBinario(c)) {
                while (!pila.isEmpty()
                        && esBinario(pila.peek().charAt(0))
                        && precedencia(pila.peek().charAt(0)) >= precedencia(c)) {
                    salida.add(pila.pop());
                }
                pila.push(token);
            } else if (esUnario(c)) {
                salida.add(token);
            } else if (c == '(') {
                pila.push(token);
            } else if (c == ')') {
                while (!pila.isEmpty() && !pila.peek().equals("(")) {
                    salida.add(pila.pop());
                }
                if (pila.isEmpty()) {
                    throw new IllegalArgumentException("Parentesis desbalanceados");
                }
                pila.pop();
            } else {
                salida.add(token);
            }
        }

        while (!pila.isEmpty()) {
            if (pila.peek().equals("(")) {
                throw new IllegalArgumentException("Parentesis desbalanceados");
            }
            salida.add(pila.pop());
        }

        return salida;
    }

    public static List<String> describir(List<String> postfija) {
        List<String> descripcion = new ArrayList<>();

        for (int i = postfija.size() - 1; i >= 0; i--) {
            String token = postfija.get(i);
            char c = token.charAt(0);

            if (c == '.') {
                descripcion.add("Concatenación con");
            } else if (c == '|') {
                descripcion.add("Unión con");
            } else if (c == '*') {
                descripcion.add("Kleene de");
            } else if (c == '+') {
                descripcion.add("Kleene positiva de");
            } else if (c == '?') {
                descripcion.add("Opcional de");
            } else if (i == 0) {
                descripcion.add(token);
            } else {
                descripcion.add(token + " de");
            }
        }

        return descripcion;
    }

    public static String postfijaComoTexto(List<String> postfija) {
        return String.join("", postfija);
    }
}
