package com.bridgeLabz.bookstore.helper;

import com.bridgeLabz.bookstore.Model.BookModel;
import com.bridgeLabz.bookstore.Model.CartModel;

public interface CartBookClickListener {

    void onAddItemQuantity(CartModel cart);
    void onMinusItemQuantity(CartModel cart, int position);
    void onBookImageClick(BookModel book, int position);
}
