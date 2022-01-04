package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class NoteActivity extends AppCompatActivity {
    static EditText editView;
    int noteIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Intent intent= getIntent();
        noteIndex = intent.getIntExtra("number",1);
        editView = findViewById(R.id.editText);
        editView.setText(MainActivity.wordsList.get(noteIndex));
    }

    @Override
    public void onBackPressed() {
        //when back button is pressed the text in the editText is saved in wordList in the noteIndex's position which was sent
        //from the MainActivity and then notify the adapter to update
        super.onBackPressed();
        MainActivity.wordsList.set(noteIndex,editView.getText().toString());
        MainActivity.arrayAdapter.notifyDataSetChanged();
        // save data to sharedPreferences
        MainActivity.saveData();
    }
}