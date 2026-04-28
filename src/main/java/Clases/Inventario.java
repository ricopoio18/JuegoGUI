package Clases;

import Interfaces.Inventariable;

import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private int capacidadMaxima;
    private List<Inventariable> items;

    public Inventario(int capacidadMaxima){
        this.capacidadMaxima = capacidadMaxima;
        items = new ArrayList<Inventariable>();
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public List<Inventariable> getItems() {
        return items;
    }

    public boolean agregarItem(Inventariable item){
        if (item == null){
            System.out.println("Item nulo");
            return false;
        }
        if(items.size() < capacidadMaxima){
            return items.add(item);
        } else {
            System.out.println("Inventario lleno");
            return false;
        }
    }

    public boolean eliminarItem(Inventariable item){
        return items.remove(item);
    }

    public void listarItems(){
        if(items.isEmpty()){
            System.out.println("Inventario vacío");
            return;
        }

        for(int i = 0; i < items.size(); i++){
            System.out.println((i + 1) + ". " + items.get(i));
        }
    }

    public Inventariable buscarPorNombre(String nombre){
        for (Inventariable item : items){
            if(item.toString().toLowerCase().contains(nombre.toLowerCase())){
                return item;
            }
        }
        return null;
    }

    @Override
    public String toString(){
        if(items.isEmpty()){
            return "Inventario vacío";
        }

        String resultado = "Inventario:\n";

        for(int i = 0; i < items.size(); i++){
            resultado += (i + 1) + ". " + items.get(i) + "\n";
        }

        return resultado;
    }
}