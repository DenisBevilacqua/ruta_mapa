package com.example.denis.mapas.modelo;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by denis on 15/11/16.
 */

// Tarea asincrónica que permite obtener una ubicacion en latitud y longitud a partir de una cadena de string.

public class GetLatLngFromString extends AsyncTask<String, Void, String[]> implements Serializable {

    public interface AsyncResponse {
        void processFinish(LatLng origen, LatLng destino);
    }

    public AsyncResponse delegate = null;

    //ProgressDialog dialog = new ProgressDialog(MapsActivity.contexto);
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        /*dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();*/
    }

    // Recibimos una cadena de string y usamos la API de google geolocalization para en segundo plano.
    @Override
    protected String[] doInBackground(String... params) {
        Log.d("params0 es ",params[0]);
        Log.d("params1 es ",params[1]);
        String response;
        String response2;
        try {
            response = getLatLongByURL("http://maps.google.com/maps/api/geocode/json?address="+params[0].replaceAll("\\s","")+"&sensor=false");
            response2 = getLatLongByURL("http://maps.google.com/maps/api/geocode/json?address="+params[1].replaceAll("\\s","")+"&sensor=false");
            Log.d("response",""+response);
            return new String[]{response,response2};
        } catch (Exception e) {
            return new String[]{"error"};
        }

    }

    @Override
    protected void onPostExecute(String... result) {
        try {
            JSONObject jsonObject = new JSONObject(result[0]);

            double lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            double lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

            Log.d("latitude", "" + lat);
            Log.d("longitude", "" + lng);

            LatLng origen = new LatLng(lat,lng);

            //////////////////////////////

            JSONObject jsonObject2 = new JSONObject(result[1]);

            double lng2 = ((JSONArray)jsonObject2.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            double lat2 = ((JSONArray)jsonObject2.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

            Log.d("latitude", "" + lat);
            Log.d("longitude", "" + lng);

            LatLng destino = new LatLng(lat2,lng2);

            delegate.processFinish(origen, destino);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*if (dialog.isShowing()) {
            dialog.dismiss();
        }*/
    }


    public String getLatLongByURL(String requestURL) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}