package com.bridgeLabz.bookstore.UI.Fragments;

import android.content.Context;
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
import com.bridgeLabz.bookstore.Model.Review;
import com.bridgeLabz.bookstore.Model.UserModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.Repository.BookRepository;
import com.bridgeLabz.bookstore.Repository.ReviewRepository;
import com.bridgeLabz.bookstore.Repository.UserRepository;
import com.bridgeLabz.bookstore.UI.Adapters.BooksListAdapter;
import com.bridgeLabz.bookstore.helper.BookAssetLoader;
import com.bridgeLabz.bookstore.helper.OnBookListener;
import com.bridgeLabz.bookstore.helper.SharedPreference;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        File reviewsFile = new File(getContext().getFilesDir(), "reviews.json");
        BookAssetLoader bookAssetLoader = new BookAssetLoader(getContext());
        userRepository = new UserRepository(userListFile, new SharedPreference(getContext()), bookAssetLoader, new ReviewRepository(reviewsFile));
        bookRepository = new BookRepository(userListFile, userRepository, bookAssetLoader, new ReviewRepository(reviewsFile));
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
        createReviewFile();
        initRecyclerView();

        return view;
    }

    private void createReviewFile() {
        List<Review> reviewList = new ArrayList<Review>();
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(getContext().getFilesDir(), "reviews.json");
        try {
            Random random = new Random();
            int max = 5;
            int min = 1;
            for (int i = 1; i <= bookRepository.getBookList().size(); i++){
                for (int j = 1; j < 11; j++){
                    String userName ="random user " + j;
                    long reviewID = System.currentTimeMillis();
                    float userRating = min + random.nextFloat() * (max - min);
                    String userReview = null;
                    if(userRating >= 4) {
                        userReview = "Excellent Book";
                    } else if(userRating >= 3 && userRating < 4) {
                        userReview = "Good Book";
                    } else if(userRating >= 2 && userRating <3) {
                        userReview = "I not think it's Worthy";
                    } else {
                        userReview = "I don,t like this book";
                    }
                    Review review = new Review(userName, j, reviewID, i, userRating, userReview);
                    reviewList.add(review);
                }
            }
            if (!file.exists()) {
                String jsonStr = mapper.writeValueAsString(reviewList);
                FileOutputStream fos = getContext().openFileOutput("reviews.json", Context.MODE_PRIVATE);
                fos.write(jsonStr.getBytes());
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

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