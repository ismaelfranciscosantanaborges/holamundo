package com.example.smartlist;

public class Lista1 extends Lista {

    private boolean productoComprado;

    public Lista1(String nombre, int cantidad, boolean productoComprado){

        this.nombre = nombre;
        this.cantidad = cantidad;
        this.productoComprado = productoComprado;

    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public int getCantidad() {
        return cantidad;
    }

    public boolean getProductoComprado(){
        return productoComprado;
    }
}
