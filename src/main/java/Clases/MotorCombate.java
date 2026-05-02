package Clases;

public class MotorCombate {
    private Personaje jugador;
    private Personaje enemigo;
    private Nivel nivel;
    private boolean juegoActivo = true;

    // Lógica de cooldown trasladada
    private int empujes = 0;
    private final int MAX_EMPUJES = 5;
    private boolean enCooldown = false;
    private long tiempoEspera = 200;
    private long inicioCooldown = 0;


    public MotorCombate(Personaje jugador, Personaje enemigo, Nivel nivel) {
        this.jugador = jugador;
        this.enemigo = enemigo;
        this.nivel = nivel;
    }

    public void actualizar(boolean espacioPresionado) {
        if (!juegoActivo) return;

        // gestión del cooldown
        if (enCooldown) {
            if (System.currentTimeMillis() - inicioCooldown >= tiempoEspera) {
                enCooldown = false;
                empujes = 0;
            }
        }

        boolean estaEmpujando = false;
        if (espacioPresionado && !enCooldown) {
            estaEmpujando = true;
            empujes++;
            if (empujes >= MAX_EMPUJES) {
                enCooldown = true;
                inicioCooldown = System.currentTimeMillis();
            }
        }

        // Movimiento automático
        jugador.mover("este", 2);
        moverIAEnemigo();

        // Física de colisión y empuje
        double dist = Math.abs(jugador.getPosicionX() - enemigo.getPosicionX());
        if (dist <= 60) {
            double fuerzaJugador = estaEmpujando ? 10.0 : 0.0;
            double fuerzaEnemigo = 2.5; // Fuerza base del enemigo

            // Reposicionar para que no se traspasen
            double centro = (jugador.getPosicionX() + enemigo.getPosicionX()) / 2;
            jugador.setPosicionX(centro - 30);
            enemigo.setPosicionX(centro + 30);

            // Aplicar desplazamiento resultante
            double resultado = fuerzaJugador - fuerzaEnemigo;
            jugador.setPosicionX(jugador.getPosicionX() + resultado);
            enemigo.setPosicionX(enemigo.getPosicionX() + resultado);
        }

        // verifica los límites del ring
        if (nivel.verificarRingOut(jugador, enemigo)) {
            juegoActivo = false;

        }
    }

    private void moverIAEnemigo() {
        if (enemigo.getPosicionX() > jugador.getPosicionX()) {
            enemigo.mover("oeste", 2);
        } else {
            enemigo.mover("este", 2);
        }
    }

    public boolean isJuegoActivo() { return juegoActivo; }
}