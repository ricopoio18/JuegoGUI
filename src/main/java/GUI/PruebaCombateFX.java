package GUI;

import Clases.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PruebaCombateFX extends Application {

    private Personaje jugador;
    private Personaje enemigo;
    private Nivel nivel;
    private MotorCombate motor;
    private GestorJuego gestor;
    private boolean esperandoDecision;

    boolean espacioPresionado = false;

    @Override
    public void start(Stage stage) {

        Pane root = new Pane();

        jugador = new Personaje("Jugador", 100, 100, 180, 200);
        enemigo = new Personaje("Enemigo", 100, 670, 180, 200);

        nivel = new Nivel("Ring", 1, "Normal", new Inventario(10));

        jugador.setArma(new Arma("Empuje", 10, 60));
        enemigo.setArma(new Arma("Empuje", 5, 60));

        Image fondoImg = new Image(
                getClass().getResource("/img/fondo.jpg").toExternalForm()
        );
        ImageView fondo = new ImageView(fondoImg);
        fondo.setFitWidth(800);
        fondo.setFitHeight(400);

        Image imgRing = new Image(
                getClass().getResource("/img/tronco.png").toExternalForm()
        );
        ImageView ring = new ImageView(imgRing);
        ring.setX(50);
        ring.setY(240);
        ring.setFitWidth(700);
        ring.setFitHeight(200);

        Image jugadorIdle = new Image(
                getClass().getResource("/img/jugador_idle.gif").toExternalForm()
        );
        Image jugadorMove = new Image(
                getClass().getResource("/img/jugador_move.gif").toExternalForm()
        );
        ImageView jugadorView = new ImageView(jugadorIdle);
        jugadorView.setFitWidth(100);
        jugadorView.setFitHeight(100);

        Image enemigoIdle = new Image(
                getClass().getResource("/img/enemigo_idle.gif").toExternalForm()
        );
        Image enemigoMove = new Image(
                getClass().getResource("/img/enemigo_move.gif").toExternalForm()
        );
        ImageView enemigoView = new ImageView(enemigoIdle);
        enemigoView.setFitWidth(100);
        enemigoView.setFitHeight(100);
        enemigoView.setScaleX(-1);

        root.getChildren().addAll(fondo, ring, jugadorView, enemigoView);

        Scene scene = new Scene(root, 800, 400);

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case RIGHT:
                    espacioPresionado = true;
                    break;
            }
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode().toString().equals("RIGHT")) {
                espacioPresionado = false;
            }
        });

        gestor = new GestorJuego();
        nivel = gestor.getNivelActual();
        motor = new MotorCombate(jugador, enemigo, nivel);

        VBox panelGuardar = new VBox(15);
        panelGuardar.setStyle("-fx-background-color: rgba(0,0,0,0.8); -fx-padding: 20; -fx-alignment: center; -fx-background-radius: 15;");
        panelGuardar.setLayoutX(300);
        panelGuardar.setLayoutY(120);
        panelGuardar.setPrefWidth(200);
        panelGuardar.setVisible(false);

        Label textoPregunta = new Label("¿Deseas guardar partida?");
        textoPregunta.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        Label lblNivel = new Label("NIVEL: " + gestor.getNumeroNivel());
        lblNivel.setStyle("-fx-text-fill: white; " +
                "-fx-font-size: 40px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.9), 15, 0, 3, 3);");
        lblNivel.setLayoutX(340);
        lblNivel.setLayoutY(20);

        root.getChildren().add(lblNivel);

        Button btnSi = new Button("SÍ");
        Button btnNo = new Button("NO");
        btnSi.setMinWidth(100);
        btnNo.setMinWidth(100);
        btnSi.setOnAction(e -> {
            gestor.jugadorGana(jugador, true);
            panelGuardar.setVisible(false);
            esperandoDecision = false;
            resetearCombate();
        });

        btnNo.setOnAction(e -> {
            gestor.jugadorGana(jugador, false);
            panelGuardar.setVisible(false);
            esperandoDecision = false;
            resetearCombate();
        });
        panelGuardar.getChildren().addAll(textoPregunta, btnSi, btnNo);
        root.getChildren().add(panelGuardar);

        // Contenedor de Victoria Final
        VBox panelVictoria = new VBox(20);
        panelVictoria.setStyle("-fx-background-color: rgba(0,0,0,0.85); " +
                "-fx-border-color: #FFD700; -fx-border-width: 3; " + // Borde dorado
                "-fx-padding: 30; -fx-alignment: center; -fx-background-radius: 20;");
        panelVictoria.setLayoutX(250);
        panelVictoria.setLayoutY(100);
        panelVictoria.setPrefWidth(300);
        panelVictoria.setVisible(false);

        Label textoVictoria = new Label("¡Ganaste!");
        textoVictoria.setStyle("-fx-text-fill: #FFD700; -fx-font-size: 26px; -fx-font-weight: bold;");

        Button btnSalir = new Button("SALIR DEL JUEGO");
        btnSalir.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black; -fx-font-weight: bold;");
        btnSalir.setMinWidth(150);


        btnSalir.setOnAction(e -> stage.close());

        panelVictoria.getChildren().addAll(textoVictoria, btnSalir);
        root.getChildren().add(panelVictoria);
        esperandoDecision = false;

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (esperandoDecision) return;

                if (gestor.isJuegoTerminado()) {
                    panelVictoria.setVisible(true);
                    stop();
                    return;
                }

                motor.actualizar(espacioPresionado);
                lblNivel.setText("NIVEL: " + gestor.getNumeroNivel());
                jugadorView.setX(jugador.getPosicionX());
                jugadorView.setY(jugador.getPosicionY());

                enemigoView.setX(enemigo.getPosicionX());
                enemigoView.setY(enemigo.getPosicionY());


                double distancia = Math.abs(jugador.getPosicionX() - enemigo.getPosicionX());

                if (distancia <= 65 || !motor.isJuegoActivo()) {
                    //manejo de estados
                    if (jugadorView.getImage() != jugadorIdle) jugadorView.setImage(jugadorIdle);
                    if (enemigoView.getImage() != enemigoIdle) enemigoView.setImage(enemigoIdle);
                } else {
                    if (jugadorView.getImage() != jugadorMove) jugadorView.setImage(jugadorMove);
                    if (enemigoView.getImage() != enemigoMove) enemigoView.setImage(enemigoMove);
                }

                // detiene el timer si el motor dice que el juego acabó
                if (!motor.isJuegoActivo()) {

                    if (enemigo.getPosicionX() >= 670 || enemigo.getPosicionX() <= 10) {
                        // ¡Ganó! Pero antes de procesar el nivel, preguntamos:
                        esperandoDecision = true;
                        if (gestor.getNumeroNivel() < 2){
                            panelGuardar.setVisible(true);
                        } else {
                            gestor.jugadorGana(jugador, false);
                            esperandoDecision = false;
                        };
                    } else {

                        gestor.jugadorPierde(jugador);
                        resetearCombate();
                    }

                }
            }
        };
        timer.start();

        stage.setTitle("Beatle Battles");
        stage.setScene(scene);
        stage.show();

    }
    private void resetearCombate() {
        if (gestor.isJuegoTerminado()) return;

        jugador.setPosicion(100, 190);
        enemigo.setPosicion(670, 190);
        gestor.configurarEnemigo(enemigo);
        motor = new MotorCombate(jugador, enemigo, gestor.getNivelActual());
    }


    public static void main(String[] args) {
        launch();
    }
}