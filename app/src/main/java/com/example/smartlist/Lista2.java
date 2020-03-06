package com.example.smartlist;

public class Lista2 extends Lista {
    private double precio;
    private long id;

    public Lista2(String nombre, int cantidad, double precio, long id) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.id = id;
    }

    @Override
    public String getNombre() {
        return null;
    }

    @Override
    public int getCantidad() {
        return 0;
    }
}
