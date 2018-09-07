package com.example.delli.myapplication;

import android.content.Intent;

public class DiaryMemo {

    private final String date;
    private final String place;
    private final String entry;


    //Instanzen nehmen Daten eines SQLite-Datensatzes auf
    //-> Repräsentation der Datensätze
    //ListView wird mit Objekten dieser Klasse gefüllt



    public DiaryMemo(String date, String place, String entry){
        this.date = date;
        this.place = place;
        this.entry = entry;
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






    @Override
    public String toString(){
        String output = "Am " + date + " in " + place + ": " + entry;

        return output;
    }


}
