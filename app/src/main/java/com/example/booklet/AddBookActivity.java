package com.example.booklet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class AddBookActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    EditText title;
    EditText author;
    EditText summary;
    ImageView cover;
    Uri coverUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        title = findViewById(R.id.edt_title);
        author = findViewById(R.id.edt_author);
        summary = findViewById(R.id.edt_summary);
        cover = findViewById(R.id.img_cover);

        findViewById(R.id.btn_add_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add book to database

                // validate data from UI
                if (bookDataValidated()) {
                    Book book = new Book(title.getText().toString(),
                            author.getText().toString(),
                            summary.getText().toString());
                    book.setPathToCoverImage(coverUri.toString());
                    addBook(book);
                    startBooksActivity();
                }

            }
        });

        findViewById(R.id.img_cover).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickCoverImage();
            }
        });


    }

    private void startBooksActivity() {
        startActivity(new Intent(this, BooksActivity.class));
    }

    private void pickCoverImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select cover image"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            loadThenDisplayBitmap(data.getData());
        }

    }

    private void loadThenDisplayBitmap(Uri uri) {
        Picasso.get().load(uri).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                cover.setImageBitmap(bitmap);
                coverUri = uri;
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    private boolean bookDataValidated() {
        if (title.getText().toString().isEmpty()) return false;
        if (author.getText().toString().isEmpty()) return false;
        if (summary.getText().toString().isEmpty()) return false;
        if (coverUri == null) return false;
        return true;
    }

    private void addBook(Book book) { // happens on UI thread (main thread),
        new Datasource(getApplication()).addBook(book);
    }

}