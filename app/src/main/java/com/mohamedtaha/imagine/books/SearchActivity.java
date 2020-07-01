package com.mohamedtaha.imagine.books;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URL;

public class SearchActivity extends AppCompatActivity {
    public static final String SEARCH_QUERY = "search_query";
    private EditText title;
    private EditText author;
    private EditText publisher;
    private EditText isbn;
    private Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        title = findViewById(R.id.ET_Title);
        author = findViewById(R.id.ET_Author);
        publisher = findViewById(R.id.ET_Publisher);
        isbn = findViewById(R.id.ET_Isbn);
        search = findViewById(R.id.Search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string_title = title.getText().toString().trim();
                String string_author = author.getText().toString().trim();
                String string_publisher = publisher.getText().toString().trim();
                String string_isbn = isbn.getText().toString().trim();
                if (string_title.isEmpty() && string_author.isEmpty()
                        && string_publisher.isEmpty() && string_isbn.isEmpty()){
                    String message  = getString(R.string.must_fill_all_field);
                    Toast.makeText(SearchActivity.this, message, Toast.LENGTH_SHORT).show();
                }else {
                    URL queryUrl = ApiUtli.buildUrl(string_title,string_author,string_publisher,string_isbn);
                    //SharedPreferences
                    Context context = getApplicationContext();
                    int position = SPUtil.getPreferencesInt(context,SPUtil.POSITION);
                    if (position == 0 || position == 5){
                        position = 1;
                    }else {
                        position++;
                    }
                    String key = SPUtil.QUERY + String.valueOf(position);
                    String value = title + ","+author + "," + publisher + "," + isbn;
                    SPUtil.setPreferencesString(context,key,value);
                    SPUtil.setPreferencesInt(context,SPUtil.POSITION,position);
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra(SEARCH_QUERY, queryUrl);
                    startActivity(intent);
                }

            }
        });


    }
}