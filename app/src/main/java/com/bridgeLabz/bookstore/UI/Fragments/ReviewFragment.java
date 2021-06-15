package com.bridgeLabz.bookstore.UI.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.bridgeLabz.bookstore.Model.Review;
import com.bridgeLabz.bookstore.Model.UserModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.helper.SharedPreference;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReviewFragment extends Fragment {

    private static final String TAG = "ReviewFragment";
    EditText userReviewTextView;
    RatingBar ratingBar;
    Button submitReview;
    SharedPreference sharedPreference;

    @Override
    public void onStart() {
        super.onStart();
        Log.w(TAG, "onStart: " );
        showToolBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        userReviewTextView = view.findViewById(R.id.review_user_review_text);
        ratingBar = view.findViewById(R.id.user_review_rating_bar);
        submitReview = view.findViewById(R.id.user_review_submit);
        sharedPreference = new SharedPreference(this.getContext());
        submitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getArguments() != null;
                int BookId = getArguments().getInt("BookReviewID");

                String userName = null;
                String jsonStr = null;
                ObjectMapper mapper = new ObjectMapper();
                try {
                    List<UserModel> userList1 = mapper.readValue(new File(getContext().getFilesDir(),
                            "users.json"), new TypeReference<List<UserModel>>() {
                    });
                    userName = userList1.get(sharedPreference.getPresentUserId()).getUserName();
                } catch (IOException jsonParseException) {
                    jsonParseException.printStackTrace();
                }

                try {
                    String userReview = userReviewTextView.getText().toString();
                    float userRating = ratingBar.getRating();
                    long reviewID = System.currentTimeMillis();
                    File file = new File(getContext().getFilesDir(), "reviews.json");
                    List<Review> reviewList = new ArrayList<Review>();
                    int userID = sharedPreference.getPresentUserId();
                    Review review = new Review(userName, userID, reviewID, BookId, userRating, userReview);
                    reviewList.add(review);
                    ArrayList<Review> userList1 = mapper.readValue(new File(getContext().getFilesDir(),
                            "reviews.json"), new TypeReference<List<Review>>() {
                    });

                    List<Review> joined = new ArrayList<Review>();

                    joined.addAll(reviewList);
                    joined.addAll(userList1);

                    jsonStr = mapper.writeValueAsString(joined);

                    FileOutputStream fos = getContext().openFileOutput("reviews.json", Context.MODE_PRIVATE);
                    fos.write(jsonStr.getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getParentFragmentManager().popBackStack();
            }
        });
        onBackPressed(view);
        return view;
    }

    private void showToolBar() {
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if(activity != null) {
            if(activity.getSupportActionBar() != null){
                Log.e(TAG, "omCreateView: " + "Review Fragment" );
                activity.getSupportActionBar().hide();
            }
        }
    }

    private void onBackPressed(View view) {

        Toolbar favoriteToolbar = (Toolbar) view.findViewById(R.id.review_toolbar);
        favoriteToolbar.setTitle("Review Book");
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
    public void onStop() {
        super.onStop();
        Log.w(TAG, "onStop: " );
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if(activity != null) {
            if(activity.getSupportActionBar() != null){
                activity.getSupportActionBar().show();
                Log.e(TAG, "onDestroyView: " + "Review Fragment" );
            }
        }
    }
}