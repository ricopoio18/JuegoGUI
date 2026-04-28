package Clases;

import Interfaces.ElementoDinamico;

public class Utileria implements ElementoDinamico {
    private String nombre;
    private String descripcion;
    private int posicionX;
    private int posicionY;
    private boolean usada;

    public Utileria(String nombre, String descripcion, int posicionX, int posicionY){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        usada = false;
    }

    public String getNombre(){
        return nombre;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public int getPosicionX(){
        return posicionX;
    }

    public int getPosicionY(){
        return posicionY;
    }

    public boolean isUsada(){
        return usada;
    }

    public void usar(){
        if (!usada) {
            usada = true;
            System.out.println(nombre + " ha sido usado");
        } else {
            System.out.println(nombre + " ya fue usado");
        }
    }

    @Override
    public void mover(String direccion, int distancia){
        switch(direccion.toLowerCase()){
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
}

