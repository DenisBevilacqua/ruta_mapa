package com.example.denis.mapas.modelo;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by denis on 06/12/16.
 */

public class Recorrido implements Serializable {

    private transient LatLng origen;
    private transient LatLng destino;

    private transient String id;
    private transient Double origen_longitud;
    private transient Double origen_latitud;
    private transient Double destino_latitud;
    private transient Double destino_longitud;

    private transient Double distanciaOrigen;

    private transient String nombre_origen;
    private transient String nombre_destino;

    private String estado;


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LatLng getOrigen() {
        return origen;
    }

    public void setOrigen(LatLng origen) {
        this.origen = origen;
    }

    public LatLng getDestino() {
        return destino;
    }

    public void setDestino(LatLng destino) {
        this.destino = destino;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getOrigen_longitud() {
        return origen_longitud;
    }

    public void setOrigen_longitud(Double origen_longitud) {
        this.origen_longitud = origen_longitud;
    }

    public Double getOrigen_latitud() {
        return origen_latitud;
    }

    public void setOrigen_latitud(Double origen_latitud) {
        this.origen_latitud = origen_latitud;
    }

    public Double getDestino_latitud() {
        return destino_latitud;
    }

    public void setDestino_latitud(Double destino_latitud) {
        this.destino_latitud = destino_latitud;
    }

    public Double getDestino_longitud() {
        return destino_longitud;
    }

    public void setDestino_longitud(Double destino_longitud) {
        this.destino_longitud = destino_longitud;
    }

    public String getNombre_origen() {
        return nombre_origen;
    }

    public void setNombre_origen(String nombre_origen) {
        this.nombre_origen = nombre_origen;
    }

    public String getNombre_destino() {
        return nombre_destino;
    }

    public void setNombre_destino(String nombre_destino) {
        this.nombre_destino = nombre_destino;
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
            jsonObject.put("id", getId());

            return jsonObject;

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }
}
