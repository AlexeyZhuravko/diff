package com.example.android5;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import java.util.List;
//DAO — это интерфейс, через который приложение будет взаимодействовать с базой данных
@Dao
public interface NoteDao {

    // Вставить новую заметку
    @Insert
    void insert(Note note);

    // Обновить заметку
    @Update
    void update(Note note);

    // Получить все заметки
    @Query("SELECT * FROM notes")
    List<Note> getAllNotes();
}
