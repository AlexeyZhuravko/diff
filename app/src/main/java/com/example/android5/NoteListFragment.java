package com.example.android5;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NoteListFragment extends Fragment {

    private RecyclerView recyclerView; // Список для отображения заметок
    private ArrayList<Note> notes; // Список заметок
    private NoteAdapter adapter; // Адаптер для RecyclerView
    private OnNoteSelectedListener listener; // Слушатель выбора заметки
    // Интерфейс для передачи выбранной заметки в Activity
    public interface OnNoteSelectedListener {
        void onNoteSelected(Note note);
    }
    // Сохраняет состояние при повороте экрана
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("notes", notes); // Сохраняем список заметок
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //container - это ViewGroup, в который добавляется разметка фрагмента, false - указывает, что созданное представление (fragment_note_list) не должно автоматически добавляться в контейнер
        View rootView = inflater.inflate(R.layout.fragment_note_list, container, false);
        recyclerView = rootView.findViewById(R.id.note_list);

        // Загружаем данные из базы данных
        new Thread(() -> {
            AppDatabase db = DatabaseClient.getInstance(getContext()); // Получаем доступ к базе данных
            List<Note> notesFromDb = db.noteDao().getAllNotes();  // Получаем все заметки из базы данных

            // Обновляем UI с полученными заметками
            getActivity().runOnUiThread(() -> {
                notes = new ArrayList<>(notesFromDb); // Сохраняем загруженные заметки в список
                adapter = new NoteAdapter(notes, new NoteAdapter.OnNoteClickListener() { // Создаем адаптер для RecyclerView
                    @Override
                    public void onNoteClick(Note note) {
                        if (listener != null) {
                            listener.onNoteSelected(note);
                        }
                    }

                    @Override
                    public void onCheckboxToggle(Note note, boolean isChecked) {
                        note.setCompleted(isChecked); // Обработка изменения состояния чекбокса (выполнена или нет)

                        new Thread(() -> {
                            AppDatabase db = DatabaseClient.getInstance(getContext());
                            db.noteDao().update(note);  // Обновляем заметку в базе данных
                        }).start(); // Запускаем поток для обновления заметки
                    }
                });
                // Настройка RecyclerView
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); // Устанавливаем менеджер компоновки
                recyclerView.setAdapter(adapter); // Устанавливаем адаптер для RecyclerView
            });
        }).start(); // Запускаем поток

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNoteSelectedListener) {
            listener = (OnNoteSelectedListener) context; // Связываем listener с Activity
        } else {
            throw new RuntimeException(context.toString() + " must implement OnNoteSelectedListener");
        }
    }

    public void addNewNote(Note note) {
        notes.add(note); // Добавляем новую заметку в список
        adapter.notifyItemInserted(notes.size() - 1); // Уведомляем адаптер об изменении
    }
}