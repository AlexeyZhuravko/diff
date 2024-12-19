package com.example.android5;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class NoteDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        // Проверяем, был ли передан объект заметки через Intent
        if (getIntent().hasExtra("note")) {
            Note note = (Note) getIntent().getSerializableExtra("note"); // Извлекаем объект заметки, переданный через Intent
            if (note != null) { // Если заметка не null, создаём и добавляем фрагмент для отображения её деталей
                NoteDetailFragment fragment = NoteDetailFragment.newInstance(note); // Создаём новый экземпляр NoteDetailFragment, передав ему заметку
                getSupportFragmentManager().beginTransaction() // Используем FragmentManager для добавления фрагмента в контейнер активности
                        .replace(R.id.fragment_container, fragment) // Заменяем содержимое контейнера новым фрагментом
                        .commit(); // Применяем транзакцию
            }
        }
    }
}