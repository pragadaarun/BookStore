package com.bridgeLabz.bookstore.UI.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.bridgeLabz.bookstore.Repository.CartRepository;
import com.bridgeLabz.bookstore.Repository.UserRepository;
import com.bridgeLabz.bookstore.helper.BookAssetLoader;
import com.bridgeLabz.bookstore.helper.SharedPreference;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.Objects;

public class BookFragment extends Fragment {

    private ImageView bookImage;
    private TextView bookTitle, bookAuthor;
    private Button addToCart;
    BookRepository bookRepository;
    UserRepository userRepository;
    private int bookID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        File userListFile = new File(getContext().getFilesDir(), "users.json");
        BookAssetLoader bookAssetLoader = new BookAssetLoader(getContext());
        userRepository = new UserRepository(userListFile, new SharedPreference(getContext()), bookAssetLoader);
        bookRepository = new BookRepository(userListFile, userRepository, bookAssetLoader);

        bookID = getArguments().getInt("BookID");
        findViews(view);
        setViews();
        setClickListeners();
        onBackPressed(view);
        return view;
    }

    private void findViews(View view) {
        bookImage = view.findViewById(R.id.book_image_view);
        bookTitle = view.findViewById(R.id.book_title_text_view);
        bookAuthor = view.findViewById(R.id.book_author_text_view);
        addToCart = view.findViewById(R.id.add_cart_text);
    }

    private void setViews() {
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

    private void onBackPressed(View view) {

        Toolbar favoriteToolbar = (Toolbar) view.findViewById(R.id.book_toolbar);
        favoriteToolbar.setTitle("Favourite Books");
        favoriteToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        favoriteToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle any click event
                getParentFragmentManager().popBackStack();

            }
        });
    }

    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }
}