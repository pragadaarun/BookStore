package com.bridgeLabz.bookstore.UI.ViewHolders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bridgeLabz.bookstore.Model.CartModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.UI.Fragments.ReviewFragment;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class OrderDetailViewHolder extends RecyclerView.ViewHolder {
    TextView bookTitle,bookAuthor,bookPrice,bookRating;
    ImageView bookImage;
    Button reviewButton;

    public OrderDetailViewHolder(@NonNull View itemView) {
        super(itemView);
        bookTitle = itemView.findViewById(R.id.item_order_book_title);
        bookAuthor = itemView.findViewById(R.id.item_order_book_author);
        bookImage = itemView.findViewById(R.id.item_order_book_image);
        bookPrice = itemView.findViewById(R.id.item_order_book_price);
        bookRating = itemView.findViewById(R.id.item_order_book_rating);
        reviewButton = itemView.findViewById(R.id.item_order_review_button);
    }

    public void bind(CartModel order) {
        bookTitle.setText(order.getBook().getTitle());
        bookAuthor.setText(order.getBook().getAuthor());
        String url = order.getBook().getBookImage();
        Glide.with(itemView.getContext()).load(url).into(bookImage);
        bookPrice.setText(String.valueOf(order.getBook().getPrice()));
        reviewButton.setOnClickListener(v -> {
            ReviewFragment reviewFragment = new ReviewFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("BookReviewID", order.getBook().getBookId());
            reviewFragment.setArguments(bundle);
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.home_fragment_container, reviewFragment)
                    .addToBackStack(null).commit();

        });
    }
}
