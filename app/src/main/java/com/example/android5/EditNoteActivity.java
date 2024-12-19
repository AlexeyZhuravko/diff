package com.example.android5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class EditNoteActivity extends AppCompatActivity {
    private EditText etNoteTitle, etNoteContent; // Поля для ввода заголовка и содержания заметки
    private CheckBox checkboxCompleted; // Чекбокс для обозначения статуса выполнения заметки
    private Button btnSaveNote; // Кнопка для сохранения заметки

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        // Привязываем элементы интерфейса к соответствующим переменным
        etNoteTitle = findViewById(R.id.etNoteTitle); // Поле для заголовка
        etNoteContent = findViewById(R.id.etNoteContent); // Поле для содержания
        checkboxCompleted = findViewById(R.id.checkboxCompleted); // Чекбокс выполненности
        btnSaveNote = findViewById(R.id.btnSaveNote); // Кнопка "Сохранить"
        // Устанавливаем обработчик нажатия на кнопку "Сохранить"
        btnSaveNote.setOnClickListener(v -> {
            // Считываем данные из полей ввода
            String title = etNoteTitle.getText().toString(); // Получаем текст заголовка
            String content = etNoteContent.getText().toString();// Получаем текст содержания
            boolean isCompleted = checkboxCompleted.isChecked(); // Считываем состояние чекбокса

            // Создаем новый Intent и передаем данные обратно в MainActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("title", title); // Передаем заголовок заметки
            resultIntent.putExtra("content", content); // Передаем содержание заметки
            resultIntent.putExtra("isCompleted", isCompleted); // Передаем состояние чекбокса
            setResult(RESULT_OK, resultIntent); // Устанавливаем результат выполнения Activity
            finish(); // Закрываем EditNoteActivity и возвращаемся в MainActivity
        });
    }
}