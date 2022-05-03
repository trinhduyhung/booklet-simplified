package com.example.booklet;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookDao { // Data access object
    @Insert
    void addBook(Book book);

    @Query("SELECT * FROM book ORDER BY title")
    List<Book> getAllBooks();

}
