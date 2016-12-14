package com.example.denis.mapas.recorridos;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.denis.mapas.MapsActivity;
import com.example.denis.mapas.R;
import com.example.denis.mapas.alarma.AlarmReceiver;
import com.example.denis.mapas.dao.ProyectoApiRest;
import com.example.denis.mapas.modelo.Recorrido;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;

import im.delight.android.location.SimpleLocation;

public class ListadoRecorridosActivity extends AppCompatActivity {

    private ListView listadoRecorridos;
    private ArrayList<Recorrido> listaRecorridos;

    private LatLng originRoute = new LatLng(-31.635468, -60.701156);
    private SimpleLocation location;

    NotificationManager mNotificationManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_recorridos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listadoRecorridos = (ListView) findViewById(R.id.listadoRecorridos);

        registerForContextMenu(listadoRecorridos);

        setTitle("Recorridos disponibles");

        generarAlarma();

        location = new SimpleLocation(this);
        // if we can't access the location yet
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
        }

        // Ejecutamos la tarea asincrónica.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Log.d("Peticion de permisos", " entra al if de SDK_INT");

            location.beginUpdates();
            new ListarRecorridos().execute("");
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Log.d("Peticion de permisos", " entra a los dos primeros if");

            } else {
                Log.d("Peticion de permisos", " entra al else");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 5);
            }
        } else {
            location.beginUpdates();
            new ListarRecorridos().execute("");
        }
        Log.d("Peticion de permisos", " no hizo nada");
        //new ListarRecorridos().execute("");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(ListadoRecorridosActivity.this);
                builder.setTitle("¿Desea generar un nuevo pedido?");

                final EditText input = new EditText(ListadoRecorridosActivity.this);

                //input.setInputType(InputType.TYPE_CLASS_TEXT);

                //input.setHint("Descripción del proyecto");

                /*final TextView textview = new TextView(ProyectosActivity.this);
                textview.setText("Ingrese descripción");
                builder.setCustomTitle(textview);*/

                // builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Intent intActAlta = new Intent(getApplicationContext(), AltaRecorrido.class);
                        //intActAlta.putExtra("ID_TAREA", 0);
                        // intActAlta.putExtra("DAO", (Parcelable) proyectoDAO);
                        startActivity(intActAlta);
                        //startActivityForResult(intActAlta, ALTA_RUTA);

                        finish();


                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });
    }


    private class GestionarRecorridos extends AsyncTask<Object, Object, Integer> {
        private Recorrido r;
        private Integer i;   //Valor que indica la operación que se quiere realizar: 1:crear, 2:borrar, 3:actualizar
        private Integer id;

        public GestionarRecorridos(Recorrido r, Integer i, Integer id) {
            this.r = r;
            this.i = i;
            this.id = id;
        }

        @Override
        protected Integer doInBackground(Object... params) {

            ProyectoApiRest rest = new ProyectoApiRest();

            switch (i) {
                case 1: {
                    rest.crearRecorrido(r);
                    break;
                }
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


            originRoute = new LatLng(location.getLatitude(), location.getLongitude());

            for (Recorrido r : listaRecorridos) {
                Double distancia = location.calculateDistance(originRoute.latitude, originRoute.longitude, r.getOrigen().latitude, r.getOrigen().longitude);
                distancia = Math.round(distancia * 100.0) / 100.0;
                r.setDistanciaOrigen(distancia);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                listaRecorridos.sort(new RecorridoComparator());
            }

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

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        inflater.inflate(R.menu.menu_gestionar_recorridos, menu);

    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.botonVerRecorrido: {
                String result = listaRecorridos.get(info.position).getId();
                LatLng LatLngO = listaRecorridos.get(info.position).getOrigen();
                LatLng LatLngD = listaRecorridos.get(info.position).getDestino();
                Log.d("ID_RECORRIDO", result);
                Log.d("ID_POSITION", info.position + "");
                //int resultado = Integer.parseInt(result);

                Intent returnIntent = new Intent(getApplicationContext(), MapsActivity.class);

                returnIntent.putExtra("activity", 0);
                returnIntent.putExtra("LatO", LatLngO.latitude);
                returnIntent.putExtra("LngO", LatLngO.longitude);
                returnIntent.putExtra("LatD", LatLngD.latitude);
                returnIntent.putExtra("LngD", LatLngD.longitude);
                returnIntent.putExtra("id", result);
                startActivity(returnIntent);
                //setResult(Activity.RESULT_OK,returnIntent);

                finish();
            }
            break;
        }

        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        Log.d("Peticion de permisos", " llega al Result");
        if (requestCode == 5) {
            Log.d("Peticion de permisos", " llega al Result y entra al if");
            // If request is cancelled, the result arrays are empty.
            if (permissions.length == 1 && permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                Log.d("Peticion de permisos", " llega al Result y entra al if");
                new ListarRecorridos().execute("");

            } else {

                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
            return;
        }

        // other 'case' lines to check for other
        // permissions this app might request
    }

    private void generarAlarma() {



        final Intent alarmIntent = new Intent(this, AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        final AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        Log.d("Calendar", calendar.getTimeInMillis() + "");

        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + 10000, 60 * 1000, pendingIntent);

    }


}
