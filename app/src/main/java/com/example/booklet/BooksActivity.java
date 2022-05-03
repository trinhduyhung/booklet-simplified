package com.example.booklet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BooksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        RecyclerView listBooks = findViewById(R.id.list_books);
        View txtNoBooks = findViewById(R.id.txt_no_books);

        Datasource repository = new Datasource(getApplication());
        List<Book> books = repository.getAllBooks();

        if (books.isEmpty()) {
            txtNoBooks.setVisibility(View.VISIBLE);
            listBooks.setVisibility(View.GONE);
        } else {
            txtNoBooks.setVisibility(View.GONE);
            listBooks.setVisibility(View.VISIBLE);

            listBooks.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            BookAdapter bookAdapter = new BookAdapter(books);
            listBooks.setAdapter(bookAdapter);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.books_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add) {
            startActivity(new Intent(this, AddBookActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    static class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<Book> books;

        public BookAdapter(List<Book> books) {
            this.books = books;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.book_item, parent, false);
            return new BookViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Book book = books.get(position);
            BookViewHolder bvh = (BookViewHolder) holder;
            bvh.bind(book);
        }

        @Override
        public int getItemCount() {
            return books.size();
        }
    }

    // ViewHolder is an object that holds views in each recycler view item

    static class BookViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCover;
        TextView title;
        TextView author;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCover = itemView.findViewById(R.id.img_cover);
            title = itemView.findViewById(R.id.txt_title);
            author = itemView.findViewById(R.id.txt_author);
        }

        public void bind(Book book) {
            title.setText(book.getTitle());
            author.setText(book.getAuthor());
            Picasso.get().load(book.getPathToCoverImage()).into(imgCover);
        }
    }

}