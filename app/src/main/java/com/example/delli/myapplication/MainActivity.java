package com.example.delli.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.EditText;
import android.view.inputmethod.InputMethodManager;
import android.text.TextUtils;

import com.google.android.gms.maps.GoogleMap;

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


        final ListView listEntry = (ListView)findViewById(R.id.itemListView);
        listEntry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DiaryMemo diaryMemo = (DiaryMemo) listEntry.getItemAtPosition(position);

                long itemId = diaryMemo.getId();


                Intent nextIntent = new Intent(MainActivity.this, Diary2Activity.class);
                nextIntent.putExtra("itemId", itemId);
                startActivity(nextIntent);
            }
        });

        initializeContextualActionBar();

    }


    public void showAllListEntries(){

        List<DiaryMemo> diaryMemoList = diaryDataSource.getAllDiaryMemos();

        ArrayAdapter<DiaryMemo> diaryMemoArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, diaryMemoList);

        ListView diaryMemoListView = (ListView)findViewById(R.id.itemListView);
        diaryMemoListView.setAdapter(diaryMemoArrayAdapter);
    }



    private void initializeContextualActionBar(){

        final ListView diaryMemosListView = (ListView)findViewById(R.id.itemListView);
        diaryMemosListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        diaryMemosListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {

                getMenuInflater().inflate(R.menu.menu_acb, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                switch(item.getItemId()){

                    case R.id.cab_delete:

                        SparseBooleanArray touchedDiaryMemosPositions =
                                diaryMemosListView.getCheckedItemPositions();

                        for(int i=0; i<touchedDiaryMemosPositions.size(); i++){
                            boolean isChecked = touchedDiaryMemosPositions.valueAt(i);
                            if(isChecked){

                                int positionInListView = touchedDiaryMemosPositions.keyAt(i);
                                DiaryMemo diaryMemo = (DiaryMemo) diaryMemosListView.getItemAtPosition(positionInListView);
                                Log.d(LOG_TAG, "Position im ListView: " + positionInListView +
                                        " Inhalt: " + diaryMemo.toString());
                                diaryDataSource.deleteDiaryMemo(diaryMemo);
                            }
                        }
                        showAllListEntries();
                        mode.finish();
                        return true;

                    default:
                        return false;
                }
            }



            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });

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
        if (id==R.id.map_settings){
            Intent nextIntent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(nextIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
