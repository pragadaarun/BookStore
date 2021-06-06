package com.bridgeLabz.bookstore.UI.ViewHolders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bridgeLabz.bookstore.Model.BookModel;
import com.bridgeLabz.bookstore.R;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartViewHolder extends RecyclerView.ViewHolder {

    public TextView bookTitle, bookAuthor, bookPrice;
    public ImageView bookImage;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        bookTitle = itemView.findViewById(R.id.book_title);
        bookAuthor = itemView.findViewById(R.id.book_author);
        bookImage = itemView.findViewById(R.id.book_image);
        bookPrice = itemView.findViewById(R.id.book_price);
    }

    public void bind(BookModel book) {
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthor());
        bookPrice.setText(String.valueOf(book.getPrice()));
        String imageUri = book.getBookImage();
        Glide.with(itemView.getContext())
                .load(imageUri)
                .into(bookImage);
    }
}
