package com.example.delli.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }

    //Zugriff auf Orte

    public void savePlace(){
        //Speichern von Orten in der Map
    }

    private void searchPlace(){
        //Hintergrundsuche
    }

    public void showInMap(){
        //Anzeigen von Ort mit Symbol auf Karte
    }

    public void toDiary(){
        //Intent zwischen Diaryactivity + MapActivity -> bei Antippen von
        //  Symbol Springen in richtigen Tagebucheintrag
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





}
