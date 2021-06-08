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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private Context context;
    private static final String TAG = "BookRepository";
    private SharedPreference sharedPreference;
    private UserRepository userRepository;

    public BookRepository(Context context) {
        this.context = context;
        sharedPreference = new SharedPreference(context);
        userRepository =  new UserRepository(context);
    }

    public String loadBookJSON() {
        String json = null;
        try {
            InputStream is = context.getAssets().open("books.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        Log.e(TAG, "loadJSONFromAsset: " + json);
        return json;
    }

    public ArrayList<BookModel> getBookList() {
        ArrayList<BookModel> bookList = new ArrayList<>();
        String data = loadBookJSON();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<BookResponseModel> bookResponseModels = null;
        try {
            bookResponseModels = mapper.readValue(data, new TypeReference<List<BookResponseModel>>() {
            });
            UserModel user = userRepository.getLoggedInUser();
            List<Integer> favoriteBookIds = user.getFavouriteItemsList();
            for (BookResponseModel bookResponseModel : bookResponseModels) {
                BookModel favouriteBook = new BookModel(bookResponseModel);
                favouriteBook.setFavourite(favoriteBookIds.contains(bookResponseModel.getBookId()));
                bookList.add(favouriteBook);
            }
            Log.e(TAG, "getBookList: " + bookList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public ArrayList<BookModel> getFavoriteBooks() {
        ArrayList<BookModel> favoriteBooks = new ArrayList<>();
        for(BookModel book : getBookList()){
            if(book.isFavourite()){
                favoriteBooks.add(book);
            }
        }
        return favoriteBooks;
    }

    public BookModel getBook(int getBookById){

        for(BookModel book : getBookList()){
            if(book.getBookId() == getBookById){
                return book;
            }
        }
        return null;
    }

    public void addBookToCart(int bookID) {

            List<UserModel> userList = userRepository.getUsersList();
            UserModel user = userRepository.getLoggedInUser();
            CartResponseModel cart = new CartResponseModel(bookID, 1);
            List<CartResponseModel> cartItemList = user.getCartItemList();
            cartItemList.add(cart);
            userList.get(user.getUserId()).setCartItemList(cartItemList);
            userRepository.writeUsersList(userList);
    }

}
