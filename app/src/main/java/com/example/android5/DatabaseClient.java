package com.example.android5;

import android.content.Context;
import androidx.room.Room;
//Этот класс управляет созданием и доступом к экземпляру базы данных.
public class DatabaseClient {

    private static AppDatabase instance;

    private DatabaseClient(Context context) {
        // Создаем базу данных Room
        instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "notes-database")
                .fallbackToDestructiveMigration()  // Позволяет избавиться от старой базы данных при изменении схемы
                .build();
    }

    // Метод для получения экземпляра базы данных
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseClient(context).getAppDatabase();
        }
        return instance;
    }

    public AppDatabase getAppDatabase() {
        return instance;
    }
}
