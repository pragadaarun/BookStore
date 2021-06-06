package com.bridgeLabz.bookstore.UI.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bridgeLabz.bookstore.Model.BookModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.UI.ViewHolders.CartViewHolder;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private ArrayList<BookModel> booksList = new ArrayList<>();

    public CartAdapter(ArrayList<BookModel> booksList) {
        this.booksList = booksList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.book_preview,parent,false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        BookModel book = booksList.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }
}
