package com.example.denis.mapas.modelo;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by denis on 06/12/16.
 */

public class Recorrido implements Serializable {

    public static LatLng origen;
    public static LatLng destino;

    public static String id;
    public static Double origen_longitud;
    public static Double origen_latitud;
    public static Double destino_latitud;
    public static Double destino_longitud;

    public static String nombre_origen;
    public static String nombre_destino;

    public static String estado;

    public Recorrido(){

    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        Recorrido.id = id;
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

    public static Double getOrigen_longitud() {
        return origen_longitud;
    }

    public static void setOrigen_longitud(Double origen_longitud) {
        Recorrido.origen_longitud = origen_longitud;
    }

    public static Double getOrigen_latitud() {
        return origen_latitud;
    }

    public static void setOrigen_latitud(Double origen_latitud) {
        Recorrido.origen_latitud = origen_latitud;
    }

    public static Double getDestino_latitud() {
        return destino_latitud;
    }

    public static void setDestino_latitud(Double destino_latitud) {
        Recorrido.destino_latitud = destino_latitud;
    }

    public static Double getDestino_longitud() {
        return destino_longitud;
    }

    public static void setDestino_longitud(Double destino_longitud) {
        Recorrido.destino_longitud = destino_longitud;
    }

    public static String getEstado() {
        return estado;
    }

    public static void setEstado(String estado) {
        Recorrido.estado = estado;
    }

    public JSONObject toJSON(){

        JSONObject jsonObject= new JSONObject();

        try {

            jsonObject.put("origen_latitud", getOrigen_latitud()+"");
            jsonObject.put("destino_latitud", getDestino_latitud()+"");
            jsonObject.put("origen_longitud", getOrigen_longitud()+"");
            jsonObject.put("destino_longitud", getDestino_longitud()+"");
            jsonObject.put("nombre_origen", getNombre_origen()+"");
            jsonObject.put("nombre_destino", getNombre_destino()+"");
            jsonObject.put("estado", 0);

            return jsonObject;

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }
}
