package com.example.delli.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private static final LatLng PERTH = new LatLng(-31.952854, 115.857342);

    private GoogleMap mMap;
    private Boolean locationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    ArrayList<LatLng> markersArray = new ArrayList<>();
    private DiaryMemoDataSource dataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        dataSource = new DiaryMemoDataSource(this);

        getLocationPermissions();

    }

    private void getDeviceLocation(){

    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Toast.makeText(getApplicationContext(),
                "Karte geladen", Toast.LENGTH_SHORT).show();


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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent nextIntent = new Intent(MapActivity.this, MainActivity.class);
            startActivity(nextIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getLocationPermissions() {
        String[] permissions = {FINE_LOCATION,
                COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationPermissionsGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionsGranted = true;
                    initMap();
                }
            }
        }
    }

    private void getLocationPermission(boolean mLocationPermissionGranted) {

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        /*Marker mPerth = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(10,10))
                .title("Perth"));
        mPerth.setTag(0);*/
        loadMarkers();
    }

    private void loadMarkers() {

        dataSource.open();
        List<DiaryMemo> diaryMemoList = dataSource.getAllDiaryMemos();


        for (DiaryMemo i: diaryMemoList   ) {
            double latitude = i.getLat();
            double longitude = i.getLng();
            LatLng coordination = new LatLng(latitude, longitude);

            mMap.addMarker(new MarkerOptions()
                    .position(coordination)
                    .title("" +i.getId()));

        }
        dataSource.close();
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {


        String StringId = marker.getTitle();
        int Id = Integer.parseInt(StringId);
        Long itemId = Long.valueOf(Id);


            Toast.makeText(this,"zu Tagebucheintrag wechseln",Toast.LENGTH_SHORT);
        Intent nextIntent = new Intent(this, Diary2Activity.class);
        nextIntent.putExtra("itemId",itemId);
        startActivity(nextIntent);

        return false;
        }

}
