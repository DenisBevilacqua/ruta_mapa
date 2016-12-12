package com.example.denis.mapas.recorridos;

import com.example.denis.mapas.modelo.Recorrido;

import java.util.Comparator;


public class RecorridoComparator implements Comparator<Recorrido>{
    @Override
    public int compare(Recorrido r1, Recorrido r2) {
        int resutado = (int) (r2.getDistanciaOrigen() - r1.getDistanciaOrigen());
        return resutado;
    }
}
