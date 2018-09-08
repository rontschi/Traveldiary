package com.example.delli.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//= helper-class
// function: creates SQLite-database
// contains important constants (table name, version of database, columns + their names)

public class DiaryMemoDbHelper extends SQLiteOpenHelper{

    private static final String LOG_TAG = DiaryMemoDbHelper.class.getSimpleName();

    public static final String DB_NAME = "traveldiary_list.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_DIARY_LIST = "diary_list";

    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_PLACE = "place";
    public static final String COLUMN_ENTRY = "entry";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_ID = "_id";

    //create database
    // contains six columns: date, place, entry, longitude, latitude, id
    // the ids will automaticly increase
    public static final String SQL_CREATE = "CREATE TABLE " + TABLE_DIARY_LIST + "(" + COLUMN_DATE +
            " TEXT NOT NULL, " + COLUMN_PLACE + " TEXT NOT NULL, " + COLUMN_ENTRY + " TEXT NOT NULL, "
            + COLUMN_LONGITUDE + COLUMN_LATITUDE + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT);";

    //environment
    public DiaryMemoDbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
    }


    //retrieving onCreate(), if database does not exist yet
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.d(LOG_TAG, "Die Tabelle wird mit SQL_Befehl: "
            + SQL_CREATE + " angelegt.");
            db.execSQL(SQL_CREATE);
        }
        catch (Exception ex){
            Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: "
            + ex.getMessage());
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
