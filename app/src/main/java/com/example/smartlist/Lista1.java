package com.example.smartlist;

public class Lista1 extends Lista {

    private String id;
    private boolean productoComprado;
    private int cantidad;
    private String nombreLista;

    public Lista1(String nombre, int cantidad, boolean productoComprado){

        this.nombre = nombre;
        this.cantidad = cantidad;
        this.productoComprado = productoComprado;

    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    @Override
    public String getNombre() {
        return nombre;
    }

    public void setCantidad(int cantidad){
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public boolean getProductoComprado(){
        return productoComprado;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){ return this.id;}

    public void setNombreLista(String nombreLista){
        this.nombreLista = nombreLista;
    }

    public String getNombreLista(){return nombreLista;}
}
