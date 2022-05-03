package com.example.booklet;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Book.class}, version = 1)
public abstract class BookDatabase extends RoomDatabase {

    public abstract BookDao bookDao();

    private static BookDatabase INSTANCE;

    public static BookDatabase getInstance(Context context) {

        if (INSTANCE == null) {
            synchronized (BookDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, BookDatabase.class, "book_store").build();
                }
            }
        }

        return INSTANCE;
    }
}
