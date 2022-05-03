package com.example.booklet;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "book")
public class Book {
    String title;
    String author;
    String summary;
    String pathToCoverImage;

    @PrimaryKey(autoGenerate=true)
    private int id;

    public Book(String title, String author, String summary) {
        this.title = title;
        this.author = author;
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPathToCoverImage() {
        return pathToCoverImage;
    }

    public void setPathToCoverImage(String pathToCoverImage) {
        this.pathToCoverImage = pathToCoverImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
