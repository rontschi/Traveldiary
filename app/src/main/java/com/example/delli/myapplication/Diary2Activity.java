package com.example.delli.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class Diary2Activity extends AppCompatActivity {

    public static final String LOG_TAG = DiaryActivity.class.getSimpleName();
    private EditText date, place, entry;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary2);

        date = (EditText)findViewById(R.id.id_edit_date);
        place = (EditText)findViewById(R.id.id_edit_place);
        entry = (EditText)findViewById(R.id.id_edit_entry);

        getEntry();

        final Button diary = (Button)findViewById(R.id.id_button_diary);
        diary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeEntry();
                finish();
            }
        });
    }


    private void getEntry(){

        long id = getIntent().getExtras().getLong("itemId");

        DiaryMemoDataSource dataSource = new DiaryMemoDataSource(this);

        DiaryMemo diary = dataSource.getDiaryMemoById(id);


        date.setText(diary.getDate());
        place.setText(diary.getPlace());
        entry.setText(diary.getEntry());

    }



    private DiaryMemo changeEntry(){

        long id = getIntent().getExtras().getLong("itemId");


        EditText changedDate = (EditText)findViewById(R.id.id_edit_date);
        EditText changedPlace = (EditText)findViewById(R.id.id_edit_place);
        EditText changedEntry = (EditText) findViewById(R.id.id_edit_entry);

        String date = changedDate.getText().toString();
        String place = changedPlace.getText().toString();
        String entry = changedEntry.getText().toString();

        DiaryMemoDataSource dataSource = new DiaryMemoDataSource(this);

        DiaryMemo diaryMemo = dataSource.updateDiaryMemo(id, date, place, entry);

        return diaryMemo;
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
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent backIntent = new Intent(Diary2Activity.this, MainActivity.class);
                startActivity(backIntent);
                return true;
            case R.id.map_settings:
                Intent nextIntent = new Intent(Diary2Activity.this, MapActivity.class);
                startActivity(nextIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
