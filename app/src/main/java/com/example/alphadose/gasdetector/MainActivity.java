package com.example.alphadose.gasdetector;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private LocationManager locationManager;
    public String key = "d6e2f7b05bd92ce69ccc964f7a3f183b";
    public String api_url = "http://api.openweathermap.org/data/2.5/weather";
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // check if GPS enabled
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_ACCESS_FINE_LOCATION);

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, this);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        String url = api_url+"?lat="+location.getLatitude()+"&lon="+location.getLongitude()+"&appid="+key;
        getJSON(url);
    }

    @Override
    public void onProviderDisabled(String provider) {

        /******** Called when User off Gps *********/

        Toast.makeText(getBaseContext(), "Gps turned off ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String provider) {

        /******** Called when User on Gps  *********/

        Toast.makeText(getBaseContext(), "Gps turned on ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    public void getJSON(String address){
        Log.e("error", address);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //getting the whole json object from the response
                            Log.e( "error", response);
                            JSONObject obj = new JSONObject(response);
                            Log.e( "error", obj.toString());


                            TextView v = findViewById(R.id.location);
                            v.setText(obj.getString("name") + ", " + obj.getJSONObject("sys").getString("country"));

                            TextView v1 = findViewById(R.id.value2);
                            v1.setText(obj.getJSONObject("main").getString("temp") + " K");
                            TextView v2 = findViewById(R.id.value3);
                            v2.setText(obj.getJSONObject("main").getString("humidity") + " %");
                            TextView v3 = findViewById(R.id.value4);
                            v3.setText(obj.getJSONObject("main").getString("pressure") + " mbar");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    public void gasdetection(View view) {
        Intent myIntent = new Intent(this, Main2.class);
        startActivity(myIntent);
    }
}
