package com.example.delli.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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





}
