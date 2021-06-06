package com.bridgeLabz.bookstore.Model;

public class CartModel {
    private int bookId;
    private int itemQuantities;

    public CartModel() {

    }

    public CartModel(int bookId, int itemQuantities) {
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
