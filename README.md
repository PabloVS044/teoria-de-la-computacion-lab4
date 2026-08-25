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
estado inicial, estado de aceptación y tabla de transiciones. Por último se
simula la cadena sobre el AFN (cerradura-ε + movimiento por símbolo) y se
responde **sí** o **no** según si termina en el estado de aceptación.

Link del video explicando: https://youtu.be/vvL9vZNYKA4

## Formato del archivo

El enunciado pide leer la expresión y la cadena desde un archivo, pero no
especifica cómo estructurarlo. El programa usa **dos líneas por caso**: la
expresión regular y, en la línea siguiente, la cadena a evaluar.

- Las líneas vacías y las que empiezan con `#` se ignoran, así que sirven para
  separar visualmente los casos y para comentar.
- Para pedir la **cadena vacía** se escribe `ε` (o `E`) en su línea.
- Si queda una expresión sin su cadena, el programa lo reporta y no procesa nada.

Ejemplo (`casos.txt`):

```
# Cada caso son dos lineas: la expresion regular y luego la cadena a evaluar.

(a*|b*)+
aaabbb

((ε|a)|b*)*
ab

(a|b)*abb(a|b)*
aabb

0?(1?)?0*
010
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
    ├── RegexShuntingYard.java  # laboratorio 2 (solo se renombró postfija -> postfix)
    ├── Nodo.java               # laboratorio 3, sin cambios
    ├── ArbolSintactico.java    # laboratorio 3, sin las partes de dibujo en texto
    ├── Caso.java               # par (regex, cadena)
    ├── LectorCasos.java        # lee el archivo y arma los casos (dos líneas cada uno)
    ├── AFN.java                # estados + transiciones + simulación (acepta) + despliegue en texto
    ├── ConstructorThompson.java # recorrido depth-first del árbol -> AFN
    └── Main.java               # arma todo el flujo por caso y lo imprime
```

## Estado

Por cada caso: se lee expresión y cadena, se convierte a postfix, se
construye el árbol sintáctico (sin desplegarlo, solo se reporta su tamaño),
se arma el AFN de Thompson (se despliega completo) y se simula la cadena
sobre ese AFN, respondiendo sí/no a la pertenencia.
