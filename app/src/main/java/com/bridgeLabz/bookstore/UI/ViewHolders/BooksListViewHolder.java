package com.bridgeLabz.bookstore.UI.ViewHolders;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bridgeLabz.bookstore.Model.BookModel;
import com.bridgeLabz.bookstore.Model.UserModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.helper.SharedPreference;
import com.bumptech.glide.Glide;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BooksListViewHolder extends RecyclerView.ViewHolder {

    public TextView bookTitle, bookAuthor, bookPrice;
    public ImageView bookImage;
    public CheckBox isFavourite;
    private final SharedPreference sharedPreference;

    public BooksListViewHolder(@NonNull View itemView) {
        super(itemView);
        bookTitle = itemView.findViewById(R.id.book_title);
        bookAuthor = itemView.findViewById(R.id.book_author);
        bookImage = itemView.findViewById(R.id.book_image);
        bookPrice = itemView.findViewById(R.id.book_price);
        isFavourite = itemView.findViewById(R.id.wish_item_checker);
        sharedPreference = new SharedPreference(itemView.getContext());
    }

    public void bind(BookModel book) {
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthor());
        bookPrice.setText(String.valueOf(book.getPrice()));
        String imageUri = book.getBookImage();
        Glide.with(itemView.getContext())
                .load(imageUri)
                .into(bookImage);
        isFavourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ObjectMapper mapper = new ObjectMapper();
                List<Integer> favoriteBooks = new ArrayList<>();
                if(isChecked) {
                    try {
                        List<UserModel> userList1 = mapper.readValue(new File(itemView.getContext().getFilesDir(),
                                "users.json"), new TypeReference<List<UserModel>>(){});
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
                        favoriteBooks.remove(book.getBookId());
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

        });
    }
}
