package com.bridgeLabz.bookstore.Model;

import java.util.List;

public class UserModel {

    private int userId;
    private String userName;
    private String userEmail;
    private String userPassword;
    private List<Integer> favouriteItemsList;
    private List<CartResponseModel> cartItemList;
    private List<AddressModel> addressList;
    private List<OrderModel> ordersList;
    private String userImage;
    private int userAccessCount;
    private boolean isUserSubscription;

    public UserModel() {

    }

    public UserModel(int userId, String userName, String userEmail, String userPassword,
                     List<Integer> favouriteItemsList, List<CartResponseModel> cartItemList,
                     List<AddressModel> addressList, List<OrderModel> ordersList, String userImage,
                     int userAccessCount, boolean isUserSubscription) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.favouriteItemsList = favouriteItemsList;
        this.cartItemList = cartItemList;
        this.addressList = addressList;
        this.ordersList = ordersList;
        this.userImage = userImage;
        this.userAccessCount = userAccessCount;
        this.isUserSubscription = isUserSubscription;
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

    public List<AddressModel> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<AddressModel> addressList) {
        this.addressList = addressList;
    }

    public List<OrderModel> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<OrderModel> ordersList) {
        this.ordersList = ordersList;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public int getUserAccessCount() {
        return userAccessCount;
    }

    public void setUserAccessCount(int userAccessCount) {
        this.userAccessCount = userAccessCount;
    }

    public boolean isUserSubscription() {
        return isUserSubscription;
    }

    public void setUserSubscription(boolean isUserSubscription) {
        this.isUserSubscription = isUserSubscription;
    }
}
