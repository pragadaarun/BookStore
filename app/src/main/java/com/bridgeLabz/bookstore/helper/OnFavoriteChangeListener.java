package com.bridgeLabz.bookstore.helper;

import com.bridgeLabz.bookstore.Model.BookModel;

public interface OnFavoriteChangeListener {
    void onUnchecked(BookModel book, int position);
}
