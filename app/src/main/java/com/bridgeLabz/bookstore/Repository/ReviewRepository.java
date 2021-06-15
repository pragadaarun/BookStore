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

    public ReviewRepository(File file) {
        this.file = file;
    }

    public float getAverageRating(int bookId) {
        float averageRating = 0.0f;
        ObjectMapper mapper = new ObjectMapper();
        List<Review> reviewsList = new ArrayList<>();
        List<Review> bookReviews = new ArrayList<>();
        try {
            reviewsList = mapper.readValue(file, new TypeReference<List<Review>>(){} );
            for (int i = 0; i < reviewsList.size(); i++) {
                if (reviewsList.get(i).getBookID() == bookId){
                    float rating = reviewsList.get(i).getRating();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return averageRating;
    }

}

