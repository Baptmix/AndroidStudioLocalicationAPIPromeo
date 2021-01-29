package com.baptmix.location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.baptmix.location.request.RequestManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView text_latitude, text_longitude, result_latitude, result_longitude, text_city, text_weather;
    public static TextView result_city, result_weather, date;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_latitude = (TextView) findViewById(R.id.text_latitude);
        text_longitude = (TextView) findViewById(R.id.text_longitude);
        text_longitude = (TextView) findViewById(R.id.text_longitude);
        result_latitude = (TextView) findViewById(R.id.result_latitude);
        result_longitude = (TextView) findViewById(R.id.result_longitude);
        text_weather = (TextView) findViewById(R.id.text_weather);
        date = (TextView) findViewById(R.id.date);
        result_weather = (TextView) findViewById(R.id.result_weather);
        result_city = findViewById(R.id.result_city);

        Date dateChecker = new Date();
        Timestamp ts=new Timestamp(dateChecker.getTime());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        date.setText(formatter.format(ts));
        date.setGravity(Gravity.CENTER);
        result_city.setGravity(Gravity.CENTER);

        // Check si on a la permission d'accéder à la loc
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            // Si on ne l'a pas on la demande
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET},
                    1000);
        } else {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            result_latitude.setText(location.getLatitude() + "");
                            result_longitude.setText(location.getLongitude() + "");
                            try {
                                RequestManager.getWeather(location.getLatitude(), location.getLongitude());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }
    }

}