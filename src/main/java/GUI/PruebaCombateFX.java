
package GUI;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PruebaCombateFX extends Application {

    double velocidadJugador = 2;
    double velocidadEnemigo = -2 ;

    int empujes = 0;
    final int MAX_EMPUJES = 5;

    boolean enCooldown = false;
    long tiempoEspera = 200;
    long inicioCooldown = 0;

    boolean espacioPresionado = false;

    @Override
    public void start(Stage stage) {

        Pane root = new Pane();

        //Fondo
        Image fondoImg = new Image(
                getClass().getResource("/img/fondo.jpg").toExternalForm()
        );
        ImageView fondo = new ImageView(fondoImg);
        fondo.setFitWidth(800);
        fondo.setFitHeight(400);

// 🪵 Tronco
        Image imgRing = new Image(
                getClass().getResource("/img/tronco.png").toExternalForm()
        );
        ImageView ring = new ImageView(imgRing);

        ring.setX(50);
        ring.setY(240);
        ring.setFitWidth(700);
        ring.setFitHeight(200);

        // jugador
        Image jugadorIdle = new Image(
                getClass().getResource("/img/jugador_idle.gif").toExternalForm()
        );

        Image jugadorMove = new Image(
                getClass().getResource("/img/jugador_move.gif").toExternalForm()
        );
        ImageView jugador = new ImageView(jugadorIdle);
        jugador.setX(100);
        jugador.setY(180);
        jugador.setFitWidth(100);
        jugador.setFitHeight(100);

        //enemigo
        Image enemigoIdle = new Image(
                getClass().getResource("/img/enemigo_idle.gif").toExternalForm()
        );

        Image enemigoMove = new Image(
                getClass().getResource("/img/enemigo_move.gif").toExternalForm()
        );
        ImageView enemigo = new ImageView(enemigoIdle);
        enemigo.setX(700);
        enemigo.setY(180);
        enemigo.setFitWidth(100);
        enemigo.setFitHeight(100);

        // Voltear enemigo para que se vea de frente
        enemigo.setScaleX(-1);

        root.getChildren().addAll(fondo, ring, jugador, enemigo);

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

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // cooldown
                if (enCooldown) {
                    if (System.currentTimeMillis() - inicioCooldown >= tiempoEspera) {
                        enCooldown = false;
                        empujes = 0;
                        System.out.println("Puedes empujar otra vez");
                    }
                }
                boolean empujandoJugador = false;

                if (espacioPresionado && !enCooldown) {
                    empujandoJugador = true;
                    empujes++;

                    System.out.println("Empuje #" + empujes);

                    if (empujes >= MAX_EMPUJES) {
                        enCooldown = true;
                        inicioCooldown = System.currentTimeMillis();
                        System.out.println("Cansado...");
                    }
                }

                // Movimiento normal
                jugador.setX(jugador.getX() + velocidadJugador);
                enemigo.setX(enemigo.getX() + velocidadEnemigo);

                // Distancia entre ellos
                double distancia = Math.abs(jugador.getX() - enemigo.getX());

                // COLISIÓN
                if (distancia <= 60) {

                    double fuerzaJugador = 0.0;
                    double fuerzaEnemigo = 2.5;

                    if (empujandoJugador) {
                        fuerzaJugador = 10.0;
                    }

                    double centro = (jugador.getX() + enemigo.getX()) / 2;

                    jugador.setX(centro - 30);
                    enemigo.setX(centro + 30);

                    double empuje = fuerzaJugador - fuerzaEnemigo;

                    jugador.setX(jugador.getX() + empuje);
                    enemigo.setX(enemigo.getX() + empuje);
                }

                // Límites del ring
                if (jugador.getX() <= 10 || jugador.getX() >= 705) {
                    stop();
                    System.out.println("Enemigo gana");
                }

                if (enemigo.getX() <= 10 || enemigo.getX() >= 705) {
                    stop();
                    System.out.println("Jugador gana");
                }
            }
        };

        timer.start();

        stage.setTitle("Combate de Escarabajos");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}