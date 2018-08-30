package com.example.delli.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class DiaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        Button newEntry = (Button)findViewById(R.id.button_diary);




        newEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Wenn Button geklickt dann:

                // füge den Eintrag zur Datenbank hinzu
                // zeige den Eintrag im ListView der MainActivity an
                // hol dir den Ort, suche ihn auf der Karte und
                //  markiere ihn auf der Karte mit einem Symbol

                newDataEntry();
                dataToList();
                showInMap();

                // spring zurück in die MainActivity:

                Intent nextIntent = new Intent (DiaryActivity.this,MainActivity.class);
                startActivity(nextIntent);
            }
        });
    }

    private void newDataEntry(){
        //Hinzufügen eines neuen Eintrags zur Datenbank
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
