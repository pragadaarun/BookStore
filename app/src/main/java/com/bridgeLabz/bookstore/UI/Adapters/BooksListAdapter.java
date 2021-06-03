package com.bridgeLabz.bookstore.UI.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bridgeLabz.bookstore.Model.BookModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.UI.ViewHolders.BooksListViewHolder;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BooksListAdapter extends RecyclerView.Adapter<BooksListViewHolder> {

    private ArrayList<BookModel> booksList = new ArrayList<>();

    public BooksListAdapter(ArrayList<BookModel> booksList) {
        this.booksList = booksList;
    }

    @NonNull
    @Override
    public BooksListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.book_preview,parent,false);
        return new BooksListViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull BooksListViewHolder holder, int position) {
        BookModel book = booksList.get(position);
        holder.bookTitle.setText(book.getTitle());
        holder.bookAuthor.setText(book.getAuthor());
        holder.bookPrice.setText(String.valueOf(book.getPrice()));
        String imageUri = book.getBookImage();
        Glide.with(holder.itemView.getContext())
                .load(imageUri)
                .into(holder.bookImage);
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }
}
