package Clases;

import Interfaces.Destruible;

public class Obstaculo implements Destruible {
    private String nombre;
    private int daño;
    private int posicionX;
    private int posicionY;
    private boolean destruido;

    public Obstaculo(String nombre, int daño,int posicionX, int posicionY){
        this.nombre = nombre;
        this.daño = daño;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.destruido = false;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDaño(){
        return daño;
    }

    public int getPosicionX(){
        return posicionX;
    }

    public int getPosicionY(){
        return posicionY;
    }

    public boolean isDestruido(){
        return destruido;
    }

    @Override
    public void destruye(){
        destruido = true;
        System.out.println(nombre + " ha sido destruido");
    }

}
