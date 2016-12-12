package com.example.denis.mapas.recorridos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.denis.mapas.R;
import com.example.denis.mapas.dao.ProyectoApiRest;
import com.example.denis.mapas.modelo.Recorrido;

import java.util.ArrayList;

public class ListadoRecorridosActivity extends AppCompatActivity {

    private ListView listadoRecorridos;
    private ArrayList<Recorrido> listaRecorridos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_recorridos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listadoRecorridos = (ListView) findViewById(R.id.listadoRecorridos);

        registerForContextMenu(listadoRecorridos);

        setTitle("Recorridos disponibles");

        // Ejecutamos la tarea asincrónica.

        new ListarRecorridos().execute("");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private class GestionarRecorridos extends AsyncTask<Object, Object, Integer> {
        private Recorrido r;
        private Integer i;   //Valor que indica la operación que se quiere realizar: 1:crear, 2:borrar, 3:actualizar
        private Integer id;
        public GestionarRecorridos(Recorrido r, Integer i, Integer id){
            this.r = r;
            this.i = i;
            this.id = id;
        }

        @Override
        protected Integer doInBackground(Object... params) {

            ProyectoApiRest rest = new ProyectoApiRest();

            switch(i){
                case 1: {
                    rest.crearRecorrido(r);
                    break;}
                /*case 2:{
                    rest.borrarProyecto(id);
                }break;*/
                /*case 3:{
                    rest.actualizarProyecto(p);
                }break;*/
                /*case 4:{
                    rest.verTareas(p);
                }break;*/
            }
            return 1;
        }

        @Override
        protected void onPostExecute(Integer result) {

            //new TareaAsincronica().execute();

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Object... values) {
        }
    }

    private class ListarRecorridos extends AsyncTask<String, Void, ArrayList<Recorrido>> {
        @Override
        protected ArrayList<Recorrido> doInBackground(String... params) {
            ProyectoApiRest rest = new ProyectoApiRest();
            ArrayList<Recorrido> listaRecorridos = rest.listarRecorridos();
            return listaRecorridos;
        }

        @Override
        protected void onPostExecute(ArrayList<Recorrido> result) {
            //listAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, result.get(1));


            // Seteamos el listado de proyectos a la variable global que mantiene los proyectos traidos de la api rest.
            listaRecorridos = result;

            // Traer de api rest el listado de recorridos.


             RecorridosAdapter adapter = new RecorridosAdapter(getApplicationContext(), listaRecorridos);

             listadoRecorridos.setAdapter(adapter);

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;

        inflater.inflate(R.menu.menu_gestionar_recorridos,menu);

    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.botonVerRecorrido: {
                //String result=info.;


                Log.d("INFO_toString",info.toString());
                Log.d("ITEM_toString",item.toString());
                /*Intent returnIntent = new Intent();
                returnIntent.putExtra("result",result);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();*/
            }break;
        }

        return true;
    }

}
