package com.example.android5;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class NoteDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        if (getIntent().hasExtra("note")) {
            Note note = (Note) getIntent().getSerializableExtra("note");
            if (note != null) {
                NoteDetailFragment fragment = NoteDetailFragment.newInstance(note);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
        }
    }
}