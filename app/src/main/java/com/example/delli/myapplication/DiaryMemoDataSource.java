package com.example.delli.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


// = Data Access Object (DAO) (type Cursor)
// function: management of data, connection to database
// adding, reading out, deleting of data sets
// transformation of data sets to java-objects

public class DiaryMemoDataSource {

    private static final String LOG_TAG = DiaryMemoDataSource.class.getSimpleName();
    private SQLiteDatabase database;
    private DiaryMemoDbHelper dbHelper;

    private String[] columns = {
            DiaryMemoDbHelper.COLUMN_DATE,
            DiaryMemoDbHelper.COLUMN_PLACE,
            DiaryMemoDbHelper.COLUMN_ENTRY,
            DiaryMemoDbHelper.COLUMN_LONGITUDE,
            DiaryMemoDbHelper.COLUMN_LATITUDE,
            DiaryMemoDbHelper.COLUMN_ID,
    };



    // create an instance of DiaryMemoDbHelper to build a connection to the database
    // the environment is passed by the method

    public DiaryMemoDataSource(Context context){
        Log.d(LOG_TAG, "Die DataSource erzeugt jetzt den dbHelper.");
        dbHelper = new DiaryMemoDbHelper(context);
    }


    // creates DiaryMemo-object
    // insert data sets into the table of tth database
    public DiaryMemo createDiaryMemo(String date, String place, String entry, double longitude,
                                     double latitude){

        ContentValues values = new ContentValues();
        values.put(DiaryMemoDbHelper.COLUMN_DATE, date);
        values.put(DiaryMemoDbHelper.COLUMN_PLACE, place);
        values.put(DiaryMemoDbHelper.COLUMN_ENTRY, entry);
        values.put(DiaryMemoDbHelper.COLUMN_LONGITUDE, longitude);
        values.put(DiaryMemoDbHelper.COLUMN_LATITUDE, latitude);

        // open the database
        open();

        // add data set to database
        // receive a distinct id with type long
        long insertId = database.insert(DiaryMemoDbHelper.TABLE_DIARY_LIST, null, values);

        // looking for a data set with a certain id
        // receive a data access object with type Cursor
        Cursor cursor = database.query(DiaryMemoDbHelper.TABLE_DIARY_LIST, columns,
                DiaryMemoDbHelper.COLUMN_ID + "=" + insertId,
                null, null, null, null);

        cursor.moveToFirst();
        DiaryMemo diaryMemo = cursorToDiaryMemo(cursor);
        cursor.close();

        //close the database
        close();

        return diaryMemo;
    }


    // is selected if an existing entry is changed, the new data has to be saved
    public DiaryMemo updateDiaryMemo(long id, String changedDate, String changedPlace,
                                     String changedEntry) {

        ContentValues values = new ContentValues();
        values.put(DiaryMemoDbHelper.COLUMN_DATE, changedDate);
        values.put(DiaryMemoDbHelper.COLUMN_PLACE, changedPlace);
        values.put(DiaryMemoDbHelper.COLUMN_ENTRY, changedEntry);
        //values.put(DiaryMemoDbHelper.COLUMN_LONGITUDE, changedLongitude);
        //values.put(DiaryMemoDbHelper.COLUMN_LATITUDE, changedLatitude);

        // open database
        open();

        database.update(DiaryMemoDbHelper.TABLE_DIARY_LIST,
                values,
                DiaryMemoDbHelper.COLUMN_ID + "=" + id,
                null);

        Cursor cursor = database.query(DiaryMemoDbHelper.TABLE_DIARY_LIST,
                columns, DiaryMemoDbHelper.COLUMN_ID + "=" + id,
                null, null, null, null);

        cursor.moveToFirst();
        DiaryMemo diaryMemo = cursorToDiaryMemo(cursor);
        cursor.close();

        close();

        return diaryMemo;
    }


