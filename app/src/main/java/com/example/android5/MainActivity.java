package com.example.android5;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements NoteListFragment.OnNoteSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {
            // Если это планшет, добавляем два фрагмента
            if (savedInstanceState != null) {
                return;
            }

            NoteListFragment listFragment = new NoteListFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, listFragment);
            transaction.commit();
        }
    }

    @Override
    public void onNoteSelected(Note note) {
        // Если это телефон, открываем новый экран с детальной информацией
        if (findViewById(R.id.detail_container) == null) {
            Intent intent = new Intent(this, NoteDetailActivity.class);
            intent.putExtra("note", note);
            startActivity(intent);
        } else {
            // Если это планшет, показываем детальную информацию в другом фрагменте
            NoteDetailFragment detailFragment = NoteDetailFragment.newInstance(note);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.detail_container, detailFragment);
            transaction.commit();
        }
    }
}

