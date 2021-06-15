package com.bridgeLabz.bookstore.Model;

import java.util.List;

public class BookModel {

    private int bookId;
    private String title;
    private String author;
    private String description;
    private String bookImage;
    private float price;
    private boolean isCarted;
    private boolean isFavourite;
    private float bookMRP;
    private float discount;
    private float rating;

    public BookModel() {

    }

    public BookModel(int bookId, String title, String author, String description,
                     String bookImage, float price, boolean isCarted, boolean isFavourite,
                     float bookMRP, float discount, float rating) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.bookImage = bookImage;
        this.isCarted = isCarted;
        this.isFavourite = isFavourite;
        this.bookMRP = bookMRP;
        this.discount = discount;
        this.rating = rating;

    }

    public BookModel(BookResponseModel bookResponseModel) {
        this.bookId = bookResponseModel.getBookId();
        this.title = bookResponseModel.getTitle();
        this.author = bookResponseModel.getAuthor();
        this.description = bookResponseModel.getDescription();
        this.price = bookResponseModel.getPrice();
        this.bookImage = bookResponseModel.getBookImage();
        this.bookMRP = bookResponseModel.getBookMRP();
        this.discount = bookResponseModel.getDiscount();
        this.rating = bookResponseModel.getRating();
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

    public boolean isCarted() {
        return isCarted;
    }

    public void setCarted(boolean carted) {
        isCarted = carted;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
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
