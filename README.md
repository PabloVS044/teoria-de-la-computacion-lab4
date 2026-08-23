# Laboratorio 4 — AFN por el método de Thompson

Teoría de la Computación, CC2019 — Universidad del Valle de Guatemala

Lee desde un archivo de texto pares de **expresión regular** y **cadena** a
evaluar. Reusa `RegexShuntingYard.java` (laboratorio 2) y `Nodo.java` /
`ArbolSintactico.java` (laboratorio 3) para tokenizar, convertir a postfix y
construir el árbol sintáctico. Con ese árbol se construye el **AFN** por el
método de **Thompson**, recorriendo el árbol depth-first (postorden): cada
nodo hoja es un fragmento de un símbolo, y cada operador combina los
fragmentos de sus hijos según las reglas de Thompson (`.` concatena, `|`
une, `*` cierra con repetición cero o más, `+` con una o más, `?` hace
opcional). El AFN se despliega en texto: cantidad de estados, alfabeto,
estado inicial, estado de aceptación y tabla de transiciones.

## Formato del archivo

Provisional, sujeto a cambio. Una línea por caso:

```
regex :: cadena
```

- `::` separa la expresión de la cadena.
- Líneas vacías o que empiezan con `#` se ignoran.
- Cadena vacía después de `::` se interpreta como ε.

Ejemplo (`casos.txt`):

```
(a*|b*)+ :: aaabbb
((E|a)|b*)* :: ab
(a|b)*abb(a|b)* :: aabb
0?(1?)?0* :: 010
```

## Ejecutar

```bash
java src/Main.java casos.txt
```

Sin argumento usa `casos.txt` por defecto.

## Estructura

```
.
├── README.md
├── casos.txt
└── src/
    ├── RegexShuntingYard.java  # laboratorio 2, sin cambios
    ├── Nodo.java               # laboratorio 3, sin cambios
    ├── ArbolSintactico.java    # laboratorio 3, sin las partes de dibujo en texto
    ├── Caso.java               # par (regex, cadena)
    ├── LectorCasos.java        # parseo del archivo
    ├── AFN.java                # estados + transiciones + despliegue en texto
    ├── ConstructorThompson.java # recorrido depth-first del árbol -> AFN
    └── Main.java               # arma todo el flujo por caso y lo imprime
```

## Estado

Por cada caso: se lee expresión y cadena, se convierte a postfix, se
construye el árbol sintáctico (sin desplegarlo, solo se reporta su tamaño) y
con ese árbol se arma el AFN de Thompson, que sí se despliega completo. La
evaluación de pertenencia de la cadena sobre el AFN se agrega en la
siguiente etapa.
