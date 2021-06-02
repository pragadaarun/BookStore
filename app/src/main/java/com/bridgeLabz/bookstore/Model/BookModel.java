package com.bridgeLabz.bookstore.Model;

public class BookModel {

    private String title;
    private String author;
    private String description;
    private float price;


    public BookModel(String title, String author, String description, float price) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.price = price;
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
}
