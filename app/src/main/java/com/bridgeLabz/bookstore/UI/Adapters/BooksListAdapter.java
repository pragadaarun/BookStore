package com.bridgeLabz.bookstore.UI.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.bridgeLabz.bookstore.Model.BookModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.UI.ViewHolders.BooksListViewHolder;
import com.bridgeLabz.bookstore.helper.OnBookListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//@GlideModule
public class BooksListAdapter extends RecyclerView.Adapter<BooksListViewHolder> {

    private ArrayList<BookModel> booksList = new ArrayList<>();
    private static final String TAG = "BooksListAdapter";
    private OnBookListener onBookListener;

    public BooksListAdapter(ArrayList<BookModel> booksList, OnBookListener onBookListener) {
        this.booksList = booksList;
        this.onBookListener = onBookListener;
    }

    @NonNull
    @Override

    public BooksListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.book_preview,parent,false);
        return new BooksListViewHolder(view, onBookListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksListViewHolder holder, int position) {
        BookModel book = booksList.get(position);
        holder.bind(book);
        holder.isFavouriteCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                holder.favouriteChanged(book, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public BookModel getItem(int position) {
        try{
            return booksList.get(position);
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return null;
    }
}
