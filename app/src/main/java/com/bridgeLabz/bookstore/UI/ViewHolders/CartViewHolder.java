package com.bridgeLabz.bookstore.UI.ViewHolders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bridgeLabz.bookstore.Model.CartModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.helper.CartBookClickListener;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartViewHolder extends RecyclerView.ViewHolder {

    public TextView cartBookTitle, cartBookAuthor, cartBookPrice, itemCountDisplay;
    public ImageView cartBookImage;
    public ImageButton addImageButton, removeImageButton;
    public int itemCount = 1;
    public float bookPrice = 1;
    private CartBookClickListener cartBookClickListener;
    private int cartPosition = 0;
    private CartModel cart;

    public CartViewHolder(@NonNull View itemView, CartBookClickListener cartBookClickListener) {
        super(itemView);
        cartBookTitle = itemView.findViewById(R.id.cart_book_title);
        cartBookAuthor = itemView.findViewById(R.id.cart_book_author);
        cartBookImage = itemView.findViewById(R.id.cart_book_image);
        cartBookPrice = itemView.findViewById(R.id.cart_book_price);
        itemCountDisplay = itemView.findViewById(R.id.book_cart_value);
        addImageButton = itemView.findViewById(R.id.book_cart_add);
        removeImageButton = itemView.findViewById(R.id.book_cart_minus);
        this.cartBookClickListener = cartBookClickListener;
    }

    public void bind(CartModel cart, int cartPosition) {
        this.cart = cart;
        this.cartPosition = cartPosition;
        this.itemCount = cart.getItemQuantities();
        cartBookTitle.setText(cart.getBook().getTitle());
        cartBookAuthor.setText(cart.getBook().getAuthor());
        bookPrice = displayPrices(itemCount);
        cartBookPrice.setText(String.valueOf(bookPrice));
        itemCountDisplay.setText(String.valueOf(itemCount));
        String imageUri = cart.getBook().getBookImage();
        Glide.with(itemView.getContext())
                .load(imageUri)
                .into(cartBookImage);
        cartBookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartBookClickListener.onBookImageClick(cart.getBook(), cartPosition);
            }
        });

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCount = itemCount + 1;
                displayPrices(itemCount);
                cartBookPrice.setText(String.valueOf(bookPrice));
                cartBookClickListener.onAddItemQuantity(cart);
            }
        });

        removeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCount = itemCount - 1;
                displayPrices(itemCount);
                cartBookPrice.setText(String.valueOf(bookPrice));
                cartBookClickListener.onMinusItemQuantity(cart, cartPosition);
            }
        });
    }

    private float displayPrices(int itemCount) {
        itemCountDisplay.setText(String.valueOf(itemCount));
        bookPrice = cart.getBook().getPrice() * itemCount;
        return bookPrice;
    }
}
