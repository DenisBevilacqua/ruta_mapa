package com.example.denis.mapas.recorridos;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.example.denis.mapas.R;
import com.example.denis.mapas.dao.ProyectoApiRest;
import com.example.denis.mapas.modelo.Recorrido;

import java.util.ArrayList;

/**
 * Created by RAIMONDI on 14/12/2016.
 */
public class ListadoHistorialActivity extends AppCompatActivity {
    private ListView listadoHistorial;
    private ArrayList<Recorrido> listaRecorridos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_recorridos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listadoHistorial = (ListView) findViewById(R.id.listadoRecorridos);

        setTitle("Mis pedidos realizados");

        new ListarRecorridos().execute();

    }

    private class ListarRecorridos extends AsyncTask<String, Void, ArrayList<Recorrido>> {
        @Override
        protected ArrayList<Recorrido> doInBackground(String... params) {
            ProyectoApiRest rest = new ProyectoApiRest();
            ArrayList<Recorrido> listaRecorridos = rest.listarRecorridosHistorial();

            return listaRecorridos;
        }

        @Override
        protected void onPostExecute(ArrayList<Recorrido> result) {
            //listAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, result.get(1));
            // Seteamos el listado de proyectos a la variable global que mantiene los proyectos traidos de la api rest.
            listaRecorridos = result;

            // Traer de api rest el listado de recorridos.

            RecorridosAdapter adapter = new RecorridosAdapter(getApplicationContext(), listaRecorridos);

            listadoHistorial.setAdapter(adapter);
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}

