package com.example.denis.mapas;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.example.denis.mapas.modelo.GetLatLngFromString;
import com.example.denis.mapas.modelo.Recorrido;
import com.example.denis.mapas.recorridos.AltaRecorrido;
import com.example.denis.mapas.recorridos.ListadoRecorridosActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import im.delight.android.location.SimpleLocation;

public class MapsActivity extends AppCompatActivity implements GetLatLngFromString.AsyncResponse, OnMapReadyCallback, View.OnClickListener, DirectionCallback {
    private Button btnRequestDirection;
    private GoogleMap googleMap;
    private String serverKey = "AIzaSyAkVcdTXj47qfUPWnDKNK_d2luTpcx1PmM";
    private LatLng camera = new LatLng(-31.635468, -60.701156);
    private LatLng originRoute = new LatLng(-31.635468, -60.701156);
    private LatLng destinationRoute = new LatLng(-31.616424, -60.675253);
    private LatLng miUbicacion;
    private LatLng origenActual;
    private LatLng destinoActual;
    private Integer color;
    static public Context contexto;

    private boolean mostrandoMapa = false;
    private SimpleLocation location;
    GetLatLngFromString asyncTask =new GetLatLngFromString();
    static final int ALTA_RUTA = 1;  // The request code
    static final int RECORRIDO = 2;  // The request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_direction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        contexto = getApplicationContext();

        // Convertir una direccion en String a latitud y longitud.
        /*DataLongOperationAsynchTask task=new DataLongOperationAsynchTask();
        task.execute();*/

        //this to set delegate/listener back to this class
        asyncTask.delegate = this;

        //execute the async task
        //asyncTask.execute();

