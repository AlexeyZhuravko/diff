package com.example.android_2;
import android.content.Intent;
import android.os.Bundle; //для работы с состоянием приложения
import android.util.Log;
import android.widget.Button; // для работы с элементами пользовательского интерфейса
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity; //базовый класс для активности приложения

import java.util.ArrayList; //для создания списков

public class MainActivity extends AppCompatActivity {
    // Поля для ввода данных
    private EditText etName, etDescription;
    // Список для хранения всех заметок
    private final ArrayList<TaskModel> tasks = new ArrayList<>();
    // Индекс текущей заметки
    private int currentIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //обращается к методу onCreate родительского класса AppCompatActivity
        setContentView(R.layout.activity_main); //метод устанавливает пользовательский интерфейс для текущей активности, используя XML-разметку

        // Инициализация элементов пользовательского интерфейса
        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnSave = findViewById(R.id.btnEdit);
        Button btnShowNote = findViewById(R.id.btnShowNote);
        ImageButton btnPrev = findViewById(R.id.btnPrev);
        ImageButton btnNext = findViewById(R.id.btnNext);

        // Восстанавливаем состояние, если оно существует
        if (savedInstanceState != null) {
            etName.setText(savedInstanceState.getString("name", ""));
            etDescription.setText(savedInstanceState.getString("description", ""));
            currentIndex = savedInstanceState.getInt("currentIndex", -1);

            // Восстанавливаем список заметок
            ArrayList<TaskModel> savedTasks = (ArrayList<TaskModel>)savedInstanceState.getSerializable("tasks");
            if (savedTasks != null) {
                tasks.addAll(savedTasks);
            }
        }

        // Установка обработчиков событий для кнопок
        btnAdd.setOnClickListener(v -> addTask());
        btnSave.setOnClickListener(v -> editTask());
        btnPrev.setOnClickListener(v -> showPreviousTask());
        btnNext.setOnClickListener(v -> showNextTask());
        btnShowNote.setOnClickListener(v -> showLastTask());

        Log.d("onCreate()", "метод вызывается при создании активности, когда она загружается в память,\n" +
                                      "но еще не отображена на экране. Здесь обычно происходит инициализация \n" +
                                      "компонентов интерфейса и других объектов");
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("onStart()", "вызывается, когда активность становится видимой, но еще не находится в \n" +
                                     "фокусе. Это момент, когда приложение начало отображаться на экране, но \n" +
                                     "пользователь не может с ним взаимодействовать.");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResume()", "метод вызывается, когда активность полностью находится на переднем плане и \n" +
                                      "готова к взаимодействию с пользователем.");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("onPause()", "активность уходит на второй план, например, если появляется другая \n" +
                                     "активность или приложение теряет фокус. Этот метод часто используется \n" +
                                     "для сохранения состояния или остановки тяжелых операций");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("onStop()", "вызывается, когда активность больше не видна пользователю. Она \n" +
                                    "больше не отображается на экране, но еще не уничтожена");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("onDestroy()", "вызывается, когда активность уничтожается, например, при закрытии \n" +
                                        "приложения или при освобождении ресурсов.");
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Сохраняем текущие данные полей и индекс
        outState.putString("name", etName.getText().toString());
        outState.putString("description", etDescription.getText().toString());
        outState.putInt("currentIndex", currentIndex);

        // Сохраняем список заметок
        outState.putSerializable("tasks", tasks);
    }
    // Метод для показа предыдущей заметки
    private void showPreviousTask() {
        //уменьшаем currentIndex и отображает предыдущую заметку, если текущая не первая
        if (currentIndex > 0) {
            currentIndex--;
            displayTask(tasks.get(currentIndex));
        } else {
            Toast.makeText(this, "Это первая заметка", Toast.LENGTH_SHORT).show();
        }
    }
    // Метод для показа следующей заметки
    private void showNextTask() {
        if (currentIndex < tasks.size() - 1) {
            currentIndex++;
            displayTask(tasks.get(currentIndex));
        } else {
            Toast.makeText(this, "Это последняя заметка", Toast.LENGTH_SHORT).show();
        }
    }
    // Метод для показа последней добавленной заметки
    private void showLastTask() {
        //если список заметок не пуст, устанавливаем currentIndex на индекс последней заметки и отображаем ее
        if (!tasks.isEmpty()) {
            currentIndex = tasks.size() - 1;
            displayTask(tasks.get(currentIndex));
        } else {
            Toast.makeText(this, "Список заметок пуст", Toast.LENGTH_SHORT).show();
        }
    }
    // Метод отображения заметки в текстовых полях
    private void displayTask(TaskModel task) {
        etName.setText(task.getName());
        etDescription.setText(task.getDescription());
    }

    // Метод добавления новой заметки
    private void addTask() {
        // Отправляем Intent в EditNoteActivity с названием по умолчанию
        String defaultName = "Заметка " + (tasks.size() + 1);  // Создаем имя заметки по умолчанию
        Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
        intent.putExtra("name", defaultName);  // передаем имя заметки
        startActivityForResult(intent, 1);  // ожидаем результат из EditNoteActivity
    }

    // Метод редактирования текущей заметки
    private void editTask() {
        if (currentIndex >= 0 && currentIndex < tasks.size()) {
            TaskModel task = tasks.get(currentIndex); // извлекаем заметку по текущему индексу
            Intent intent = new Intent(this, EditNoteActivity.class);
            intent.putExtra("taskId", task.getId()); // передаем id заметки
            intent.putExtra("name", task.getName()); // передаем имя заметки.
            intent.putExtra("description", task.getDescription()); // передаем описание заметки.
            startActivityForResult(intent, 1); // Ожидаем результат от EditNoteActivity
        } else {
            Toast.makeText(this, "Нет выбранной заметки для редактирования", Toast.LENGTH_SHORT).show();
        }
    }

    // Метод для получения результата из EditNoteActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            int taskId = data.getIntExtra("taskId", -1); // Получаем id заметки
            String name = data.getStringExtra("name"); // Получаем имя заметки
            String description = data.getStringExtra("description"); // Получаем описание заметки

            // Находим заметку по id и обновляем её
            if (taskId >= 0 && taskId < tasks.size()) {
                TaskModel task = tasks.get(taskId);
                // Обновляем имя и описание заметки
                task.setName(name);
                task.setDescription(description);
                displayTask(task);  // Отображаем обновленную заметку
                Toast.makeText(this, "Заметка обновлена", Toast.LENGTH_SHORT).show();
            }else{
            // Создаем новую заметку и добавляем её в список
            TaskModel newTask = new TaskModel(tasks.size(), name, description);
            tasks.add(newTask);
            currentIndex = tasks.size() - 1; //Обновляем текущий индекс
            displayTask(newTask);  // Отображаем добавленную заметку
            Toast.makeText(this, "Новая заметка добавлена", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
