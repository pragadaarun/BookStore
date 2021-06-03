package com.bridgeLabz.bookstore.UI.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bridgeLabz.bookstore.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BooksListViewHolder extends RecyclerView.ViewHolder {

    public TextView bookTitle, bookAuthor, bookPrice;
    public ImageView bookImage;

    public BooksListViewHolder(@NonNull View itemView) {
        super(itemView);
        bookTitle = itemView.findViewById(R.id.book_title);
        bookAuthor = itemView.findViewById(R.id.book_author);
        bookImage = itemView.findViewById(R.id.book_image);
        bookPrice = itemView.findViewById(R.id.book_price);

    }
}
