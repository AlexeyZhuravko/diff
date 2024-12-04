package com.example.android5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class NoteDetailFragment extends Fragment {

    private TextView noteTitle;
    private TextView noteContent;

    public static NoteDetailFragment newInstance(Note note) {
        NoteDetailFragment fragment = new NoteDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("note", note);  // Передаем заметку
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note_detail, container, false);
        noteTitle = rootView.findViewById(R.id.note_title);
        noteContent = rootView.findViewById(R.id.note_content);

        if (getArguments() != null) {
            Note note = (Note) getArguments().getSerializable("note");
            noteTitle.setText(note.getTitle());
            noteContent.setText(note.getContent());
        }

        return rootView;
    }
}