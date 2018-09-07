package com.example.delli.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import java.util.List;


public class DiaryActivity extends AppCompatActivity {

    public static final String LOG_TAG = DiaryActivity.class.getSimpleName();
    private DiaryMemoDataSource dataSource;
    static final String DATE = "31.08.18";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        //DiaryMemo testMemo = new DiaryMemo("30.08.18","Emmering", "Heute hat es geregnet.", 1 );

        //final String DATE = "31.08.18";
        //String place = "Emmering";
        //String entry = "Heute regnet es den ganzen Tag";







        Log.d(LOG_TAG,  "Es wurde der folgende Eintrag in die Datenbank geschrieben:");
        //Log.d(LOG_TAG, "ID: " + diaryMemo.getId() + ", Inhalt: " + diaryMemo.toString());


        final Button newEntry = (Button)findViewById(R.id.button_diary);
        Button newEntry1 = (Button)findViewById(R.id.button_diary);








        newEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Wenn Button geklickt dann:

                // füge den Eintrag zur Datenbank hinzu
                // zeige den Eintrag im ListView der MainActivity an
                // hol dir den Ort, suche ihn auf der Karte und
                //  markiere ihn auf der Karte mit einem Symbol

                DiaryMemo entry = saveEntry();



                // spring zurück in die MainActivity:
                finish();


                //Intent nextIntent = new Intent (DiaryActivity.this,MainActivity.class);
                //nextIntent.putExtra(DATE, );
                //startActivity(nextIntent);

            }
        });
    }









    private DiaryMemo saveEntry(){


        EditText newDate = (EditText)findViewById(R.id.edit_date_id);
        EditText newPlace = (EditText)findViewById(R.id.edit_place_id);
        EditText newEntry = (EditText) findViewById(R.id.edit_entry_id);

        String date = newDate.getText().toString();
        String place = newPlace.getText().toString();
        String entry = newEntry.getText().toString();

        DiaryMemo diaryMemo = dataSource.createDiaryMemo(date, place, entry);

        return diaryMemo;

    }

    private void dataToList(){
        //Anzeigen des gespeicherten Eintrags in Liste in MainActivity
    }

    //erbt von MapActivity? -> selbe Methode? andere Möglichkeit?
    //Wird es zweimal gebraucht oder reicht einmal?
    private void showInMap(){
        //Ort holen und in Map markieren
        // .toString
    }

}
