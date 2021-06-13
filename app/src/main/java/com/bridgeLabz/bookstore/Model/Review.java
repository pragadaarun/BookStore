package com.bridgeLabz.bookstore.Model;

public class Review {

    private String author;
    private String review;
    private float rating;

    public Review() {

    }

    public Review(String author, String review, float rating) {
        this.author = author;
        this.review = review;
        this.rating = rating;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}

