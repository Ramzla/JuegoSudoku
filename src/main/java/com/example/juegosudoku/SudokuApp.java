package com.example.juegosudoku;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;

public class SudokuApp extends Application {

    private final Sudoku sudoku = new Sudoku();
    private final GridPane grilla = new GridPane();
    private final TextField[][] campos = new TextField[9][9];
    private final Label mensaje = new Label("Selecciona una casilla vacia para comenzar.");

    private int ultimaFila = -1;
    private int ultimaColumna = -1;
    private double tamanoCasilla = 40;
    private boolean mostrandoVictoria = false;
    private Stage ventana;

    @Override
    public void start(Stage stage) {
        ventana = stage;

        Label titulo = new Label("SUDOKU");
        titulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #172554;");

        Label instrucciones = new Label(
                "Completa el tablero con numeros del 1 al 9 sin repetir en filas, columnas o cuadros 3x3."
        );
        instrucciones.setWrapText(true);
        instrucciones.setStyle("-fx-text-fill: #475569; -fx-font-size: 13px;");

        HBox selector = new HBox(10, new Label("Plantillas:"));
        selector.setAlignment(Pos.CENTER);

        for (int i = 1; i <= 3; i++) {
            int plantilla = i;
            Button boton = new Button("Plantilla " + i);
            boton.setOnAction(e -> {
                sudoku.cargarTablero(plantilla);
                construirTablero();
                mensaje.setText("Plantilla " + plantilla + " cargada.");
            });
            selector.getChildren().add(boton);
        }

        VBox encabezado = new VBox(8, titulo, instrucciones, selector);
        encabezado.setAlignment(Pos.CENTER);

        grilla.setAlignment(Pos.CENTER);
        grilla.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        URL imagen = getClass().getResource("/imagenes/fondo_sudoku.png");
        if (imagen != null) {
            grilla.setStyle(
                    "-fx-background-image: url('" + imagen.toExternalForm() + "');" +
                            "-fx-background-size: cover;" +
                            "-fx-background-position: center;" +
                            "-fx-background-repeat: no-repeat;"
            );
        }

        construirTablero();

        Button validar = new Button("Validar tablero");
        validar.setOnAction(e -> {
            if (sudoku.estaResuelto()) {
                mostrarVictoria();
            } else if (sudoku.tieneErrores()) {
                mensaje.setText("Hay numeros repetidos en el tablero.");
            } else {
                mensaje.setText("No hay numeros repetidos. Continua completando el tablero.");
            }
        });

        Button reiniciar = new Button("Reiniciar");
        reiniciar.setOnAction(e -> {
            sudoku.reiniciar();
            construirTablero();
            mensaje.setText("Se reinicio la plantilla " + sudoku.getPlantillaActual() + ".");
        });

        HBox acciones = new HBox(12, validar, reiniciar);
        acciones.setAlignment(Pos.CENTER);

        Label colores = new Label("Verde: Valido    Rojo: Repetido    Gris: Fijo");
        colores.setStyle("-fx-font-size: 12px; -fx-text-fill: #475569;");

        mensaje.setWrapText(true);
        mensaje.setMaxWidth(Double.MAX_VALUE);
        mensaje.setAlignment(Pos.CENTER);
        mensaje.setStyle("-fx-font-size: 13px; -fx-text-fill: #334155;");

        VBox pie = new VBox(8, colores, acciones, mensaje);
        pie.setAlignment(Pos.CENTER);

        BorderPane panel = new BorderPane();
        panel.setTop(encabezado);
        panel.setCenter(grilla);
        panel.setBottom(pie);
        panel.setPadding(new Insets(15));
        panel.setStyle("-fx-background-color: #f8fafc;");
        BorderPane.setMargin(encabezado, new Insets(0, 0, 10, 0));
        BorderPane.setMargin(pie, new Insets(10, 0, 0, 0));

        Rectangle2D pantalla = Screen.getPrimary().getVisualBounds();
        double ancho = Math.min(650, pantalla.getWidth() - 40);
        double alto = Math.min(760, pantalla.getHeight() - 70);

        Scene escena = new Scene(panel, ancho, alto);
        stage.setTitle("Taller JavaFX - Sudoku");
        stage.setScene(escena);
        stage.setMinWidth(Math.min(440, pantalla.getWidth()));
        stage.setMinHeight(Math.min(560, pantalla.getHeight()));
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();

        Runnable ajustar = () -> ajustarTablero(escena, encabezado, pie);
        Platform.runLater(ajustar);
        escena.widthProperty().addListener((obs, antes, ahora) -> Platform.runLater(ajustar));
        escena.heightProperty().addListener((obs, antes, ahora) -> Platform.runLater(ajustar));
    }

