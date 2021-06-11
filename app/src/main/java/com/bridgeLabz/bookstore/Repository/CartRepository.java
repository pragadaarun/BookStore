package com.bridgeLabz.bookstore.Repository;

import android.content.Context;
import android.util.Log;

import com.bridgeLabz.bookstore.Model.BookModel;
import com.bridgeLabz.bookstore.Model.BookResponseModel;
import com.bridgeLabz.bookstore.Model.CartModel;
import com.bridgeLabz.bookstore.Model.CartResponseModel;
import com.bridgeLabz.bookstore.Model.UserModel;
import com.bridgeLabz.bookstore.helper.BookAssetLoader;
import com.bridgeLabz.bookstore.helper.SharedPreference;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartRepository {

    private static final String TAG = "CartRepository";
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private File file;

    public CartRepository(File file , UserRepository userRepository, BookAssetLoader bookAssetLoader) {
        this.file = file;
        bookRepository = new BookRepository(file, userRepository, bookAssetLoader);
        this.userRepository = userRepository;
    }

    public List<CartModel> getCartList() {
        List<CartModel> cartList = new ArrayList<>();
        UserModel user = userRepository.getLoggedInUser();
        List<CartResponseModel> userCartItemList = user.getCartItemList();
        Log.e(TAG, "cartBookIds: " + userCartItemList);

        ArrayList<BookModel> bookList = bookRepository.getBookList();
        ArrayList<Integer> bookIds = getBookIds(bookList);

        for (CartResponseModel cartResponseModel : userCartItemList) {
            int bookIndex = bookIds.indexOf(cartResponseModel.getBookId());
            CartModel cartModel = new CartModel(cartResponseModel.getItemQuantities(), bookList.get(bookIndex));
            cartList.add(cartModel);
        }

        return cartList;
    }

    public void incrementCartItemQuantity(int bookId) {
        List<UserModel> userList = userRepository.getUsersList();
        UserModel user = userRepository.getLoggedInUser();
        List<CartResponseModel> userCartItemList = user.getCartItemList();
        ArrayList<BookModel> bookList = bookRepository.getBookList();
        ArrayList<Integer> bookIds = getBookIds(bookList);
        for (int i =0; i < userCartItemList.size(); i++) {
            CartResponseModel model = userCartItemList.get(i);
            if( model.getBookId() == bookId) {
                int currentQuantity =  model.getItemQuantities();
                model.setItemQuantities(currentQuantity + 1);
            }
        }
        userList.get(user.getUserId()).setCartItemList(userCartItemList);
        userRepository.writeUsersList(userList);
    }

    public void decrementCartItemQuantity(int bookId) {
        List<UserModel> usersList = userRepository.getUsersList();
        UserModel user = userRepository.getLoggedInUser();
        List<CartResponseModel> userCartItemList = user.getCartItemList();
        for (int i =0; i < userCartItemList.size(); i++) {
            CartResponseModel model = userCartItemList.get(i);
            if( model.getBookId() == bookId) {
                int currentQuantity =  model.getItemQuantities();
                if (currentQuantity < 2) {
                    userCartItemList.remove(model);
                } else {
                    model.setItemQuantities(currentQuantity - 1);
                }
            }
        }
        usersList.get(user.getUserId()).setCartItemList(userCartItemList);
        userRepository.writeUsersList(usersList);
    }

    public ArrayList<Integer> getBookIds(ArrayList<BookModel> bookList) {
        ArrayList<Integer> bookIds = new ArrayList<>();
        for (BookModel bookModel : bookList) {
            bookIds.add(bookModel.getBookId());
        }
        return bookIds;
    }

    public float calculateTotalPrice(List<CartModel> cartList) {
        float totalPrice = 0.0f;
        for(CartModel cart : cartList){
            totalPrice = totalPrice + cart.getBook().getPrice() * cart.getItemQuantities();
        }
        return BigDecimal.valueOf(totalPrice).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
    }
}
