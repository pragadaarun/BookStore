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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartRepository {

    private static final String TAG = "CartRepository";
    private Context context;
    private SharedPreference sharedPreference;
    private BookRepository bookRepository;

    public CartRepository(Context context) {
        this.context = context;
        sharedPreference = new SharedPreference(context);
        bookRepository = new BookRepository(context);
    }

    public List<CartModel> getCartList() {
        List<CartModel> cartList = new ArrayList<>();
        UserModel user = bookRepository.getLoggedInUser();
        List<CartResponseModel> userCartItemList = user.getCartItemList();
        Log.e(TAG, "cartBookIds: " + userCartItemList);

        ArrayList<BookModel> bookList = bookRepository.getBookList();
        ArrayList<Integer> bookIds = new ArrayList<>();
        for (BookModel bookModel : bookList) {
            bookIds.add(bookModel.getBookId());
        }

        for (CartResponseModel cartResponseModel : userCartItemList) {
            int bookIndex = bookIds.indexOf(cartResponseModel.getBookId());
            CartModel cartModel = new CartModel(cartResponseModel.getItemQuantities(), bookList.get(bookIndex));
            cartList.add(cartModel);
        }

        return cartList;
    }

    public ArrayList<BookModel> getUserCartItemList() {
        ArrayList<BookModel> bookList = new ArrayList<>();
        String data = bookRepository.loadBookJSON();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<BookResponseModel> bookResponseModels = null;
        try {
            bookResponseModels = mapper.readValue(data, new TypeReference<List<BookResponseModel>>() {
            });
            UserModel user = bookRepository.getLoggedInUser();
            List<CartResponseModel> cartBookIds = user.getCartItemList();
            Log.e(TAG, "cartBookIds: " + cartBookIds);
            for (BookResponseModel bookResponseModel : bookResponseModels) {
                BookModel cartBook = new BookModel(bookResponseModel);
                cartBook.setCarted(cartBookIds.contains(bookResponseModel.getBookId()));
                Log.e(TAG, "cartBook: " + cartBook);
                bookList.add(cartBook);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public ArrayList<BookModel> getCartItemBooks() {
        ArrayList<BookModel> cartItemBooks = new ArrayList<>();
        for (BookModel book : getUserCartItemList()) {
            if (book.isCarted()) {
                cartItemBooks.add(book);
            }
        }
        return cartItemBooks;
    }

}
