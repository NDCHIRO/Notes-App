package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> wordsList;
    ListView listView;
    SharedPreferences sharedPreferences;
    static ArrayAdapter arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            //as it a new element so it will be put in the list in the end so we will send the index as size-1
            case R.id.add:
                wordsList.add("");
                Intent intent = new Intent(MainActivity.this,NoteActivity.class);
                intent.putExtra("number",wordsList.size()-1);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        wordsList = new ArrayList<String>();
        sharedPreferences = this.getSharedPreferences("com.example.notesapp",MODE_PRIVATE);
        try {
            wordsList = (ArrayList<String>)ObjectSerializer.deserialize(sharedPreferences.getString("notes",ObjectSerializer.serialize(new ArrayList<String>())));
            Log.i("info",wordsList.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }

        listView = findViewById(R.id.listView);
        arrayAdapter =new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, wordsList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
            {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //Toast.makeText(getApplicationContext(), "long clicked", Toast.LENGTH_SHORT).show();
                    //there are two i's so we need to save it in another name
                    int deletedNoteIndex = i;
                    new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.star_on)
                            .setTitle("delete note")
                            .setMessage("Do you want to delete this note?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    wordsList.remove(deletedNoteIndex);
                                    arrayAdapter.notifyDataSetChanged();
                                    try {
                                        sharedPreferences.edit().putString("notes",ObjectSerializer.serialize(MainActivity.wordsList)).apply();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            })
                            .setNegativeButton("No",null).show();
                    return false;
                }
            });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,NoteActivity.class);
                intent.putExtra("number",i);
                startActivity(intent);
            }
        });
    }
}