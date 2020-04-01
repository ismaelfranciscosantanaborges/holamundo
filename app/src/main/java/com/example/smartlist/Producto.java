package com.example.smartlist;

public class Producto {
    protected String id;
    protected String img = "https://i.pinimg.com/originals/31/86/74/318674fe9146c20e3096bcd86b325d70.jpg";
    protected String nombre;
    protected double precio;
    protected String descripcion;
    protected String marca;

    //CONTRUCTOR PARA FIREBASE
    public Producto(){}

    public Producto(String id, String nombre, String descripcion, String marca, double precio){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.marca = marca;
        this.precio = precio;
    }

    public void setId(String id){this.id = id;}
    public String getId(){return this.id;}
    public String getNombre(){return this.nombre;}
    public String getDescripcion(){return this.descripcion;}
    public String getMarca(){return this.marca;}
    public double getPrecio(){return this.precio;}


    public void setImg(String img){ this.img = img; }
    public String getImg(){ return this.img; }


}
