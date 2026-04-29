package Clases;

public class MotorCombate {

    private Personaje jugador;
    private Personaje enemigo;
    private Nivel nivel;

    private boolean juegoActivo = true;

    public MotorCombate(Personaje jugador, Personaje enemigo, Nivel nivel) {
        this.jugador = jugador;
        this.enemigo = enemigo;
        this.nivel = nivel;
    }

    public void actualizar() {

        if (!juegoActivo) return;

        if (!jugadorVivo() || !enemigoVivo()) {
            juegoActivo = false;
            return;
        }

        moverEnemigo();

        jugador.atacar(enemigo);
        enemigo.atacar(jugador);

        boolean fin = nivel.verificarRingOut(jugador, enemigo);

        if (fin) {
            juegoActivo = false;
        }
    }

    private void moverEnemigo() {
        if (enemigo.getPosicionX() < jugador.getPosicionX()) {
            enemigo.mover("este", 5);
        } else {
            enemigo.mover("oeste", 5);
        }
    }

    private boolean jugadorVivo() {
        return jugador.getVida() > 0;
    }

    private boolean enemigoVivo() {
        return enemigo.getVida() > 0;
    }

    public boolean isJuegoActivo() {
        return juegoActivo;
    }
}
