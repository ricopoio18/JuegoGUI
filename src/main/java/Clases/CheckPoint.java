package Clases;

public class CheckPoint {
    private int nivel;
    private double posicionX;
    private double posicionY;
    private boolean activado;

    public CheckPoint(int nivel, double posicionX, double posicionY) {
        this.nivel = nivel;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.activado = false;
    }

    public int getNivel() {
        return nivel;
    }
    public double getPosicionX() {
        return posicionX;
    }

    public double getPosicionY(){
        return posicionY;
    }

    public boolean isActivado() {
        return activado;
    }

    public void activar(){
        activado = true;
    }
}
