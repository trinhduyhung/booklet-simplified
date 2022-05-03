package com.example.booklet;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Datasource {

    BookDao bookDao;

    Datasource(Application context) {
        bookDao = BookDatabase.getInstance(context).bookDao();
    }

    public void addBook(Book book) {
        try {
            new AddBookTask(bookDao).execute(book).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Book> getAllBooks() {
        try {
            return new GetAllBooksTask(bookDao).execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class AddBookTask extends AsyncTask<Book, Void, Void> {

        private BookDao bookDao;

        public AddBookTask(BookDao dao) {
            bookDao = dao;
        }

        @Override
        protected Void doInBackground(Book... books) {
            // happens on a new thread
            bookDao.addBook(books[0]);
            return null;
        }
    }

    private static class GetAllBooksTask extends AsyncTask<Void, Void, List<Book>> {

        private BookDao dao;

        public GetAllBooksTask(BookDao dao) {
            this.dao = dao;
        }

        @Override
        protected List<Book> doInBackground(Void... voids) {
            return dao.getAllBooks();
        }
    }
}
