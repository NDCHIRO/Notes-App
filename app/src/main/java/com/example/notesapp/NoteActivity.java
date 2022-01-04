package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import java.io.IOException;

public class NoteActivity extends AppCompatActivity {
    static EditText editView;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Intent intent= getIntent();
        i = intent.getIntExtra("number",1);
        editView = findViewById(R.id.editText);
        editView.setText(MainActivity.wordsList.get(i));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.wordsList.set(i,editView.getText().toString());
        MainActivity.arrayAdapter.notifyDataSetChanged();
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.notesapp",MODE_PRIVATE);
        try {
            sharedPreferences.edit().putString("notes",ObjectSerializer.serialize(MainActivity.wordsList)).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}