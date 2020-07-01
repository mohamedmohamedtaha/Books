package com.mohamedtaha.imagine.books;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    public static final String CURRENT_BOOK = "current_book";
    ArrayList<BookModel> bookModels;

    public BookAdapter(ArrayList<BookModel> bookModels) {
        this.bookModels = bookModels;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.books_list_item, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        BookModel bookModel = bookModels.get(position);
        holder.bind(bookModel);
    }

    @Override
    public int getItemCount() {
        return bookModels.size();
    }


    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_title;
        TextView tv_authors;
        TextView tv_publisher;
        TextView tv_publisher_date;
        ImageView image ;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.TV_Title);
            tv_authors = itemView.findViewById(R.id.TV_Authors);
            tv_publisher = itemView.findViewById(R.id.TV_Publisher);
            tv_publisher_date = itemView.findViewById(R.id.TV_Publisher_Date);
            image = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            BookModel currentBook = bookModels.get(position);
            Intent intent = new Intent(itemView.getContext(), BookDetailActivity.class);
            intent.putExtra(CURRENT_BOOK, currentBook);
            itemView.getContext().startActivity(intent);

        }

        public void bind(BookModel book) {
            tv_title.setText(book.getTitle());
//            String authors = "";
//            int i=0;
//            for (String author:book.getAuthors()){
//                authors+= author;
//                i++;
//                if (i<book.getAuthors().length) {
//                    authors+=", ";
//                }
//                tv_authors.setText(authors);
            tv_authors.setText(book.getAuthors());
            tv_publisher.setText(book.getPublisher());
            tv_publisher_date.setText(book.getPublishedDate());
            Picasso.with(itemView.getContext()).load(book.getThumbnail()).placeholder(R.drawable.book_open).into(image);
      //  }
    }
}
}
