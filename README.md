# Laboratorio 4 — Lectura de expresión regular y cadena

Teoría de la Computación, CC2019 — Universidad del Valle de Guatemala

Primera etapa: leer desde un archivo de texto pares de **expresión regular** y
**cadena** a evaluar. Reusa `RegexShuntingYard.java` del laboratorio 2 (sin
modificaciones) para tokenizar y convertir cada expresión a postfix.

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
    └── Main.java               # lee casos, muestra postfix y arma el árbol sintáctico
```

## Estado

Por cada caso se lee la expresión y la cadena, se convierte a postfix y se
construye el árbol sintáctico (sin desplegarlo — solo se reporta su tamaño
como confirmación de que se armó bien). La construcción del autómata y la
evaluación de pertenencia se agregan en etapas siguientes.
