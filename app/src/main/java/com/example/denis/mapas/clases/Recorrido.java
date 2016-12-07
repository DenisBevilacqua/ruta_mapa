package com.example.denis.mapas.clases;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by denis on 06/12/16.
 */

public class Recorrido implements Serializable {

    public static LatLng origen;
    public static LatLng destino;

    public static String nombre_origen;
    public static String nombre_destino;

    public Recorrido(){

    }

    public LatLng getOrigen() {
        return origen;
    }

    public void setOrigen(LatLng origen) {
        this.origen = origen;
    }

    public String getNombre_origen() {
        return nombre_origen;
    }

    public void setNombre_origen(String nombre_origen) {
        this.nombre_origen = nombre_origen;
    }

    public LatLng getDestino() {
        return destino;
    }

    public void setDestino(LatLng destino) {
        this.destino = destino;
    }

    public String getNombre_destino() {
        return nombre_destino;
    }

    public void setNombre_destino(String nombre_destino) {
        this.nombre_destino = nombre_destino;
    }
}
