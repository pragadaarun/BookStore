package com.bridgeLabz.bookstore.UI.Fragments;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bridgeLabz.bookstore.Model.BookModel;
import com.bridgeLabz.bookstore.Model.UserModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.Repository.BookRepository;
import com.bridgeLabz.bookstore.UI.Adapters.BooksListAdapter;
import com.bridgeLabz.bookstore.UI.Adapters.FavouriteAdapter;
import com.bridgeLabz.bookstore.helper.OnBookListener;
import com.bridgeLabz.bookstore.helper.SharedPreference;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment {

    private BooksListAdapter booksListAdapter;
    private static final String TAG = "FavouriteFragment";
    private RecyclerView recyclerView;
    private int spanCount;
    private BookRepository bookRepository;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_books_list, container, false);
        bookRepository = new BookRepository(getContext());
        ArrayList<BookModel> favourites = getFavoriteBooks();
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            spanCount = 2;
        } else {
            // In portrait
            spanCount = 1;
        }
        final RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        recyclerView = view.findViewById(R.id.bookList_RecyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        booksListAdapter = new BooksListAdapter(favourites, new OnBookListener() {
            @Override
            public void onBookClick(int position, View viewHolder) {
                Toast.makeText(getContext(), "Book is in Favourite List", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(booksListAdapter);
        booksListAdapter.notifyDataSetChanged();
        return view;
    }

    private ArrayList<BookModel> getFavoriteBooks() {
        ArrayList<BookModel> favoriteBooks = new ArrayList<>();
        for(BookModel book : bookRepository.getBookList()){
            if(book.isFavourite()){
                favoriteBooks.add(book);
            }
        }
        return favoriteBooks;
    }

}