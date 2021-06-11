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

import com.bridgeLabz.bookstore.Model.BookModel;
import com.bridgeLabz.bookstore.Model.BookResponseModel;
import com.bridgeLabz.bookstore.Model.UserModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.Repository.BookRepository;
import com.bridgeLabz.bookstore.Repository.UserRepository;
import com.bridgeLabz.bookstore.UI.Adapters.BooksListAdapter;
import com.bridgeLabz.bookstore.helper.BookAssetLoader;
import com.bridgeLabz.bookstore.helper.OnBookListener;
import com.bridgeLabz.bookstore.helper.SharedPreference;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BooksListFragment extends Fragment {

    private BooksListAdapter booksListAdapter;
    private static final String TAG = "BooksListFragment";
    private ArrayList<BookModel> bookList = new ArrayList<>();
    private RecyclerView recyclerView;
    private int spanCount;
    private BookFragment bookFragment;
    private BookRepository bookRepository;
    private UserRepository userRepository;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_books_list, container, false);
        int orientation = getResources().getConfiguration().orientation;
        File userListFile = new File(getContext().getFilesDir(), "users.json");
        BookAssetLoader bookAssetLoader = new BookAssetLoader(getContext());
        userRepository = new UserRepository(userListFile, new SharedPreference(getContext()), bookAssetLoader);
        bookRepository = new BookRepository(userListFile, userRepository, bookAssetLoader);
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
        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {

        ArrayList<BookModel> bookArrayList = bookRepository.getBookList();
        booksListAdapter = new BooksListAdapter(bookArrayList, new OnBookListener() {
            @Override
            public void onBookClick(int position, View viewHolder) {
                int bookId = booksListAdapter.getItem(position).getBookId();
                bookFragment = new BookFragment();
                Bundle bundle = new Bundle();

                bundle.putInt("BookId", bookId);

                bookFragment.setArguments(bundle);

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.home_fragment_container, bookFragment)
                        .addToBackStack(null).commit();
            }
        });
        recyclerView.setAdapter(booksListAdapter);
        booksListAdapter.notifyDataSetChanged();
    }

}