package com.bridgeLabz.bookstore.UI.Fragments;

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
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.UI.Adapters.BooksListAdapter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BooksListFragment extends Fragment {

    private BooksListAdapter booksListAdapter;
    private static final String TAG = "FragmentBooks";
    private ArrayList<BookModel> bookList = new ArrayList<>();
    //    private RecyclerView.LayoutManager layoutManager;
//    BookListManager bookListManager;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_books_list, container, false);
        final RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView = view.findViewById(R.id.bookList_RecyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        getBooks();

        return view;

    }

    private void getBooks() {

        try {
            String data = loadJSONFromAsset();
            ObjectMapper mapper = new ObjectMapper();
            ArrayList<BookModel> bookArrayList = mapper.readValue(data, new TypeReference<List<BookModel>>(){} );
            booksListAdapter = new BooksListAdapter(bookArrayList);
            recyclerView.setAdapter(booksListAdapter);
            booksListAdapter.notifyDataSetChanged();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("books.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        Log.e(TAG, "loadJSONFromAsset: " + json );
        return json;
    }
}