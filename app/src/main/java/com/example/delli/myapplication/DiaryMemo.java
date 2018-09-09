package com.example.delli.myapplication;


//function: representation of data set
// instances receive data of SQLite-data set
//ListView in MainActivity is filled with DiaryMemo-objects

public class DiaryMemo {

    private final String date;
    private final String place;
    private final String entry;
    private final double lng;
    private final double lat;
    private final long id;


    public DiaryMemo(String date, String place, String entry, double lng, double lat, long id){
        this.date = date;
        this.place = place;
        this.entry = entry;
        this.lng = lng;
        this.lat = lat;
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

    public double getLng(){
        return lng;
    }

    public double getLat(){
        return lat;
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
