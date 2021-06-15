package com.bridgeLabz.bookstore.Model;

import java.util.List;

public class BookResponseModel {

    private int bookId;
    private String title;
    private String author;
    private String description;
    private String bookImage;
    private float price;
    private float bookMRP;
    private float discount;
    private float rating;

    public BookResponseModel() {

    }

    public BookResponseModel(int bookId, String title, String author, String description,
                     String bookImage, float price, float bookMRP, float discount, float rating) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.bookImage = bookImage;
        this.bookMRP = bookMRP;
        this.discount = discount;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public float getBookMRP() {
        return bookMRP;
    }

    public void setBookMRP(float bookMRP) {
        this.bookMRP = bookMRP;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
