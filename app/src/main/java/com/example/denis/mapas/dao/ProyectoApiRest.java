package com.example.denis.mapas.dao;

import android.util.Log;

import com.example.denis.mapas.modelo.Recorrido;
import com.example.denis.mapas.modelo.Usuario;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by martdominguez on 20/10/2016.
 */
public class ProyectoApiRest {

    public void crearRecorrido(Recorrido r) {

        RestClient cliRest = new RestClient();

        // Crear Object json a partir de r.

        cliRest.crearRecorrido(r, "recorridos");

    }

    public void borrarRecorrido(Integer id) {
        RestClient cliRest = new RestClient();
        cliRest.borrar(id, "recorridos");
    }

    public Integer getMaxId() throws JSONException {

        RestClient cliRest = new RestClient();

        // Crear Object json a partir de p.

        JSONArray j = cliRest.getByAll(null, "recorridos");

        Integer maxId = 0;

        for (int i = 0; i < j.length(); i++) {
            JSONObject actor = j.getJSONObject(i);
            String name = actor.getString("id");

            Integer val = Integer.parseInt(name);
            if (val > maxId)
                maxId = val;

            //Log.d("nooooombre ", name );
            //allNames.add(name);
        }

        return maxId + 1;

    }

    public Integer getMaxIdUser() throws JSONException {

        RestClient cliRest = new RestClient();

        // Crear Object json a partir de p.

        JSONArray j = cliRest.getByAll(null, "usuarios");

        Integer maxId = 0;

        for (int i = 0; i < j.length(); i++) {
            JSONObject actor = j.getJSONObject(i);
            String name = actor.getString("id");

            Integer val = Integer.parseInt(name);
            if (val > maxId)
                maxId = val;

            //Log.d("nooooombre ", name );
            //allNames.add(name);
        }

        return maxId + 1;

    }

    public void actualizarProyecto(Recorrido r) {

        RestClient cliRest = new RestClient();
        cliRest.actualizar(r, "recorridos");

    }

    /*public ArrayList<ArrayList> listarProyectos(){
        ArrayList<String> listaProyectos = new ArrayList<String>();
        ArrayList<Integer> idProyectos = new ArrayList<Integer>();
        ArrayList<ArrayList> resultado = new ArrayList<ArrayList>();
        RestClient cliRest = new RestClient();
        JSONArray array = cliRest.getByAll(null, "proyectos");
        for(int i=0; i<array.length(); i++){
            try {
                JSONObject o = array.getJSONObject(i);
                listaProyectos.add(o.getString("nombre"));
                idProyectos.add(o.getInt("id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        resultado.add(idProyectos);
        resultado.add(listaProyectos);
        Log.d("listaProyectosAString: ",listaProyectos.toString());
        return resultado;
    }*/

    public ArrayList<Recorrido> listarRecorridos() {
        ArrayList<Recorrido> listaRecorridos = new ArrayList<Recorrido>();
        RestClient cliRest = new RestClient();
        JSONArray array = cliRest.getByAll(null, "recorridos");
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject o = array.getJSONObject(i);
                Recorrido r = new Recorrido();

                r.setNombre_destino(o.getString("nombre_destino"));
                r.setNombre_origen(o.getString("nombre_origen"));
                r.setOrigen(new LatLng(Double.parseDouble(o.getString("origen_latitud")), Double.parseDouble(o.getString("origen_longitud"))));
                r.setDestino(new LatLng(Double.parseDouble(o.getString("destino_latitud")), Double.parseDouble(o.getString("destino_longitud"))));
                r.setEstado(o.getString("estado"));
                r.setId(o.getString("id"));
                Log.d("NOMBRE ORIGEN", r.getNombre_origen());

                if (r.getEstado() == "0") listaRecorridos.add(r);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return listaRecorridos;
    }

    public Recorrido buscarRecorrido(Integer id) {
        RestClient cliRest = new RestClient();
        JSONObject t = cliRest.getById(1, "proyectos");
        // transformar el objeto JSON a proyecto y retornarlo

        Recorrido p = new Recorrido();
        // try {
            /*Integer idS = t.getInt("id");
            p.setId(idS);*/

            /*String nombre = t.getString("nombre");
            p.setNombre(nombre);*/

        // } catch (JSONException e) {
        //     e.printStackTrace();
        // }
        return p;
    }

    public void verTareas(Recorrido p) {
        RestClient cliRest = new RestClient();
        //JSONArray array = cliRest.getByAll(0,"tareas?proyectoId="+p.getId());
        //Log.d("Tareas del Proyecto: "+p.getId(),array.toString());
    }

    public Integer existeUsuario(String nombre_usuario) {
        Integer idResultado = 0;
        RestClient cliRest = new RestClient();
        JSONArray jsonArray = cliRest.getByAll(null, "usuarios?nombre=" + nombre_usuario);
        if (jsonArray.length() > 0) {
            try {
                JSONObject o = jsonArray.getJSONObject(0);
                idResultado = o.getInt("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return idResultado;
    }

    public void crearUsuario(Usuario u) {
        RestClient cliRest = new RestClient();
        cliRest.crearUsuario(u, "usuarios");
    }
}