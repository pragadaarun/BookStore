package com.bridgeLabz.bookstore.Repository;

import android.content.Context;
import android.util.Log;

import com.bridgeLabz.bookstore.Model.BookModel;
import com.bridgeLabz.bookstore.Model.BookResponseModel;
import com.bridgeLabz.bookstore.Model.CartModel;
import com.bridgeLabz.bookstore.Model.CartResponseModel;
import com.bridgeLabz.bookstore.Model.UserModel;
import com.bridgeLabz.bookstore.helper.SharedPreference;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartRepository {

    private static final String TAG = "CartRepository";
    private Context context;
    private SharedPreference sharedPreference;
    private BookRepository bookRepository;
    private UserRepository userRepository;

    public CartRepository(Context context) {
        this.context = context;
        sharedPreference = new SharedPreference(context);
        bookRepository = new BookRepository(context);
        userRepository = new UserRepository(context);
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

    public void updateCart(CartModel cart) {
        List<UserModel> userList = userRepository.getUsersList();
        UserModel user = userRepository.getLoggedInUser();
        List<CartResponseModel> userCartItemList = user.getCartItemList();
        ArrayList<BookModel> bookList = bookRepository.getBookList();
        ArrayList<Integer> bookIds = getBookIds(bookList);
        for (CartResponseModel cartResponseModel : userCartItemList) {
            int bookIndex = bookIds.indexOf(cartResponseModel.getBookId());
            if (bookIndex == cart.getBook().getBookId()) {
                cartResponseModel.setItemQuantities(cart.getItemQuantities());
                userCartItemList.add(cartResponseModel);
                break;
            }
        }
        userList.get(user.getUserId()).setCartItemList(userCartItemList);
        userRepository.writeUsersList(userList);
    }

    public void removeCart(CartModel cart) {

        List<UserModel> usersList = userRepository.getUsersList();
        UserModel user = userRepository.getLoggedInUser();
        List<CartResponseModel> userCartItemList = user.getCartItemList();
        ArrayList<BookModel> bookList = bookRepository.getBookList();
        ArrayList<Integer> bookIds = getBookIds(bookList);
        for (CartResponseModel cartResponseModel : userCartItemList) {
            int bookIndex = bookIds.indexOf(cartResponseModel.getBookId());
            if (bookIndex == cart.getBook().getBookId()) {
                userCartItemList.remove(cartResponseModel);
                break;
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
}
