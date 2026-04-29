package Clases;

import Interfaces.Destruible;
import Interfaces.ElementoDinamico;

public class Personaje implements Destruible, ElementoDinamico {
    private String nombre;
    private int vida;
    private double posicionX;
    private double posicionY;
    private boolean vivo;
    //Ataques
    private Arma arma;
    private long ultimoAtaque;
    private long tiempoEspera;

    public Personaje(String nombre, int vida, double posicionX, double posicionY, long tiempoEspera) {
        this.nombre = nombre;
        this.vida = vida;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.vivo = true;
        this.tiempoEspera = tiempoEspera;
    }

    public void setArma(Arma arma) {
        this.arma = arma;
    }

    public Arma getArma() {
        return arma;
    }

    public String getNombre(){
        return nombre;
    }

    public int getVida(){
        return vida;
    }

    public double getPosicionX(){
        return posicionX;
    }

    public double getPosicionY(){
        return posicionY;
    }

    public void setPosicion(double x, double y){
        posicionX = x;
        posicionY = y;
    }

    public void setPosicionX(double x){
        posicionX = x;
    }

    public void recibirDaño(int daño){

        vida = Math.max(0, vida - daño);
        if (vida == 0) {
            destruye();
        }
    }

    @Override
    public void mover(String direccion, int distancia){
        String cadena = direccion.toLowerCase();
        switch(cadena){
            case "norte":
                posicionY -= distancia;
                break;
            case "sur":
                posicionY += distancia;
                break;
            case "este":
                posicionX += distancia;
                break;
            case "oeste":
                posicionX -= distancia;
                break;

        }
    }

    public void atacar(Personaje enemigo) {
        long tiempoActual = System.currentTimeMillis();

        if (arma == null) {
            System.out.println(nombre + " no tiene arma equipada");
            return;
        }

        if (tiempoActual - ultimoAtaque < tiempoEspera) {
            return;
        }

        double distancia = Math.abs(this.posicionX - enemigo.posicionX);

        if (distancia <= arma.getAlcance()) {

            int empuje = arma.getDaño();

            if (this.posicionX < enemigo.posicionX) {
                enemigo.setPosicion(enemigo.getPosicionX() + empuje, enemigo.getPosicionY());
            } else {
                enemigo.setPosicion(enemigo.getPosicionX() - empuje, enemigo.getPosicionY());
            }

            System.out.println(nombre + " empuja a " + enemigo.getNombre());

            ultimoAtaque = tiempoActual;
        }
    }

    @Override
    public void destruye(){
        vivo = false;
        System.out.println(nombre + " ha sido eliminado");
    }


}
