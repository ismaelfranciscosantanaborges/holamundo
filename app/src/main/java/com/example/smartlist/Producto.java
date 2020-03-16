package com.example.smartlist;

public class Producto {
    private String id;
    private String nombre;
    private double precio;
    private String descripcion;
    private String marca;

    public Producto(){ }
    public Producto(String id, String nombre, String descripcion, String marca, double precio){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.marca = marca;
        this.precio = precio;
    }

    public String getId(){return this.id;}
    public String getNombre(){return this.nombre;}
    public String getDescripcion(){return this.descripcion;}
    public String getMarca(){return this.marca;}
    public double getPrecio(){return this.precio;}
}
