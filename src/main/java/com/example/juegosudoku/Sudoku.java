package com.example.juegosudoku;

public class Sudoku {

    public static final int TAMANO = 9;

    private final TableroSudoku tablero = new TableroSudoku();
    private final ValidadorSudoku validador = new ValidadorSudoku(tablero);
    private int plantillaActual;

    public Sudoku() {
        cargarTablero(1);
    }

    public void cargarTablero(int plantilla) {
        tablero.cargar(PlantillasSudoku.obtenerPlantilla(plantilla));
        plantillaActual = plantilla;
    }

    public int getPlantillaActual() {
        return plantillaActual;
    }

    public int getNumero(int fila, int columna) {
        return tablero.getNumero(fila, columna);
    }

    public boolean esFija(int fila, int columna) {
        return tablero.esFija(fila, columna);
    }

    public boolean colocarNumero(int fila, int columna, int numero) {
        return tablero.colocarNumero(fila, columna, numero);
    }

    public boolean validarFila(int fila, int columna, int numero) {
        return validador.validarFila(fila, columna, numero);
    }

    public boolean validarColumna(int fila, int columna, int numero) {
        return validador.validarColumna(fila, columna, numero);
    }

    public boolean validarCuadricula(int fila, int columna, int numero) {
        return validador.validarCuadricula(fila, columna, numero);
    }

    public boolean esValido(int fila, int columna, int numero) {
        return validador.esValido(fila, columna, numero);
    }

    public boolean tieneErrores() {
        return validador.tieneErrores();
    }

    public boolean estaResuelto() {
        return validador.estaResuelto();
    }

    public void reiniciar() {
        cargarTablero(plantillaActual);
    }
}
