package com.bridgeLabz.bookstore.Model;

public class BookResponseModel {

    private int bookId;
    private String title;
    private String author;
    private String description;
    private String bookImage;
    private float price;

    public BookResponseModel() {

    }

    public BookResponseModel(int bookId, String title, String author, String description,
                     String bookImage, float price) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
        this.bookImage = bookImage;
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

}
