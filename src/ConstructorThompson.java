public class ConstructorThompson {

    public static AFN construir(Nodo raiz) {
        AFN afn = new AFN();
        AFN.Fragmento f = construirFragmento(raiz, afn);
        afn.inicio = f.inicio;
        afn.aceptacion = f.fin;
        return afn;
    }

    private static AFN.Fragmento construirFragmento(Nodo nodo, AFN afn) {
        if (nodo.esHoja()) {
            return nodo.valor.equals(ArbolSintactico.EPSILON)
                    ? simboloOEpsilon(null, afn)
                    : simboloOEpsilon(nodo.valor, afn);
        }

        switch (nodo.valor) {
            case ".":
                return concatenar(construirFragmento(nodo.izquierdo, afn),
                        construirFragmento(nodo.derecho, afn), afn);
            case "|":
                return union(construirFragmento(nodo.izquierdo, afn),
                        construirFragmento(nodo.derecho, afn), afn);
            case "*":
                return estrella(construirFragmento(nodo.izquierdo, afn), afn);
            case "+":
                return positiva(construirFragmento(nodo.izquierdo, afn), afn);
            case "?":
                return opcional(construirFragmento(nodo.izquierdo, afn), afn);
            default:
                throw new IllegalArgumentException("Nodo desconocido: " + nodo.valor);
        }
    }

    private static AFN.Fragmento simboloOEpsilon(String simbolo, AFN afn) {
        int inicio = afn.nuevoEstado();
        int fin = afn.nuevoEstado();
        afn.agregarTransicion(inicio, simbolo, fin);
        return new AFN.Fragmento(inicio, fin);
    }

    private static AFN.Fragmento concatenar(AFN.Fragmento a, AFN.Fragmento b, AFN afn) {
        afn.agregarTransicion(a.fin, null, b.inicio);
        return new AFN.Fragmento(a.inicio, b.fin);
    }

    private static AFN.Fragmento union(AFN.Fragmento a, AFN.Fragmento b, AFN afn) {
        int inicio = afn.nuevoEstado();
        int fin = afn.nuevoEstado();
        afn.agregarTransicion(inicio, null, a.inicio);
        afn.agregarTransicion(inicio, null, b.inicio);
        afn.agregarTransicion(a.fin, null, fin);
        afn.agregarTransicion(b.fin, null, fin);
        return new AFN.Fragmento(inicio, fin);
    }

    private static AFN.Fragmento estrella(AFN.Fragmento a, AFN afn) {
        int inicio = afn.nuevoEstado();
        int fin = afn.nuevoEstado();
        afn.agregarTransicion(inicio, null, a.inicio);
        afn.agregarTransicion(inicio, null, fin);
        afn.agregarTransicion(a.fin, null, a.inicio);
        afn.agregarTransicion(a.fin, null, fin);
        return new AFN.Fragmento(inicio, fin);
    }

    // r+ = r seguido de cero o más repeticiones: sin el salto directo inicio->fin de la estrella.
    private static AFN.Fragmento positiva(AFN.Fragmento a, AFN afn) {
        int inicio = afn.nuevoEstado();
        int fin = afn.nuevoEstado();
        afn.agregarTransicion(inicio, null, a.inicio);
        afn.agregarTransicion(a.fin, null, a.inicio);
        afn.agregarTransicion(a.fin, null, fin);
        return new AFN.Fragmento(inicio, fin);
    }

    // r? = r o ε: unión con un fragmento vacío.
    private static AFN.Fragmento opcional(AFN.Fragmento a, AFN afn) {
        int inicio = afn.nuevoEstado();
        int fin = afn.nuevoEstado();
        afn.agregarTransicion(inicio, null, a.inicio);
        afn.agregarTransicion(inicio, null, fin);
        afn.agregarTransicion(a.fin, null, fin);
        return new AFN.Fragmento(inicio, fin);
    }
}
