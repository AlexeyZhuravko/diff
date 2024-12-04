package com.example.android5;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

public class NoteListFragment extends Fragment {

    private ListView listView;
    private ArrayList<Note> notes;
    private OnNoteSelectedListener listener;

    public interface OnNoteSelectedListener {
        void onNoteSelected(Note note);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note_list, container, false);
        listView = rootView.findViewById(R.id.note_list);
        notes = getNotes(); // Заполните вашими заметками

        ArrayAdapter<Note> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Note selectedNote = notes.get(position);
            listener.onNoteSelected(selectedNote);
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNoteSelectedListener) {
            listener = (OnNoteSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnNoteSelectedListener");
        }
    }

    // Метод для получения списка заметок
    private ArrayList<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        // Добавьте свои заметки в список
        notes.add(new Note("Заметка 1", "Содержимое заметки 1"));
        notes.add(new Note("Заметка 2", "Содержимое заметки 2"));
        return notes;
    }
}