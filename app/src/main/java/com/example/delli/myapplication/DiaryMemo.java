package com.example.delli.myapplication;


//function: representation of data set
// instances receive data of SQLite-data set
//ListView in MainActivity is filled with DiaryMemo-objects

public class DiaryMemo {

    private final String date;
    private final String place;
    private final String entry;
    private final double longitude;
    private final double latitude;
    private final long id;


    public DiaryMemo(String date, String place, String entry, double longitude, double latitude, long id){
        this.date = date;
        this.place = place;
        this.entry = entry;
        this.longitude = longitude;
        this.latitude = latitude;
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

    public double getLongitude(){
        return longitude;
    }

    public double getLatitude(){
        return latitude;
    }

    public long getId(){
        return id;
    }


    //output in ListView in MainActivity
    @Override
    public String toString(){
        String output = date + ": " + place;

        return output;
    }
}
