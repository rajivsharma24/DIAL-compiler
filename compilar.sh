#!/bin/bash
java -cp src/jflex.jar jflex.Main AnalizadorLexico.l
mv AnalizadorLexico.java src/alex/
java -cp src/cup.jar java_cup.Main -parser AnalizadorSintactico -symbols ClaseLexica -nopositions tiny2.cup
mv AnalizadorSintactico.java src/asint/
mv ClaseLexica.java src/asint/
javac -cp "src/cup.jar:src" src/*/*.java
javac -cp "src/cup.jar:src" src/*/*/*.java
