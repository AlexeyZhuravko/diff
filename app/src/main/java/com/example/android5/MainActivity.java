package com.example.android5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements NoteListFragment.OnNoteSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addNoteButton = findViewById(R.id.btnAddNote); // Инициализируем кнопку для добавления новой заметки
        addNoteButton.setOnClickListener(v -> {
            // Запускаем активность для создания новой заметки
            Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
            startActivityForResult(intent, 1); // Запрашиваем результат с кодом запроса 1
        });

        // Проверяем наличие контейнера для фрагментов (landscape или portrait режим)
        if (findViewById(R.id.fragment_container) != null) {
            // Если есть сохранённое состояние, восстанавливать фрагмент не нужно
            if (savedInstanceState != null) {
                return;
            }

            // Создаём новый экземпляр списка заметок NoteListFragment
            NoteListFragment listFragment = new NoteListFragment();
            // Начинаем транзакцию для добавления фрагмента в контейнер
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, listFragment); // Добавляем фрагмент списка
            transaction.commit(); // Применяем изменения
        }
    }

    // Реализация интерфейса NoteListFragment.OnNoteSelectedListener
    // Обрабатываем выбор заметки из списка
    @Override
    public void onNoteSelected(Note note) {
        // Проверяем, есть ли контейнер для деталей заметки
        if (findViewById(R.id.detail_container) == null) {
            // Если контейнера нет, запускаем NoteDetailActivity для отображения деталей
            Intent intent = new Intent(this, NoteDetailActivity.class);
            intent.putExtra("note", note); // Передаём выбранную заметку
            startActivity(intent);
        } else {
            // Если контейнер есть, создаём новый экземпляр NoteDetailFragment
            NoteDetailFragment detailFragment = NoteDetailFragment.newInstance(note);

            // Начинаем транзакцию для замены фрагмента в контейнере деталей
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.detail_container, detailFragment); // Обновляем контейнер деталей
            transaction.commit(); // Применяем изменения
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Получаем результат из EditNoteActivity
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String title = data.getStringExtra("title"); // Заголовок заметки
            String content = data.getStringExtra("content"); // Содержимое заметки
            boolean isCompleted = data.getBooleanExtra("isCompleted", false); // Получаем состояние чекбокса
            // Создаем новую заметку
            Note newNote = new Note(title, content);
            newNote.setCompleted(isCompleted); // Устанавливаем состояние выполнения заметки
            // Создаем новый поток для работы с базой данных
            new Thread(() -> {
                AppDatabase db = DatabaseClient.getInstance(getApplicationContext()); // Получаем доступ к базе данных через DatabaseClient
                db.noteDao().insert(newNote);  // Вставляем заметку в базу данных

                // Обновляем список заметок в NoteListFragment
                runOnUiThread(() -> {
                    NoteListFragment listFragment = (NoteListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container); // Получаем ссылку на фрагмент NoteListFragment
                    if (listFragment != null) {
                        listFragment.addNewNote(newNote); // Добавляем новую заметку в список заметок фрагмента
                    }
                });
            }).start(); // Запускаем поток
        }
    }
}