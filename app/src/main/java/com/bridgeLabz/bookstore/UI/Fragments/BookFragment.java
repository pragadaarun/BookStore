package com.bridgeLabz.bookstore.UI.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bridgeLabz.bookstore.Model.BookModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.Repository.BookRepository;
import com.bumptech.glide.Glide;

public class BookFragment extends Fragment {

    private ImageView bookImage;
    private TextView bookTitle, bookAuthor;
    private Button addToCart;
    BookRepository bookRepository;
    private int bookID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book, container, false);

        bookID = getArguments().getInt("BookID");
        findViews(view);
        setViews();
        setClickListeners();
        return view;
    }

    private void findViews(View view) {
        bookImage = view.findViewById(R.id.book_image_view);
        bookTitle = view.findViewById(R.id.book_title_text_view);
        bookAuthor = view.findViewById(R.id.book_author_text_view);
        addToCart = view.findViewById(R.id.add_cart_text);
    }

    private void setViews() {
        bookRepository = new BookRepository(getContext());
        BookModel book = bookRepository.getBook(bookID);
        String imageUri = book.getBookImage();
        Glide.with(getContext())
                .load(imageUri)
                .into(bookImage);
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthor());
    }

    private void setClickListeners() {
        addToCart.setOnClickListener(v -> {
            bookRepository.addBookToCart(bookID);
            addToCart.setEnabled(false);
        });
    }
}