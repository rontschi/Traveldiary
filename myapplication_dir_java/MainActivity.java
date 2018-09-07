package com.example.delli.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.EditText;
import android.view.inputmethod.InputMethodManager;
import android.text.TextUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = DiaryActivity.class.getSimpleName();
    private DiaryMemoDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        //String diary = extras.getString(DiaryActivity.DATE);

        Button newEntry = (Button) findViewById(R.id.button_entry);


        newEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(MainActivity.this, DiaryActivity.class);
                startActivity(nextIntent);

            }
        });


        DiaryMemo testMemo = new DiaryMemo("30.08.18","Emmering", "Heute hat es geregnet.");
        Log.d(LOG_TAG, "Inhalt der Testmemo: " + testMemo.toString());

        dataSource = new DiaryMemoDataSource(this);

//        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
//        dataSource.open();
//
//
//
//        Log.d(LOG_TAG, "Folgende Einträge sind in der Datenbank vorhanden: ");
//        showAllListEntries();
//
//
//
//        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
//        dataSource.close();


    }

    public void showAllListEntries(){

        List<DiaryMemo> diaryMemoList = dataSource.getAllDiaryMemos();

        ArrayAdapter<DiaryMemo> diaryMemoArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, diaryMemoList);

        ListView diaryMemoListView = (ListView)findViewById(R.id.itemListView);
        diaryMemoListView.setAdapter(diaryMemoArrayAdapter);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu, this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        //Actionbar clicks, automatisches handlen von Clicks (Home/Up-Button
        //-> solange wie parent activity in AndroidManifest.xml spezifiziert
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id==R.id.action_settings){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
        dataSource.open();

        Log.d(LOG_TAG, "Folgende Einträge sind in der Datenbank vorhanden:");
        showAllListEntries();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();
    }

}
