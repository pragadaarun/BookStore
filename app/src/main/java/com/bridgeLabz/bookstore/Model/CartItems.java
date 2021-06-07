package com.bridgeLabz.bookstore.Model;

public class CartItems {

    private int cartBookId;
    private String cartBookTitle;
    private String cartBookAuthor;
    private String cartBookPrice;

    public CartItems(int cartBookId, String cartBookTitle, String cartBookAuthor, String cartBookPrice) {
        this.cartBookId = cartBookId;
        this.cartBookTitle = cartBookTitle;
        this.cartBookAuthor = cartBookAuthor;
        this.cartBookPrice = cartBookPrice;
    }

    public int getCartBookId() {
        return cartBookId;
    }

    public void setCartBookId(int cartBookId) {
        this.cartBookId = cartBookId;
    }

    public String getCartBookTitle() {
        return cartBookTitle;
    }

    public void setCartBookTitle(String cartBookTitle) {
        this.cartBookTitle = cartBookTitle;
    }

    public String getCartBookAuthor() {
        return cartBookAuthor;
    }

    public void setCartBookAuthor(String cartBookAuthor) {
        this.cartBookAuthor = cartBookAuthor;
    }

    public String getCartBookPrice() {
        return cartBookPrice;
    }

    public void setCartBookPrice(String cartBookPrice) {
        this.cartBookPrice = cartBookPrice;
    }
}
