package com.example.android_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditNoteActivity extends AppCompatActivity {
    private EditText etName, etDescription;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //устанавливается макет для активности
        setContentView(R.layout.activity_edit_note);

        etName = findViewById(R.id.etEditName);
        etDescription = findViewById(R.id.etEditDescription);
        btnSave = findViewById(R.id.btnSaveEditedNote);

        // Получаем переданные данные
        final int taskId = getIntent().getIntExtra("taskId", -1); // получаем id заметки
        String name = getIntent().getStringExtra("name"); // получаем имя заметки
        String description = getIntent().getStringExtra("description"); // получаем описание заметки

        etName.setText(name);  // Устанавливаем имя
        etDescription.setText(description);  // Устанавливаем описание

        //Устанавливаем обработчик нажатия на кнопку
        btnSave.setOnClickListener(v -> {
            // Создаем Intent с обновленными данными
            Intent resultIntent = new Intent();
            resultIntent.putExtra("taskId", taskId);  // передаем id заметки
            resultIntent.putExtra("name", etName.getText().toString()); // передаем имя заметки
            resultIntent.putExtra("description", etDescription.getText().toString()); //передаем описание заметки
            setResult(RESULT_OK, resultIntent); //отправляем результат обратно в родительскую активность
            finish();  // Закрываем активность и возвращаем результат в MainActivity
        });
    }
}