        btnRequestDirection = (Button) findViewById(R.id.btn_request_direction);
        btnRequestDirection.setOnClickListener(this);

        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);

        // construct a new instance of SimpleLocation
        location = new SimpleLocation(this);




        // if we can't access the location yet
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
        }

        findViewById(R.id.botonUbicacionActual).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                location.beginUpdates();

                final double latitude = location.getLatitude();
                final double longitude = location.getLongitude();
                miUbicacion = new LatLng(latitude,longitude);

                googleMap.addMarker(new MarkerOptions().position(miUbicacion).title("Mi ubicación"));

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(miUbicacion, 14));

                color = Color.parseColor("#DF7401");

                requestDirection(miUbicacion, originRoute, color);

            }

        });

    }

    @Override
    public void processFinish(LatLng origen, LatLng destino){
        //Here you will receive the result fired from async class
        //of onPostExecute(result) method.

        /*Toast.makeText(getApplicationContext(), "Valor de api recibido geolocation " + output,
                Toast.LENGTH_LONG).show();*/

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.limpiar) {

            googleMap.clear();
            btnRequestDirection.setText("Mostrar ruta");
            mostrandoMapa = false;

            return true;
        }

        if (id == R.id.nueva_ruta) {

            Intent intActAlta= new Intent(this,AltaRecorrido.class);
            //intActAlta.putExtra("ID_TAREA", 0);
            // intActAlta.putExtra("DAO", (Parcelable) proyectoDAO);
            startActivity(intActAlta);
            //startActivityForResult(intActAlta, ALTA_RUTA);

            return true;
        }

        if (id == R.id.listado_recorridos) {

            Intent ListadoRecorridos= new Intent(this,ListadoRecorridosActivity.class);
            //intActAlta.putExtra("ID_TAREA", 0);
            // intActAlta.putExtra("DAO", (Parcelable) proyectoDAO);
            startActivityForResult(ListadoRecorridos, RECORRIDO);
            //startActivityForResult(intActAlta, ALTA_RUTA);

            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(camera, 13));

        /*Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            Recorrido recorrido =
                    (Recorrido) bundle.getSerializable("MyClass");

        Log.d("Recorrido serial ",recorrido.getOrigen().toString());
            if (recorrido != null) {
                color = Color.parseColor("#2196F3");
                requestDirection(recorrido.getOrigen(), recorrido.getDestino(), color);
            }
        }*/

        Intent intent = getIntent();

        Integer activity = intent.getIntExtra("activity",-1);

        if (activity==0)
        {

            Double latO = intent.getDoubleExtra("LatO",-1.0);
            Double lngO = intent.getDoubleExtra("LngO",-1.0);
            Double latD = intent.getDoubleExtra("LatD",-1.0);
            Double lngD = intent.getDoubleExtra("LngD",-1.0);

            Log.d("lat",""+ latO);

            color = Color.parseColor("#2196F3");

            originRoute = new LatLng(latO,lngO);

            requestDirection(new LatLng(latO,lngO), new LatLng(latD,lngD) , color);

        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_request_direction) {
            if (mostrandoMapa == false) {
                color = Color.parseColor("#2196F3");
                requestDirection(originRoute, destinationRoute, color);
            }
            else
            {
                googleMap.clear();
                btnRequestDirection.setText("Mostrar ruta");
                mostrandoMapa = false;

            }
        }
    }

    // Funcion que utiliza la libreria requestDirection que utiliza la API Google Directions.
    public void requestDirection(LatLng origin, LatLng destination, Integer color) {
        Snackbar.make(btnRequestDirection, "Buscando dirección...", Snackbar.LENGTH_SHORT).show();
        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(destination)
                .transportMode(TransportMode.DRIVING)
                .execute(this);

        origenActual = origin;
        destinoActual = destination;
        this.color = color;
    }

    // Luego de requerir la ruta utilizando requestDirection, si sale bien entramos a esta función.
    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        Snackbar.make(btnRequestDirection, "Se ha realizado la operación con el siguiente estado: " + direction.getStatus(), Snackbar.LENGTH_LONG).show();
        if (direction.isOK()) {

            mostrandoMapa = true;
            btnRequestDirection.setText("Limpiar mapa");
            btnRequestDirection.setVisibility(View.VISIBLE);
            googleMap.addMarker(new MarkerOptions().position(origenActual).title("Origen"));
            googleMap.addMarker(new MarkerOptions().position(destinoActual).title("Destino"));

            ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
            googleMap.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, 7, color ));

            // Calculamos distancia entre ambos puntos

            Double distancia = location.calculateDistance(origenActual.latitude, origenActual.longitude, destinoActual.latitude, destinoActual.longitude);

            distancia = Math.round( distancia * 100.0 ) / 100.0;

            Toast.makeText(getApplicationContext(), "La distancia entre origen y destino es de: " + distancia + " metros",
                    Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onDirectionFailure(Throwable t) {
        Snackbar.make(btnRequestDirection, "No hemos podido mostrarte la ruta: " + t.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Pedimos permiso para obtener ubicación.

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {


            } else {


                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        0);
            }
        }

        // Deberíamos correr este código.
        // location.beginUpdates();

    }

    @Override
    protected void onPause() {
        // stop location updates (saves battery)
        location.endUpdates();

        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == ALTA_RUTA) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                //Bundle bundle = data.getExtras();

                /*Recorrido recorrido=
                        (Recorrido) data.getSerializable("MyClass");*/

                Recorrido recorrido = (Recorrido) getIntent().getSerializableExtra("MyClass");

                Log.d("Llegamos con recorrido ",recorrido.getNombre_origen());
                Log.d("Llegamos con recorrido ",recorrido.getNombre_destino());

                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
            }
        }
        if (requestCode == RECORRIDO) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                    //Log.d("ID_RECORRIDO SELECCIONDO",""+ id);
                    Double latO = data.getDoubleExtra("LatO",-1.0);
                    Double lngO = data.getDoubleExtra("LngO",-1.0);
                    Double latD = data.getDoubleExtra("LatD",-1.0);
                    Double lngD = data.getDoubleExtra("LngD",-1.0);

                    Log.d("lat",""+ latO);

                    color = Color.parseColor("#2196F3");
                    requestDirection(new LatLng(latO,lngO), new LatLng(latD,lngD) , color);

            }
        }
    }
}