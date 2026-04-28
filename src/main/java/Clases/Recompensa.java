package Clases;

import Interfaces.Inventariable;

public class Recompensa implements Inventariable {
    private String nombre;
    private int valor;
    private String tipo;
    private Inventario inventario;

    public Recompensa(String nombre, int valor, String tipo) {
        this.nombre = nombre;
        this.valor = valor;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getValor(){
        return valor;
    }

    public String getTipo(){
        return tipo;
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
                System.out.println(nombre + " eliminada del inventario");
            }
        } else {
            System.out.println("Inventario no asignado");
        }
    }

    public String toString(){
        return "\nRecompensa: " + nombre + "\n Valor: " + valor + "\n Tipo: " + tipo;
    }
}
