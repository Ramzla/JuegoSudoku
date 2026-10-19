package com.example.juegosudoku;

public class PlantillasSudoku {

    private static final int[][][] PLANTILLAS = {
            {
                    {5,3,0,0,7,0,0,0,0},
                    {6,0,0,1,9,5,0,0,0},
                    {0,9,8,0,0,0,0,6,0},
                    {8,0,0,0,6,0,0,0,3},
                    {4,0,0,8,0,3,0,0,1},
                    {7,0,0,0,2,0,0,0,6},
                    {0,6,0,0,0,0,2,8,0},
                    {0,0,0,4,1,9,0,0,5},
                    {0,0,0,0,8,0,0,7,9}
            },
            {
                    {0,0,0,2,6,0,7,0,1},
                    {6,8,0,0,7,0,0,9,0},
                    {1,9,0,0,0,4,5,0,0},
                    {8,2,0,1,0,0,0,4,0},
                    {0,0,4,6,0,2,9,0,0},
                    {0,5,0,0,0,3,0,2,8},
                    {0,0,9,3,0,0,0,7,4},
                    {0,4,0,0,5,0,0,3,6},
                    {7,0,3,0,1,8,0,0,0}
            },
            {
                    {0,0,0,0,0,0,9,0,7},
                    {0,0,0,4,2,0,1,8,0},
                    {0,0,0,7,0,5,0,2,6},
                    {1,0,0,9,0,4,0,0,0},
                    {0,5,0,0,0,0,0,4,0},
                    {0,0,0,5,0,7,0,0,9},
                    {9,2,0,1,0,8,0,0,0},
                    {0,3,4,0,5,9,0,0,0},
                    {5,0,7,0,0,0,0,0,0}
            }
    };

    public static int[][] obtenerPlantilla(int numero) {
        if (numero < 1 || numero > PLANTILLAS.length) {
            throw new IllegalArgumentException("La plantilla debe ser 1, 2 o 3");
        }

        int[][] copia = new int[9][9];
        for (int fila = 0; fila < 9; fila++) {
            for (int columna = 0; columna < 9; columna++) {
                copia[fila][columna] = PLANTILLAS[numero - 1][fila][columna];
            }
        }
        return copia;
    }
}
