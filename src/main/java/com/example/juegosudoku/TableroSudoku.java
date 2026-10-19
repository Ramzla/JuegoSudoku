package com.example.juegosudoku;

public class TableroSudoku {

    private final int[][] numeros = new int[9][9];
    private final boolean[][] fijas = new boolean[9][9];

    public void cargar(int[][] plantilla) {
        for (int fila = 0; fila < 9; fila++) {
            for (int columna = 0; columna < 9; columna++) {
                numeros[fila][columna] = plantilla[fila][columna];
                fijas[fila][columna] = numeros[fila][columna] != 0;
            }
        }
    }

    public int getNumero(int fila, int columna) {
        return numeros[fila][columna];
    }

    public boolean esFija(int fila, int columna) {
        return fijas[fila][columna];
    }

    public boolean colocarNumero(int fila, int columna, int numero) {
        if (fijas[fila][columna] || numero < 0 || numero > 9) {
            return false;
        }
        numeros[fila][columna] = numero;
        return true;
    }
}
