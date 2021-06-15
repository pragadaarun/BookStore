package com.bridgeLabz.bookstore.UI.Fragments;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bridgeLabz.bookstore.Model.BookModel;
import com.bridgeLabz.bookstore.Model.Review;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.Repository.BookRepository;
import com.bridgeLabz.bookstore.Repository.ReviewRepository;
import com.bridgeLabz.bookstore.Repository.UserRepository;
import com.bridgeLabz.bookstore.UI.Adapters.ReviewAdapter;
import com.bridgeLabz.bookstore.helper.BookAssetLoader;
import com.bridgeLabz.bookstore.helper.SharedPreference;
import com.bumptech.glide.Glide;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookFragment extends Fragment {

    private ImageView bookImage;
    private TextView bookTitle, bookAuthor, bookPrice, bookDescription;
    private Button addToCart, addReviewButton;
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private int bookID;
    private BookModel book;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ReviewAdapter reviewAdapter;
    private ReviewFragment reviewFragment;
    private static final String TAG = "BookFragment";

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: " );
        showToolBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        File userListFile = new File(getContext().getFilesDir(), "users.json");
        File reviewsFile = new File(getContext().getFilesDir(), "reviews.json");
        BookAssetLoader bookAssetLoader = new BookAssetLoader(getContext());
        userRepository = new UserRepository(userListFile, new SharedPreference(getContext()), bookAssetLoader, new ReviewRepository(reviewsFile));
        bookRepository = new BookRepository(userListFile, userRepository, bookAssetLoader, new ReviewRepository(reviewsFile));
        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        bookID = getArguments().getInt("BookId");
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
        bookDescription = view.findViewById(R.id.book_description_text_view);
        bookPrice = view.findViewById(R.id.book_price_text_view);
        addToCart = view.findViewById(R.id.add_cart_text);
        addReviewButton = view.findViewById(R.id.book_add_review_button);
        recyclerView = view.findViewById(R.id.book_review_recyclerView);
    }

    private void setViews() {
        book = bookRepository.getBook(bookID);
        String imageUri = book.getBookImage();
        Glide.with(getContext())
                .load(imageUri)
                .into(bookImage);
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthor());
        bookDescription.setText(book.getDescription());
        bookPrice.setText(String.valueOf(book.getPrice()));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        ObjectMapper mapper = new ObjectMapper();
        List<Review> reviewsList = new ArrayList<>();
        List<Review> bookReviews = new ArrayList<>();
        try {
            reviewsList = mapper.readValue(new File(getContext().getFilesDir(), "reviews.json"),new TypeReference<List<Review>>(){} );
            Log.e(TAG, "review list: " + reviewsList );
            for (int i=0;i<reviewsList.size();i++) {
                if (reviewsList.get(i).getBookID() == bookID){
                    String userName = reviewsList.get(i).getUserName();
                    String review = reviewsList.get(i).getReview();
                    float rating = reviewsList.get(i).getRating();
                    long reviewID = reviewsList.get(i).getReviewID();
                    int bookId = reviewsList.get(i).getBookID();
                    int authorID = reviewsList.get(i).getUserID();
                    Review reviewModel = new Review(userName, authorID, reviewID, bookId, rating, review);
                    bookReviews.add(reviewModel);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        reviewAdapter = new ReviewAdapter(reviewsList);
        recyclerView.setAdapter(reviewAdapter);
        reviewAdapter.notifyDataSetChanged();
    }

    private void setClickListeners() {
        boolean isCarted = userRepository.isCarted(bookID);
        addToCart.setEnabled(isCarted);
        addToCart.setOnClickListener(v -> {
            bookRepository.addBookToCart(bookID);
            addToCart.setEnabled(false);
        });
        addReviewButton.setOnClickListener(v -> {
            reviewFragment = new ReviewFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("BookReviewID", bookID);
            reviewFragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.home_fragment_container, reviewFragment)
                    .addToBackStack(null).commit();
        });
    }

    private void onBackPressed(View view) {

        Toolbar favoriteToolbar = (Toolbar) view.findViewById(R.id.book_toolbar);
        favoriteToolbar.setTitle(book.getTitle());
        favoriteToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        favoriteToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle any click event
                getParentFragmentManager().popBackStack();

            }
        });
    }

    public void showToolBar(){
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if(activity != null) {
            if(activity.getSupportActionBar() != null){
                Log.e(TAG, "onCreateView: " );
                activity.getSupportActionBar().hide();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.w(TAG, "onStop: " );
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if(activity != null) {
            if(activity.getSupportActionBar() != null){
                Log.e(TAG, "onDestroyView: " );
                activity.getSupportActionBar().show();
            }
        }
    }
}