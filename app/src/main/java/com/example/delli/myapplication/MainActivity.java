package com.example.delli.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.EditText;
import android.view.inputmethod.InputMethodManager;
import android.text.TextUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = DiaryActivity.class.getSimpleName();
    private DiaryMemoDataSource diaryDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        diaryDataSource = new DiaryMemoDataSource(this);

        final Button newEntry = (Button) findViewById(R.id.button_entry);
        newEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(MainActivity.this, DiaryActivity.class);
                startActivity(nextIntent);

            }
        });


        ListView listEntry = (ListView)findViewById(R.id.itemListView);
        listEntry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent nextIntent = new Intent(MainActivity.this, Diary2Activity.class);
                nextIntent.putExtra("position", position);
                startActivity(nextIntent);
            }
        });
    }


    public void showAllListEntries(){

        List<DiaryMemo> diaryMemoList = diaryDataSource.getAllDiaryMemos();

        ArrayAdapter<DiaryMemo> diaryMemoArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, diaryMemoList);

        ListView diaryMemoListView = (ListView)findViewById(R.id.itemListView);
        diaryMemoListView.setAdapter(diaryMemoArrayAdapter);
    }


    @Override
    protected void onResume(){

        super.onResume();

        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
        diaryDataSource.open();



        Log.d(LOG_TAG, "Folgende Einträge sind in der Datenbank vorhanden: ");
        showAllListEntries();
    }


    @Override
    protected void onPause(){
        super.onPause();

        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        diaryDataSource.close();
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

}
