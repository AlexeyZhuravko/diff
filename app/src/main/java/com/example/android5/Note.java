package com.example.android5;

import java.io.Serializable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
//Это модель, которая представляет данные, которые будут сохраняться в базе данных.
// Указывает, что этот класс будет храниться в таблице "notes"
@Entity(tableName = "notes")  // Название таблицы в базе данных
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)  // Уникальный идентификатор заметки
    private int id;

    private String title;  // Заголовок заметки
    private String content;  // Содержимое заметки
    private boolean isCompleted;  // Флаг выполнения задачи

    // Конструктор для создания заметки
    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.isCompleted = false;  // По умолчанию задача не выполнена
    }

    // Геттеры и сеттеры для полей
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
