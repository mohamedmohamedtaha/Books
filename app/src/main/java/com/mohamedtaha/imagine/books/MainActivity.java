package com.mohamedtaha.imagine.books;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static com.mohamedtaha.imagine.books.SearchActivity.SEARCH_QUERY;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ProgressBar progressBar;
    //  TextView Response_Data ;
    TextView Error_Message;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.Progress_Bar);
        recyclerView = findViewById(R.id.RecyclerView);
        // Response_Data = findViewById(R.id.Response_Data);
        Error_Message = findViewById(R.id.Error_Message);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL
                , false);
        recyclerView.setLayoutManager(linearLayoutManager);
        Intent intent = getIntent();
        String query = intent.getStringExtra(SEARCH_QUERY);
        URL bookUrl;
        try {
            if (query == null || query.isEmpty()) {
                bookUrl = ApiUtli.buildUrl("cooking");

            } else {
                bookUrl = ApiUtli.buildUrl(query);
            }
            new BooksQueryTask().execute(bookUrl);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.icon_advanced_search:
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
            return true;
            default:
                int position = item.getItemId() + 1;
                String preferenceName = SPUtil.QUERY + String.valueOf(position);
                String query = SPUtil.getPreferencesString(getApplicationContext(),preferenceName);
                String[] prefParams = query.split("\\,");
                String[] queryParams = new String[4];
                for (int i=0;i<prefParams.length;i++){
                    queryParams[i] = prefParams[i];
                }
                URL bookUrl = ApiUtli.buildUrl(
                        (queryParams[0]==null)?"":queryParams[0],
                        (queryParams[1]==null)?"":queryParams[1],
                        (queryParams[2]==null)?"":queryParams[2],
                        (queryParams[3]==null)?"":queryParams[3]);
                new BooksQueryTask().execute(bookUrl);
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        try {
            URL bookUrl = ApiUtli.buildUrl(query);
            new BooksQueryTask().execute(bookUrl);
        } catch (Exception e) {
            Log.d("TAG", e.getMessage());
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public class BooksQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchURL = urls[0];
            String result = null;
            try {
                result = ApiUtli.getJSon(searchURL);

            } catch (IOException e) {
                Log.e("Error", e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String jsonResult) {
            if (jsonResult == null) {
                progressBar.setVisibility(View.INVISIBLE);
                Error_Message.setVisibility(View.VISIBLE);

            } else {
                progressBar.setVisibility(View.INVISIBLE);
                Error_Message.setVisibility(View.INVISIBLE);
                ArrayList<BookModel> books = ApiUtli.getBooksFromJson(jsonResult);
                BookAdapter bookAdapter = new BookAdapter(books);
                recyclerView.setAdapter(bookAdapter);
            }
//            String resultString = "";
//            for (BookModel bookModel: books){
//                resultString = resultString + bookModel.getTitle() + "\n" + bookModel.getPublishedDate() +"\n\n";
//            }

            //  Response_Data.setText(resultString);

            super.onPostExecute(jsonResult);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_menu, menu);
        final MenuItem searchITem = menu.findItem(R.id.icon_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchITem);
        searchView.setOnQueryTextListener(this);
//        ArrayList<String>recentList = SPUtil.getQueryList(getApplicationContext());
//        int itemNum = recentList.size();
//        MenuItem recentMenu;
//        for (int i=0;i<itemNum; i++){
//            recentMenu = menu.add(Menu.NONE,i,Menu.NONE,recentList.get(i));
//        }
        return true;
    }
}




















