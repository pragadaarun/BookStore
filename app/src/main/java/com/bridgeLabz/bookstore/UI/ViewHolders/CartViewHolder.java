package com.bridgeLabz.bookstore.UI.ViewHolders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bridgeLabz.bookstore.Model.CartModel;
import com.bridgeLabz.bookstore.R;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartViewHolder extends RecyclerView.ViewHolder {

    public TextView cartBookTitle, cartBookAuthor, cartBookPrice, itemCountDisplay;
    public ImageView cartBookImage;
    public ImageButton addImageButton, removeImageButton;
    public int itemCount = 1;
    public float bookPrice = 1;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        cartBookTitle = itemView.findViewById(R.id.cart_book_title);
        cartBookAuthor = itemView.findViewById(R.id.cart_book_author);
        cartBookImage = itemView.findViewById(R.id.cart_book_image);
        cartBookPrice = itemView.findViewById(R.id.cart_book_price);
        itemCountDisplay = itemView.findViewById(R.id.book_cart_value);
        addImageButton = itemView.findViewById(R.id.book_cart_add);
        removeImageButton = itemView.findViewById(R.id.book_cart_minus);
    }

    public void bind(CartModel cart) {
        this.itemCount = cart.getItemQuantities();
        cartBookTitle.setText(cart.getBook().getTitle());
        cartBookAuthor.setText(cart.getBook().getAuthor());
        cartBookPrice.setText(String.valueOf(cart.getBook().getPrice()));
        itemCountDisplay.setText(String.valueOf(cart.getItemQuantities()));
        String imageUri = cart.getBook().getBookImage();
        Glide.with(itemView.getContext())
                .load(imageUri)
                .into(cartBookImage);
        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCount = itemCount + 1;
                itemCountDisplay.setText(String.valueOf(itemCount));
                bookPrice = cart.getBook().getPrice() * itemCount;
                cartBookPrice.setText(String.valueOf(bookPrice));
            }
        });

//        removeImageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(itemCount > 0) {
//                    itemCount = itemCount - 1;
//                    itemCountDisplay.setText(String.valueOf(itemCount));
//                    bookPrice = cart.getBook().getPrice() * itemCount;
//                    cartBookPrice.setText(String.valueOf(bookPrice));
//                } else {
//
//                }
//            }
//        });
    }
}
