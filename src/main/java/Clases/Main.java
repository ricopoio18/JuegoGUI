package Clases;

public class Main {
    public static void main(String[] args) {

//        // crear inventario
//        Inventario inventario = new Inventario(5);
//
//        // crear objetos inventariables
//        Arma espada = new Arma("Espada", 50, 1.5);
//        Recompensa moneda = new Recompensa("Moneda", 100, "oro");
//
//        // asignar inventario a los objetos
//        espada.setInventario(inventario);
//        moneda.setInventario(inventario);
//
//        // registrar items en el inventario
//        espada.registrar();
//        moneda.registrar();
//
//        // mostrar inventario
//        System.out.println("\nInventario:");
//        inventario.listarItems();
//
//        // eliminar un item
//        espada.borrar();
//
//        // mostrar inventario actualizado
//        System.out.println("\nInventario después de borrar:");
//        inventario.listarItems();
//
//        // crear personaje
//        Personaje jugador = new Personaje("Heroe", 100, 0, 0);
//
//        // crear obstáculo
//        Obstaculo roca = new Obstaculo("Roca", 20, 5, 5);
//
//        // crear checkpoint
//        CheckPoint checkPoint = new CheckPoint("CP1", 10, 10);
//
//        // crear utileria
//        Utileria caja = new Utileria("Caja", "Movible", 2, 2);
//
//        // crear nivel con el inventario
//        Nivel nivel = new Nivel("Bosque", 1, "Normal", inventario);
//
//        // agregar elementos al nivel
//        nivel.agregarObstaculo(roca);
//        nivel.agregarCheckPoint(checkPoint);
//        nivel.agregarElementoDinamico(jugador);
//        nivel.agregarElementoDinamico(caja);
//
//        // mostrar estado del nivel
//        System.out.println("\nEstado del nivel:");
//        nivel.mostrarEstado();
//
//        // mover personaje
//        jugador.mover("norte", 3);
//        System.out.println("Posición jugador: (" + jugador.getPosicionX() + ", " + jugador.getPosicionY() + ")");
//
//        // mover elementos automáticamente
//        nivel.moverElementosDinamicos();
//
//        // mostrar posiciones después del movimiento
//        System.out.println("\nDespués de mover:");
//        System.out.println("Jugador: (" + jugador.getPosicionX() + ", " + jugador.getPosicionY() + ")");
//        System.out.println("Caja: (" + caja.getPosicionX() + ", " + caja.getPosicionY() + ")");
//
//        // aplicar daño al personaje
//        jugador.recibirDaño(120);
//
//        // destruir obstáculo
//        roca.destruye();
//
//        // activar checkpoint
//        checkPoint.activar();
//        System.out.println("\nCheckpoint activado: " + checkPoint.isActivado());

        Personaje jugador = new Personaje("Pelotero", 100, 100, 0,2000);
        Personaje enemigo = new Personaje("Ciervo", 100, 500, 0, 2000);

        Arma formaInicial = new Arma("Pelotero", 20, 50);
        Arma formaEnemigo = new Arma("Ciervo", 30, 40);

        jugador.setArma(formaInicial);
        enemigo.setArma(formaEnemigo);

        Nivel nivel = new Nivel("Tronco", 1, "Normal", new Inventario(10));

        MotorCombate motor = new MotorCombate(jugador, enemigo, nivel);

        motor.iniciar();

    }
}