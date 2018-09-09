package com.example.delli.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DiaryActivity extends AppCompatActivity implements LocationListener {

    public static final String LOG_TAG = DiaryActivity.class.getSimpleName();
    private DiaryMemoDataSource dataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        dataSource = new DiaryMemoDataSource(this);

     /*   final Button coordinatesButton = (Button)findViewById(R.id.button_coordinates);
        coordinatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent mapIntent = new Intent(DiaryActivity.this, MapsActivity.class);
                mapIntent.putExtra("Latitude", lat);
                mapIntent.putExtra("Longitude", lng);
                startActivity(mapIntent);
            }
        });*/


        final Button coordinatesButton = (Button)findViewById(R.id.button_coordinates);
        coordinatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLongitude();
                getLatitude();
            }
        });

        final Button newEntry = (Button)findViewById(R.id.button_diary);
        newEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (saveEntry()){
                    finish();
                }

            }
        });

    }


    private boolean saveEntry(){


        EditText newDate = (EditText)findViewById(R.id.edit_date_id);
        EditText newPlace = (EditText)findViewById(R.id.edit_place_id);
        EditText newEntry = (EditText) findViewById(R.id.edit_entry_id);

        String date = newDate.getText().toString();
        String place = newPlace.getText().toString();
        String entry = newEntry.getText().toString();
        double longitude = getLongitude();
        double latitude = getLatitude();

        if (isDateValid(date, "dd.mm.yy")){
            newDate.setError("Datum nicht gültig");
            Toast.makeText(getApplicationContext(),
                    "Ungültiges Datum", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(date)) {
            newDate.setError("");
            Toast.makeText(getApplicationContext(),
                    "Eingabe erforderlich", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(TextUtils.isEmpty(place)) {
            newPlace.setError("");
            Toast.makeText(getApplicationContext(),
                    "Eingabe erforderlich", Toast.LENGTH_SHORT).show();
            return false;
        }


        dataSource.createDiaryMemo(date, place, entry, longitude, latitude);

        return true;
    }


    public boolean isDateValid(String dateToValidate, String dateFormat){

        if(dateToValidate == null){
            return false;
        }

        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        format.setLenient(false);

        try {

            Date date = format.parse(dateToValidate);
            System.out.println(date);

        } catch (ParseException e){

            e.printStackTrace();
            return true;
        }

        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu, this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        //Actionbar clicks, automatisches handlen von Clicks (Home/Up-Button
        //-> solange wie parent activity in AndroidManifest.xml spezifiziert

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent backIntent = new Intent(DiaryActivity.this, MainActivity.class);
                startActivity(backIntent);
                return true;
            case R.id.map_settings:
                Intent nextIntent = new Intent(DiaryActivity.this, MapsActivity.class);
                startActivity(nextIntent);
                return true;
        }


        return super.onOptionsItemSelected(item);
    }


    public double getLongitude(){


        String service = Context.LOCATION_SERVICE;
        LocationManager locationManger = (LocationManager)
                getSystemService(service);
        String provider = LocationManager.GPS_PROVIDER;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = locationManger
                    .getLastKnownLocation(provider);
            double longitude = location.getLongitude();
            return longitude;
        }
        return 0;

    }

    public double getLatitude(){

        //Location location;

        String service = Context.LOCATION_SERVICE;
        LocationManager locationManger = (LocationManager)
                getSystemService(service);
        String provider = LocationManager.GPS_PROVIDER;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = locationManger
                    .getLastKnownLocation(provider);
            double latitude = location.getLatitude();
            return latitude;
        }
        return 0;
    }


        @Override
        public void onLocationChanged (Location location){

        }

        @Override
        public void onStatusChanged (String provider,int status, Bundle extras){

        }

        @Override
        public void onProviderEnabled (String provider){

        }

        @Override
        public void onProviderDisabled (String provider){

        }

}
