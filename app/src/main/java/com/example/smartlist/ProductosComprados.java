package com.example.smartlist;

public class ProductosComprados extends Producto {
    private int cantidad;
    private double precioTotal;
    private int idDataBase;

    public ProductosComprados(String id, String nombre, String descripcion, String marca, double precio){

        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.marca = marca;
        this.precio = precio;
    }

    public void setCantidad(int cantidad){ this.cantidad = cantidad; }
    public int getCantidad(){ return this.cantidad; }
    public void setPrecioTotal(double precioTotal){ this.precioTotal = precioTotal; }
    public double getPrecioTotal(){ return this.precioTotal; }
    public void setIdDataBase(int idDataBase){ this.idDataBase = idDataBase; }
    public int getIdDataBase(){ return this.idDataBase; }

    public String getJSON(){

        return "{\"id\":\""+ this.id +"\", " +
                "\"nombre\":\""+ this.nombre +"\"," +
                "\"cantidad\":\""+ this.cantidad +"\"," +
                "\"precio\":\""+ this.precio +"\"}";
    }

    public String getJSONAllCampos(){

        return "{\"id\":\""+ this.id +"\", " +
                "\"nombre\":\""+ this.nombre +"\"," +
                "\"marca\":\""+ this.marca +"\"," +
                "\"descripcion\":\""+ this.descripcion +"\"," +
                "\"precioTotal\":\""+ this.precioTotal +"\"," +
                "\"cantidad\":\""+ this.cantidad +"\"," +
                "\"precio\":\""+ this.precio +"\"}";
    }

}