    // method to delete an existing entry from the database
    public void deleteDiaryMemo(DiaryMemo diaryMemo){

        long id = diaryMemo.getId();

        database.delete(DiaryMemoDbHelper.TABLE_DIARY_LIST,
                DiaryMemoDbHelper.COLUMN_ID + "=" + id,
                null);

        Log.d(LOG_TAG, "Eintrag gelöscht! ID: " + id + " Inhalt: "
                + diaryMemo.toString());

    }


    // convert Cursor-object into DiaryMemo-object
    // reading out the index of the columns and create the object out of it
    private DiaryMemo cursorToDiaryMemo(Cursor cursor){

        int idDate = cursor.getColumnIndex(DiaryMemoDbHelper.COLUMN_DATE);
        int idPlace = cursor.getColumnIndex(DiaryMemoDbHelper.COLUMN_PLACE);
        int idEntry = cursor.getColumnIndex(DiaryMemoDbHelper.COLUMN_ENTRY);
        int idLongitude = cursor.getColumnIndex(DiaryMemoDbHelper.COLUMN_LONGITUDE);
        int idLatitude = cursor.getColumnIndex(DiaryMemoDbHelper.COLUMN_LATITUDE);
        int idIndex = cursor.getColumnIndex(DiaryMemoDbHelper.COLUMN_ID);

        String date = cursor.getString(idDate);
        String place = cursor.getString(idPlace);
        String entry = cursor.getString(idEntry);
        double longitude = cursor.getDouble(idLongitude);
        double latitude = cursor.getDouble(idLatitude);
        long id = cursor.getLong(idIndex);

        DiaryMemo diaryMemo = new DiaryMemo(date, place, entry, longitude, latitude, id);

        return diaryMemo;
    }


    // returns the certain DiaryMemo-object identified by its passed id
    public DiaryMemo getDiaryMemoById (long id){

        DiaryMemo diaryMemo = null;

        //open database
        open();
        Cursor cursor = database.query(DiaryMemoDbHelper.TABLE_DIARY_LIST, columns,
                DiaryMemoDbHelper.COLUMN_ID + "=" + id,
                null, null, null, null);

        if (cursor!=null){
            cursor.moveToFirst();
            diaryMemo = cursorToDiaryMemo(cursor);
            Log.d(LOG_TAG, "ID: " + DiaryMemoDbHelper.COLUMN_ID + ", Inhalt: "
             + diaryMemo.toString());

            cursor.close();
        }

        // close database
        close();

        return diaryMemo;

    }


    // reading out all the data sets in the table of the database
    public List<DiaryMemo> getAllDiaryMemos(){

        List<DiaryMemo> diaryMemoList = new ArrayList<>();

        //Such-String = null -> alle in Tabelle existierenden Datensätze werden als Ergebnis
        //  zurückgeliefert:

        // query to get all existing data sets (by setting all arguments = null)
        Cursor cursor = database.query(DiaryMemoDbHelper.TABLE_DIARY_LIST, columns,
                null, null, null, null, null);

        //if(cursor != null) {
            cursor.moveToFirst();

            DiaryMemo diaryMemo;

            // convert the data sets into DiaryMemo-objects and add to list
            while (!cursor.isAfterLast()) {
                diaryMemo = cursorToDiaryMemo(cursor);
                diaryMemoList.add(diaryMemo);
                Log.d(LOG_TAG, "ID: " + diaryMemo.getId() + ", Inhalt: "
                        + diaryMemo.toString());
                cursor.moveToNext();
            }

            cursor.close();
        //}
        return diaryMemoList;  //DiaryMemo-Liste: enthält alle Datensätze der Tabelle
    }


    //establish connection to database with DbHlper-object
    public void open(){

        Log.d(LOG_TAG, "Eine REferenz auf die Datenbank wird jetzt angefragt.");
        database = dbHelper.getWritableDatabase();
        Log.d(LOG_TAG, "Datenbank-Referenz erhalten. Pfad zur Datenbank: "
                + database.getPath());
    }


    //close connection to database
    public void close(){

        dbHelper.close();
        Log.d(LOG_TAG, "Datenbank mit Hilfe des DbHelpers geschlossen.");
    }

}
