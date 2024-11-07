package org.example;

public class Recurso {
    int id;
    String nombre;
    boolean enUso;
    int ubicacion;


    public Recurso(int id, String nombre, boolean enUso, int ubi) {
        this.id = id;
        this.nombre = nombre;
        this.enUso = enUso;
        this.ubicacion = ubi;
    }

    public int getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(int ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEnUso() {
        return enUso;
    }

    public void setEnUso(boolean enUso) {
        this.enUso = enUso;
    }
}
