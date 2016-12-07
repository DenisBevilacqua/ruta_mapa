package com.example.denis.mapas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.denis.mapas.clases.GetLatLngFromString;
import com.example.denis.mapas.clases.Recorrido;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AltaRuta extends AppCompatActivity implements GetLatLngFromString.AsyncResponse  {

    private Button btn_crear_ruta;
    private EditText origen;
    private EditText destino;

    private Recorrido recorrido;
    private Context context;

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
                asyncTask.execute(recorrido.getNombre_origen(),recorrido.getNombre_destino());



                // Enviar a main activity los valores.

            }

        });
    }

    @Override
    public void processFinish(LatLng origen, LatLng destino) {

        recorrido.setOrigen(origen);

        recorrido.setDestino(destino);

        Intent intActAlta= new Intent(context,MapsActivity.class);

        /*intActAlta.putExtra("MyClass", recorrido);

        startActivity(intActAlta);*/

        Bundle bundle = new Bundle();

        bundle.putSerializable("MyClass", recorrido);

        intActAlta.putExtras(bundle);

        startActivity(intActAlta);

        /*Intent resultIntent = new Intent();

        // TODO Add extras or a data URI to this intent as appropriate.

        resultIntent.putExtra("MyClass", recorrido);

        // To retrieve object in second Activity

        getIntent().getSerializableExtra("MyClass");

        setResult(Activity.RESULT_OK, resultIntent);

        //finish();

        finish();*/

    }
}
