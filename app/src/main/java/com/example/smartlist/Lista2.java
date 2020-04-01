package com.example.smartlist;

public class Lista2 extends Lista {
    private double precio;
    private long id;

    public Lista2(String nombre, double precio, long id) {
        this.nombre = nombre;
        this.precio = precio;
        this.id = id;
    }

    @Override
    public String getNombre() {
        return null;
    }

}
