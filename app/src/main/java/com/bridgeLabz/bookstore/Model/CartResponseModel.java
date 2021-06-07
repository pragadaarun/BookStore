package com.bridgeLabz.bookstore.Model;

public class CartResponseModel {
    private int bookId;
    private int itemQuantities;

    public CartResponseModel() {

    }

    public CartResponseModel(int bookId, int itemQuantities) {
        this.bookId = bookId;
        this.itemQuantities = itemQuantities;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getItemQuantities() {
        return itemQuantities;
    }

    public void setItemQuantities(int itemQuantities) {
        this.itemQuantities = itemQuantities;
    }

}
