package com.bridgeLabz.bookstore.Repository;

import com.bridgeLabz.bookstore.Model.CartModel;
import com.bridgeLabz.bookstore.Model.CartResponseModel;
import com.bridgeLabz.bookstore.Model.OrderModel;
import com.bridgeLabz.bookstore.Model.UserModel;
import com.bridgeLabz.bookstore.helper.BookAssetLoader;
import com.bridgeLabz.bookstore.helper.SharedPreference;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private SharedPreference sharedPreference;
    private CartRepository cartRepository;
    private File userListFile;

    public UserRepository(File file, SharedPreference sharedPreference, BookAssetLoader bookAssetLoader) {
        this.userListFile = file;
        this.sharedPreference = sharedPreference;
        cartRepository = new CartRepository(file, this, bookAssetLoader);
    }

    public List<UserModel> getUsersList() {
        ObjectMapper mapper = new ObjectMapper();
        List<UserModel> usersList = null;
        try {
            usersList = mapper.readValue(userListFile, new TypeReference<List<UserModel>>() {
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public void writeUsersList(List<UserModel> usersList) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(userListFile, usersList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UserModel getLoggedInUser() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<UserModel> userList = mapper.readValue(userListFile, new TypeReference<List<UserModel>>() {
            });
            for (UserModel user : userList) {
                if (user.getUserId() == sharedPreference.getPresentUserId()) {
                    return user;
                }
            }
        } catch (IOException jsonException) {
            jsonException.printStackTrace();
        }
        return null;
    }

    public void addOrdersList(long orderNo) {
        List<UserModel> usersList = getUsersList();
        UserModel user = getLoggedInUser();
        List<CartResponseModel> userCartItemList = user.getCartItemList();
        List<OrderModel> userOrdersList = user.getOrdersList();
        List<CartModel> cartList = cartRepository.getCartList();
        float cartTotalPrice = cartRepository.calculateTotalPrice(cartList);
        //Creating Order list
        OrderModel order = new OrderModel(orderNo, cartTotalPrice, userCartItemList);
        userOrdersList.add(order);
        usersList.get(user.getUserId()).setOrdersList(userOrdersList);
        //Empty the Cart Items
        List<CartResponseModel> newCartItemList = new ArrayList<>();
        usersList.get(user.getUserId()).setCartItemList(newCartItemList);
        writeUsersList(usersList);
    }

    public List<OrderModel> getAllOrders() {

        List<UserModel> usersList = getUsersList();
        return usersList.get(sharedPreference.getPresentUserId()).getOrdersList();
    }

}
