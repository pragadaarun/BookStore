package com.bridgeLabz.bookstore.Model;

import java.util.List;

public class UserModel {

    private int userId;
    private String userName;
    private String userEmail;
    private String userPassword;
    private List<Integer> favouriteItemsList;
    private List<CartResponseModel> cartItemList;

    public UserModel() {

    }

    public UserModel(int userId, String userName, String userEmail, String userPassword, List<Integer> favouriteItemsList, List<CartResponseModel> cartItemList) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.favouriteItemsList = favouriteItemsList;
        this.cartItemList = cartItemList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Integer> getFavouriteItemsList() {
        return favouriteItemsList;
    }

    public void setFavouriteItemsList(List<Integer> favouriteItemsList) {
        this.favouriteItemsList = favouriteItemsList;
    }

    public List<CartResponseModel> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartResponseModel> cartItemList) {
        this.cartItemList = cartItemList;
    }
}
