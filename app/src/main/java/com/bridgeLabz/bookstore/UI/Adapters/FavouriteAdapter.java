package com.bridgeLabz.bookstore.UI.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bridgeLabz.bookstore.Model.BookModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.UI.ViewHolders.BooksListViewHolder;
import com.bridgeLabz.bookstore.UI.ViewHolders.FavoriteViewHolder;
import com.bridgeLabz.bookstore.helper.OnBookListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavouriteAdapter extends RecyclerView.Adapter<FavoriteViewHolder> {

    private ArrayList<BookModel> booksList = new ArrayList<>();

    public FavouriteAdapter(ArrayList<BookModel> booksList) {
        this.booksList = booksList;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.book_preview,parent,false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        BookModel book = booksList.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }
}
