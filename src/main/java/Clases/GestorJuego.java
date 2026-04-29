package Clases;

public class GestorJuego {

    private Nivel nivelActual;
    private int numeroNivel = 1;
    private boolean juegoTerminado = false;

    private CheckPoint checkPointActual = null;

    public GestorJuego() {
        cargarNivel(1);
    }

    public void cargarNivel(int numero) {
        this.numeroNivel = numero;
        this.nivelActual = new Nivel("Nivel " + numero, numero, "Normal", new Inventario(10));

        System.out.println("Cargando nivel " + numero);
    }

    public Nivel getNivelActual() {
        return nivelActual;
    }

    public int getNumeroNivel() {
        return numeroNivel;
    }

    public void configurarEnemigo(Personaje enemigo) {

        if (numeroNivel == 1) {

            enemigo.setArma(new Arma("Empuje", 1, 60));
        } else {

            enemigo.setArma(new Arma("Empuje", 5, 60));
        }
    }

    public void guardarPartida(Personaje jugador) {
        checkPointActual = new CheckPoint(
                numeroNivel,
                jugador.getPosicionX(),
                jugador.getPosicionY()
        );

        System.out.println("Checkpoint guardado en nivel " + numeroNivel);
    }

    public void cargarDesdeCheckpoint(Personaje jugador) {

        if (checkPointActual != null) {
            this.numeroNivel = checkPointActual.getNivel();
            cargarNivel(numeroNivel);

            jugador.setPosicion(
                    checkPointActual.getPosicionX(),
                    checkPointActual.getPosicionY()
            );

        }
    }

    public void jugadorGana(Personaje jugador, boolean guardar) {
        if (guardar) {
            guardarPartida(jugador);
        }

        if (numeroNivel < 2) {
            numeroNivel++;
            cargarNivel(numeroNivel);
        } else {
            juegoTerminado = true;
        }
    }

    public void jugadorPierde(Personaje jugador) {

        if (checkPointActual != null) {
            cargarDesdeCheckpoint(jugador);
        } else {
            cargarNivel(1);
        }
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }
}