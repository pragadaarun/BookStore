package com.bridgeLabz.bookstore.Model;

public class CartModel {

    private int itemQuantities;
    private BookModel book;

    public CartModel() {

    }

    public CartModel(int itemQuantities, BookModel book) {
        this.itemQuantities = itemQuantities;
        this.book = book;
    }

    public int getItemQuantities() {
        return itemQuantities;
    }

    public void setItemQuantities(int itemQuantities) {
        this.itemQuantities = itemQuantities;
    }

    public BookModel getBook() {
        return book;
    }

    public void setBook(BookModel book) {
        this.book = book;
    }
}
