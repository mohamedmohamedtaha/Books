package com.mohamedtaha.imagine.books;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.Toast;

import com.mohamedtaha.imagine.books.databinding.ActivityBookDetailBinding;

import static com.mohamedtaha.imagine.books.BookAdapter.CURRENT_BOOK;

public class BookDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        BookModel bookCurrent = getIntent().getParcelableExtra(CURRENT_BOOK);
        ActivityBookDetailBinding bookDetailBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_book_detail);
        bookDetailBinding.setBookModel(bookCurrent);
    }
}




