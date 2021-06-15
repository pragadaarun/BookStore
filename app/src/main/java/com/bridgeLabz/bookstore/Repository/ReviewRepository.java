package com.bridgeLabz.bookstore.Repository;

import android.util.Log;

import com.bridgeLabz.bookstore.Model.Review;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepository {

    private File file;
    private static final String TAG = "ReviewRepository";

    public ReviewRepository(File file) {
        this.file = file;
    }

    public List<Review> getReviewFile() {
        List<Review> reviewsList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            reviewsList = mapper.readValue(file, new TypeReference<List<Review>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reviewsList;
    }

    public float getAverageRating(int bookId) {
        float averageRating = 0.0f;
        int totalReviews = 0;
        float rating = 0.0f;
        List<Review> reviewsList = getReviewFile();
        for (int i = 0; i < reviewsList.size(); i++) {
            if (reviewsList.get(i).getBookID() == bookId) {
                rating = rating + reviewsList.get(i).getRating();
                totalReviews++;
            }
        }
        averageRating = rating / totalReviews;
        Log.e(TAG, "getAverageRating: " + rating + "  " + totalReviews);
        return averageRating;
    }
}

