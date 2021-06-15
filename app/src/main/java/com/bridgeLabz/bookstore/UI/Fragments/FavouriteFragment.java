package com.bridgeLabz.bookstore.UI.Fragments;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bridgeLabz.bookstore.Model.BookModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.Repository.BookRepository;
import com.bridgeLabz.bookstore.Repository.ReviewRepository;
import com.bridgeLabz.bookstore.Repository.UserRepository;
import com.bridgeLabz.bookstore.UI.Adapters.BooksListAdapter;
import com.bridgeLabz.bookstore.helper.BookAssetLoader;
import com.bridgeLabz.bookstore.helper.OnBookListener;
import com.bridgeLabz.bookstore.helper.SharedPreference;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class FavouriteFragment extends Fragment {

    private BooksListAdapter booksListAdapter;
    private static final String TAG = "FavouriteFragment";
    private RecyclerView recyclerView;
    private int spanCount;
    private BookFragment bookFragment;
    UserRepository userRepository;
    private BookRepository bookRepository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if(activity != null) {
            if(activity.getSupportActionBar() != null){
                activity.getSupportActionBar().hide();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        File userListFile = new File(getContext().getFilesDir(), "users.json");
        File reviewsFile = new File(getContext().getFilesDir(), "reviews.json");
        BookAssetLoader bookAssetLoader = new BookAssetLoader(getContext());
        userRepository = new UserRepository(userListFile, new SharedPreference(getContext()), bookAssetLoader, new ReviewRepository(reviewsFile));
        bookRepository = new BookRepository(userListFile, userRepository, bookAssetLoader, new ReviewRepository(reviewsFile));
        ArrayList<BookModel> favourites = bookRepository.getFavoriteBooks();
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            spanCount = 2;
        } else {
            // In portrait
            spanCount = 1;
        }
        final RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        recyclerView = view.findViewById(R.id.favourite_RecyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        booksListAdapter = new BooksListAdapter(favourites, new OnBookListener() {
            @Override
            public void onBookClick(int position, View viewHolder) {
                int bookId = booksListAdapter.getItem(position).getBookId();
                bookFragment = new BookFragment();
                Bundle bundle = new Bundle();

                bundle.putInt("BookId", bookId);

                bookFragment.setArguments(bundle);

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.home_fragment_container, bookFragment)
                        .addToBackStack(null).commit();            }
        });
        recyclerView.setAdapter(booksListAdapter);
        booksListAdapter.notifyDataSetChanged();
        onBackPressed(view);
        return view;
    }

    private void onBackPressed(View view) {

        Toolbar favoriteToolbar = (Toolbar) view.findViewById(R.id.favourite_toolbar);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if(activity != null) {
            if(activity.getSupportActionBar() != null){
                activity.getSupportActionBar().show();
            }
        }
    }

}