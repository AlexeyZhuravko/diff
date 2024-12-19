package com.example.android5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class NoteDetailFragment extends Fragment {
    private Note note; // Объект заметки, который будет отображаться во фрагменте
    private CheckBox checkBox; // Чекбокс для отображения состояния выполнения заметки

    public static NoteDetailFragment newInstance(Note note) {
        NoteDetailFragment fragment = new NoteDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("note", note); // Упаковываем объект заметки в Bundle
        fragment.setArguments(args); // Передаём аргументы в фрагмент
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note_detail, container, false); // Загружаем макет для фрагмента
        // Проверяем, переданы ли аргументы в фрагмент
        if (getArguments() != null) {
            note = (Note) getArguments().getSerializable("note"); // Извлекаем объект заметки из переданных аргументов
        }
        // Находим элементы интерфейса в макете
        TextView title = rootView.findViewById(R.id.note_title); // Поле для заголовка заметки
        TextView content = rootView.findViewById(R.id.note_content); // Поле для содержания заметки
        checkBox = rootView.findViewById(R.id.note_checkbox); // Чекбокс для состояния выполнения

        if (note != null) {
            title.setText(note.getTitle()); // Устанавливаем заголовок заметки
            content.setText(note.getContent()); // Устанавливаем содержание заметки
            checkBox.setChecked(note.isCompleted()); // Устанавливаем состояние чекбокса
            checkBox.setEnabled(false); // Делаем чекбокс неизменяемым
        }
        return rootView;
    }
}
