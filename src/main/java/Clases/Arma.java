package Clases;

import Interfaces.Inventariable;

public class Arma implements Inventariable {
    private String nombre;
    private int daño;
    private double alcance;
    private Inventario inventario;

    public Arma(String nombre, int daño, double alcance){
        this.nombre = nombre;
        this.daño = daño;
        this.alcance = alcance;
    }

    public String getNombre(){
        return nombre;
    }

    public int getDaño(){
        return daño;
    }

    public double getAlcance(){
        return alcance;
    }

    public void setInventario(Inventario inventario){
        this.inventario = inventario;
    }

    @Override
    public void registrar(){
        if(inventario != null){
            boolean agregado = inventario.agregarItem(this);
            if(agregado){
                System.out.println(nombre + " agregado al inventario");
            }
        } else {
            System.out.println("Inventario no asignado");
        }
    }

    @Override
    public void borrar(){
        if(inventario != null){
            boolean eliminado = inventario.eliminarItem(this);
            if(eliminado){
                System.out.println(nombre + " eliminado del inventario");
            }
        } else {
            System.out.println("Inventario no asignado");
        }
    }

    public String toString(){
        return "\nArma: " + nombre + "\n Daño: " + daño + "\n Alcance: " + alcance;
    }
}
