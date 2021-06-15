package com.bridgeLabz.bookstore.Model;

public class Review {

    private String userName;
    private int userID;
    private long reviewID;
    private int bookID;
    private float rating;
    private String review;

    public Review(String userName, int userID, long reviewID, int bookID, float rating, String review) {
        this.userName = userName;
        this.userID = userID;
        this.reviewID = reviewID;
        this.bookID = bookID;
        this.rating = rating;
        this.review = review;
    }

    public Review(){}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public long getReviewID() {
        return reviewID;
    }

    public void setReviewID(long reviewID) {
        this.reviewID = reviewID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}