package com.example.delli.myapplication;

import android.content.Intent;

public class DiaryMemo {

    private final String date;
    private final String place;
    private final String entry;
    private final long id;


    //Instanzen nehmen Daten eines SQLite-Datensatzes auf
    //-> Repräsentation der Datensätze
    //ListView wird mit Objekten dieser Klasse gefüllt


    public DiaryMemo(String date, String place, String entry, long id){
        this.date = date;
        this.place = place;
        this.entry = entry;
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public String getPlace() {
        return place;
    }

    public String getEntry() {
        return entry;
    }

    public long getId(){
        return id;
    }


    @Override
    public String toString(){
        String output = date + ": " + place;

        return output;
    }
}
