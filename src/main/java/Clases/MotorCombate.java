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

    public void iniciar() {

        System.out.println("((( INICIA EL COMBATE )))");

        while (juegoActivo) {

            actualizar();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("))) FIN DEL COMBATE (((");
    }

    private void actualizar() {

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
            return;
        }

        mostrarEstado();
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

    private void mostrarEstado() {
        System.out.println(
                jugador.getNombre() + " Posición en x: " + jugador.getPosicionX() +
                        " | " +
                        enemigo.getNombre() + " Posicion en x: " + enemigo.getPosicionX()
        );
    }
}
