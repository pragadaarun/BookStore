package com.bridgeLabz.bookstore.UI.ViewHolders;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bridgeLabz.bookstore.Model.BookModel;
import com.bridgeLabz.bookstore.Model.UserModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.helper.OnBookListener;
import com.bridgeLabz.bookstore.helper.SharedPreference;
import com.bumptech.glide.Glide;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BooksListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView bookTitle, bookAuthor, bookPrice;
    public ImageView bookImage;
    public CheckBox isFavouriteCheckBox;
    private final SharedPreference sharedPreference;
    OnBookListener onBookListener;

    public BooksListViewHolder(@NonNull View itemView, OnBookListener onBookListener) {
        super(itemView);
        bookTitle = itemView.findViewById(R.id.book_title);
        bookAuthor = itemView.findViewById(R.id.book_author);
        bookImage = itemView.findViewById(R.id.book_image);
        bookPrice = itemView.findViewById(R.id.book_price);
        isFavouriteCheckBox = itemView.findViewById(R.id.wish_item_checker);
        this.onBookListener = onBookListener;
        itemView.setOnClickListener(this);
        sharedPreference = new SharedPreference(itemView.getContext());
    }

    public void bind(BookModel book) {
        isFavouriteCheckBox.setChecked(book.isFavourite());
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthor());
        bookPrice.setText(String.valueOf(book.getPrice()));
        String imageUri = book.getBookImage();
        Glide.with(itemView.getContext())
                .load(imageUri)
                .into(bookImage);
    }

    @Override
    public void onClick(View v) {
        onBookListener.onBookClick(getBindingAdapterPosition(),v);
    }

    public void favouriteChanged(BookModel book, boolean isChecked) {
        ObjectMapper mapper = new ObjectMapper();
                if(isChecked) {
                    try {
                        List<UserModel> userList1 = mapper.readValue(new File(itemView.getContext().getFilesDir(),
                                "users.json"), new TypeReference<List<UserModel>>(){});
                        List<Integer> favoriteBooks = userList1.get(sharedPreference.getPresentUserId()).getFavouriteItemsList();
                        favoriteBooks.add(book.getBookId());
                        userList1.get(sharedPreference.getPresentUserId()).setFavouriteItemsList(favoriteBooks);
                        String updatedFile = mapper.writeValueAsString(userList1);
                        FileOutputStream fos = itemView.getContext().openFileOutput("users.json", Context.MODE_PRIVATE);
                        fos.write(updatedFile.getBytes());
                        fos.close();

                    } catch (IOException jsonParseException) {
                        jsonParseException.printStackTrace();
                    }
                } else {
                    List<UserModel> userList1 = null;
                    try {
                        userList1 = mapper.readValue(new File(itemView.getContext().getFilesDir(),
                                "users.json"), new TypeReference<List<UserModel>>() {
                        });
                        List<Integer> favoriteBooks = userList1.get(sharedPreference.getPresentUserId()).getFavouriteItemsList();
                        favoriteBooks.remove(Integer.valueOf(book.getBookId()) );
                        userList1.get(sharedPreference.getPresentUserId()).setFavouriteItemsList(favoriteBooks);
                        String updatedFile = mapper.writeValueAsString(userList1);
                        FileOutputStream fos = itemView.getContext().openFileOutput("users.json", Context.MODE_PRIVATE);
                        fos.write(updatedFile.getBytes());
                        fos.close();
                    } catch (IOException | IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            }
}