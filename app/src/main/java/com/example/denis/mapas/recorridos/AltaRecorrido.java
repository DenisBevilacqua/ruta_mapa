package com.example.denis.mapas.recorridos;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.denis.mapas.MapsActivity;
import com.example.denis.mapas.R;
import com.example.denis.mapas.modelo.GetLatLngFromString;
import com.example.denis.mapas.modelo.Recorrido;
import com.example.denis.mapas.dao.ProyectoApiRest;
import com.google.android.gms.maps.model.LatLng;

public class AltaRecorrido extends AppCompatActivity implements GetLatLngFromString.AsyncResponse  {

    private Button btn_crear_ruta;
    private EditText origen;
    private EditText destino;

    private Recorrido recorrido;
    private Context context;
    private GetLatLngFromString.AsyncResponse asyncResponse;

    GetLatLngFromString asyncTask =new GetLatLngFromString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getApplicationContext();

        // Seteo del layout a la actividad.
        setContentView(R.layout.activity_alta_ruta);

        // Boton CREAR RUTA
        btn_crear_ruta = (Button) findViewById(R.id.btn_crear_ruta);

        // EditTexts origen y destino.
        origen = (EditText) findViewById(R.id.origen);
        destino = (EditText) findViewById(R.id.destino);

        asyncResponse = this;
        asyncTask.delegate = this;

        // Listener en clase anonima. Se ejecuta cuando presionamos el botón "Crear ruta".
        btn_crear_ruta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Obtenemos los valores de los EditTexts y los asignamos a 2 variables String.

                String origenString = origen.getText().toString();
                String destinoString = destino.getText().toString();

                recorrido = new Recorrido();

                recorrido.setNombre_origen(origenString);
                recorrido.setNombre_destino(destinoString);

                //execute the async task

                asyncTask = new GetLatLngFromString();

                asyncTask.delegate = asyncResponse;

                asyncTask.execute(recorrido.getNombre_origen(),recorrido.getNombre_destino());


                // Enviar a main activity los valores.

            }

        });
    }

    @Override
    public void processFinish(LatLng origen, LatLng destino, String name1, String name2) {

        // Debemos guardar los valores en la base de datos

        recorrido.setOrigen_latitud(origen.latitude);
        recorrido.setOrigen_longitud(origen.longitude);
        recorrido.setDestino_latitud(destino.latitude);
        recorrido.setDestino_longitud(destino.longitude);

        recorrido.setNombre_origen(name1);
        recorrido.setNombre_destino(name2);

        recorrido.setOrigen(origen);

        recorrido.setDestino(destino);

        //new GetMaxId().execute();
        new GestionarRecorridos(recorrido, 1, null).execute("");

        /*Intent intActAlta= new Intent(context,MapsActivity.class);

        Bundle bundle = new Bundle();

        bundle.putSerializable("MyClass", recorrido);

        intActAlta.putExtras(bundle);

        startActivity(intActAlta);*/

        Intent intActAlta= new Intent(context,ListadoRecorridosActivity.class);

        startActivity(intActAlta);

        //((AltaRecorrido)getApplicationContext()).finish();

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


    private class GetMaxId extends AsyncTask<Object, Object, Integer> {

        public GetMaxId(){
        }

        @Override
        protected Integer doInBackground(Object... params) {

            Integer valor=100;
            ProyectoApiRest rest = new ProyectoApiRest();

            try {
                valor = rest.getMaxId();
            } catch (Exception e){
            }
            return valor;

        }
        @Override
        protected void onPostExecute(Integer result) {

            recorrido.setId(result.toString());

            new GestionarRecorridos(recorrido, 1, null).execute("");

        }
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected void onProgressUpdate(Object... values) {
        }
    }


}
