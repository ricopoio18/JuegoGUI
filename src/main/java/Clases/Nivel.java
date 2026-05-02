package Clases;

import Interfaces.ElementoDinamico;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Nivel {
    private String nombre;
    private int numero;
    private String dificultad;
    private List<Obstaculo> obstaculos;
    private List<CheckPoint> checkPoints;
    private List<ElementoDinamico> elementosDinamicos;
    private Inventario inventario;
    //Limites del ring
    private int limiteIzquierdo = 0;
    private int limiteDerecho = 700;

    public Nivel(String nombre, int numero, String dificultad,Inventario inventario){
        this.nombre = nombre;
        this.numero = numero;
        this.dificultad = dificultad;
        this.inventario = inventario;
        this.obstaculos = new ArrayList<>();
        this.checkPoints = new ArrayList<>();
        this.elementosDinamicos = new ArrayList<>();
    }

    public String getNombre(){
        return nombre;
    }

    public int getNumero(){
        return numero;
    }

    public String getDificultad(){
        return dificultad;
    }

    public void agregarObstaculo(Obstaculo obstaculo){
        obstaculos.add(obstaculo);
    }

    public void agregarCheckPoint(CheckPoint checkPoint){
        checkPoints.add(checkPoint);
    }

    public void agregarElementoDinamico(ElementoDinamico elemento){
        elementosDinamicos.add(elemento);
    }

    public void moverElementosDinamicos(){
        Random rm = new Random();
        String[] direccion = {"norte","sur","este","oeste"};

        for(ElementoDinamico elemento : elementosDinamicos){
            elemento.mover(direccion[rm.nextInt(4)], rm.nextInt(500) );
        }
    }

    public void mostrarEstado(){
        System.out.println("Nombre: "+ nombre + "\nNúmero: " + numero + "\nDificultad: " + dificultad +
        "\nInventario: " + inventario + "CheckPoints: " + checkPoints.size() );
    }

    public boolean verificarRingOut(Personaje p1, Personaje p2) {

        if (p1.getPosicionX() < limiteIzquierdo || p1.getPosicionX() > limiteDerecho) {
            p1.destruye();
            System.out.println(p2.getNombre() + " gana!");
            return true;
        }

        if (p2.getPosicionX() < limiteIzquierdo || p2.getPosicionX() > limiteDerecho) {
            p2.destruye();
            System.out.println(p1.getNombre() + " gana!");
            return true;
        }

        return false;
    }
}
