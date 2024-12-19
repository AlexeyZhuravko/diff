package com.example.android5;

import androidx.room.Database;
import androidx.room.RoomDatabase;
//Этот класс управляет доступом к базе данных и связывает сущности и DAO.
@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    // Абстрактный метод для получения DAO
    public abstract NoteDao noteDao();
}