    private void ajustarTablero(Scene escena, VBox encabezado, VBox pie) {
        double espacioAncho = escena.getWidth() - 40;
        double espacioAlto = escena.getHeight() - encabezado.getHeight() - pie.getHeight() - 65;
        tamanoCasilla = Math.max(22, Math.min(48, Math.min(espacioAncho / 9, espacioAlto / 9)));

        for (TextField[] fila : campos) {
            for (TextField campo : fila) {
                campo.setPrefSize(tamanoCasilla, tamanoCasilla);
                campo.setMinSize(tamanoCasilla, tamanoCasilla);
                campo.setMaxSize(tamanoCasilla, tamanoCasilla);
            }
        }
        pintarTablero();
    }

    private void construirTablero() {
        grilla.getChildren().clear();
        ultimaFila = -1;
        ultimaColumna = -1;

        for (int f = 0; f < 9; f++) {
            for (int c = 0; c < 9; c++) {
                final int fila = f;
                final int columna = c;
                TextField campo = new TextField();
                campos[f][c] = campo;

                campo.setAlignment(Pos.CENTER);
                campo.setPrefSize(tamanoCasilla, tamanoCasilla);
                campo.setMinSize(tamanoCasilla, tamanoCasilla);
                campo.setMaxSize(tamanoCasilla, tamanoCasilla);
                campo.setTextFormatter(new TextFormatter<String>(
                        cambio -> cambio.getControlNewText().matches("[1-9]?") ? cambio : null
                ));

                int numero = sudoku.getNumero(f, c);
                if (numero != 0) campo.setText(String.valueOf(numero));

                boolean fija = sudoku.esFija(f, c);
                campo.setEditable(!fija);
                campo.setFocusTraversable(!fija);

                if (!fija) {
                    campo.textProperty().addListener((obs, anterior, nuevo) -> {
                        int valor = nuevo.isEmpty() ? 0 : Integer.parseInt(nuevo);
                        sudoku.colocarNumero(fila, columna, valor);
                        ultimaFila = valor == 0 ? -1 : fila;
                        ultimaColumna = valor == 0 ? -1 : columna;
                        pintarTablero();

                        if (sudoku.estaResuelto()) {
                            mostrarVictoria();
                        } else if (valor == 0) {
                            mensaje.setText("Casilla vacia.");
                        } else if (sudoku.esValido(fila, columna, valor)) {
                            mensaje.setText("Numero " + valor + " valido en esta posicion.");
                        } else {
                            mensaje.setText("Numero " + valor + " repetido en fila, columna o cuadro.");
                        }
                    });
                }
                grilla.add(campo, c, f);
            }
        }
        pintarTablero();
    }

    private void pintarTablero() {
        for (int f = 0; f < 9; f++) {
            for (int c = 0; c < 9; c++) {
                String fondo = (f / 3 + c / 3) % 2 == 0
                        ? "rgba(255,255,255,0.65)" : "rgba(239,246,255,0.65)";
                String texto = "#1d4ed8";

                if (sudoku.esFija(f, c)) {
                    fondo = "rgba(226,232,240,0.85)";
                    texto = "#0f172a";
                } else if (f == ultimaFila && c == ultimaColumna) {
                    boolean valido = sudoku.esValido(f, c, sudoku.getNumero(f, c));
                    fondo = valido ? "rgba(187,247,208,0.90)" : "rgba(254,202,202,0.90)";
                    texto = valido ? "#166534" : "#991b1b";
                }

                int arriba = f % 3 == 0 ? 3 : 1;
                int derecha = c == 8 ? 3 : 1;
                int abajo = f == 8 ? 3 : 1;
                int izquierda = c % 3 == 0 ? 3 : 1;
                double fuente = Math.min(19, tamanoCasilla * 0.43);

                campos[f][c].setStyle(
                        "-fx-background-color: " + fondo + ";" +
                                "-fx-text-fill: " + texto + ";" +
                                "-fx-font-size: " + fuente + "px;" +
                                "-fx-font-weight: bold; -fx-alignment: center;" +
                                "-fx-background-radius: 0; -fx-border-radius: 0; -fx-padding: 0;" +
                                "-fx-border-color: #334155;" +
                                "-fx-border-width: " + arriba + " " + derecha + " " + abajo + " " + izquierda + ";"
                );
            }
        }
    }

    private void mostrarVictoria() {
        if (mostrandoVictoria) return;
        mostrandoVictoria = true;

        Platform.runLater(() -> {
            try {
                ButtonType ok = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Sudoku completado");
                alerta.setHeaderText("¡FELICIDADES, GANASTE!");
                alerta.setContentText(
                        "Has completado correctamente el Sudoku.\n\n" +
                                "¡Excelente trabajo!\n\n" +
                                "Presiona OK para comenzar una nueva partida."
                );
                alerta.getButtonTypes().setAll(ok);
                alerta.getDialogPane().setPrefWidth(450);
                alerta.initOwner(ventana);

                if (alerta.showAndWait().filter(ok::equals).isPresent()) {
                    sudoku.reiniciar();
                    construirTablero();
                    mensaje.setText("Nueva partida iniciada. ¡Buena suerte!");
                }
            } finally {
                mostrandoVictoria = false;
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
