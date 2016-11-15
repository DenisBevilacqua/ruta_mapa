package com.example.denis.mapas;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AltaRuta extends AppCompatActivity {

    private Button btn_crear_ruta;
    private EditText origen;
    private EditText destino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_ruta);

        btn_crear_ruta = (Button) findViewById(R.id.btn_crear_ruta);

        origen = (EditText) findViewById(R.id.origen);
        destino = (EditText) findViewById(R.id.destino);

        btn_crear_ruta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String origenString = origen.getText().toString();
                String destinoString = destino.getText().toString();

                // Enviar a main activity los valores.

            }

        });
    }
}
