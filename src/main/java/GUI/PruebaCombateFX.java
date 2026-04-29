package GUI;

import Clases.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PruebaCombateFX extends Application {

    private Personaje jugador;
    private Personaje enemigo;
    private Nivel nivel;

    boolean espacioPresionado = false;

    @Override
    public void start(Stage stage) {

        Pane root = new Pane();

        jugador = new Personaje("Jugador", 100, 100, 180, 200);
        enemigo = new Personaje("Enemigo", 100, 700, 180, 200);

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
                case SPACE:
                    espacioPresionado = true;
                    break;
            }
        });

        scene.setOnKeyReleased(e -> {
            if (e.getCode().toString().equals("SPACE")) {
                espacioPresionado = false;
            }
        });
// Crea la instancia fuera del Timer
        // Crea la instancia fuera del Timer
        // --- Dentro de tu método start() ---

        MotorCombate motor = new MotorCombate(jugador, enemigo, nivel);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // 1. El motor procesa toda la lógica
                motor.actualizar(espacioPresionado);

                // 2. Sincronizamos las vistas (ImageView) con el modelo (Personaje)
                jugadorView.setX(jugador.getPosicionX());
                jugadorView.setY(jugador.getPosicionY()); // <-- IMPORTANTE

                enemigoView.setX(enemigo.getPosicionX());
                enemigoView.setY(enemigo.getPosicionY());

                // 3. Lógica de GIFs (Movimiento constante vs Colisión)
                double distancia = Math.abs(jugador.getPosicionX() - enemigo.getPosicionX());

                if (distancia <= 65 || !motor.isJuegoActivo()) {
                    // Si están chocando o el juego terminó, ponemos IDLE
                    if (jugadorView.getImage() != jugadorIdle) jugadorView.setImage(jugadorIdle);
                    if (enemigoView.getImage() != enemigoIdle) enemigoView.setImage(enemigoIdle);
                } else {
                    // Si están lejos, se están acercando (caminando)
                    if (jugadorView.getImage() != jugadorMove) jugadorView.setImage(jugadorMove);
                    if (enemigoView.getImage() != enemigoMove) enemigoView.setImage(enemigoMove);
                }

                // 4. Detener el timer si el motor dice que el juego acabó
                if (!motor.isJuegoActivo()) {
                    stop();
                    System.out.println("Combate finalizado");
                }
            }
        };
        timer.start();

        stage.setTitle("Combate de Escarabajos PRO");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}