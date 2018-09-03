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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        dataSource = new DiaryMemoDataSource(this);


        final Button newEntry = (Button)findViewById(R.id.button_diary);
        newEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveEntry();

                finish();

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


}
