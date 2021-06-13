package com.bridgeLabz.bookstore.UI.ViewHolders;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bridgeLabz.bookstore.Model.Review;
import com.bridgeLabz.bookstore.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    TextView reviewUser, reviewDetails;
    RatingBar reviewRating;

    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        reviewUser = itemView.findViewById(R.id.review_user);
        reviewDetails = itemView.findViewById(R.id.review_detail);
        reviewRating = itemView.findViewById(R.id.review_user_rating);
    }
    public void bind(Review review){
        reviewUser.setText(review.getAuthor());
        reviewDetails.setText(review.getReview());
        reviewRating.setRating(review.getRating());
    }
}

