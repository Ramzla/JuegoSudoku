package com.example.juegosudoku;

public class ValidadorSudoku {

    private final TableroSudoku tablero;

    public ValidadorSudoku(TableroSudoku tablero) {
        this.tablero = tablero;
    }

    public boolean validarFila(int fila, int columna, int numero) {
        if (numero == 0) return true;

        for (int c = 0; c < 9; c++) {
            if (c != columna && tablero.getNumero(fila, c) == numero) {
                return false;
            }
        }
        return true;
    }

    public boolean validarColumna(int fila, int columna, int numero) {
        if (numero == 0) return true;

        for (int f = 0; f < 9; f++) {
            if (f != fila && tablero.getNumero(f, columna) == numero) {
                return false;
            }
        }
        return true;
    }

    public boolean validarCuadricula(int fila, int columna, int numero) {
        if (numero == 0) return true;

        int inicioFila = (fila / 3) * 3;
        int inicioColumna = (columna / 3) * 3;

        for (int f = inicioFila; f < inicioFila + 3; f++) {
            for (int c = inicioColumna; c < inicioColumna + 3; c++) {
                if ((f != fila || c != columna)
                        && tablero.getNumero(f, c) == numero) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean esValido(int fila, int columna, int numero) {
        if (numero < 1 || numero > 9) return false;

        return validarFila(fila, columna, numero)
                && validarColumna(fila, columna, numero)
                && validarCuadricula(fila, columna, numero);
    }

    public boolean tieneErrores() {
        for (int f = 0; f < 9; f++) {
            for (int c = 0; c < 9; c++) {
                int numero = tablero.getNumero(f, c);
                if (numero != 0 && !esValido(f, c, numero)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean estaResuelto() {
        for (int f = 0; f < 9; f++) {
            for (int c = 0; c < 9; c++) {
                int numero = tablero.getNumero(f, c);
                if (numero == 0 || !esValido(f, c, numero)) {
                    return false;
                }
            }
        }
        return true;
    }
}