package com.bridgeLabz.bookstore.Repository;

import android.util.Log;
import com.bridgeLabz.bookstore.Model.BookModel;
import com.bridgeLabz.bookstore.Model.BookResponseModel;
import com.bridgeLabz.bookstore.Model.CartModel;
import com.bridgeLabz.bookstore.Model.CartResponseModel;
import com.bridgeLabz.bookstore.Model.Review;
import com.bridgeLabz.bookstore.Model.UserModel;
import com.bridgeLabz.bookstore.UI.Adapters.ReviewAdapter;
import com.bridgeLabz.bookstore.helper.BookAssetLoader;
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

    private File file;
    private static final String TAG = "BookRepository";
    private UserRepository userRepository;
    private BookAssetLoader bookAssetLoader;
    private ReviewRepository reviewRepository;

    public BookRepository(File file, UserRepository userRepository, BookAssetLoader bookAssetLoader, ReviewRepository reviewRepository) {
        this.file = file;
        this.userRepository = userRepository;
        this.bookAssetLoader = bookAssetLoader;
        this.reviewRepository = reviewRepository;;
    }

    public ArrayList<BookModel> getBookList() {
        ArrayList<BookModel> bookList = new ArrayList<>();
        String data = bookAssetLoader.loadBookJSON();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<BookResponseModel> bookResponseModels = null;
        try {
            bookResponseModels = mapper.readValue(data, new TypeReference<List<BookResponseModel>>() {
            });
            UserModel user = userRepository.getLoggedInUser();
            List<Integer> favoriteBookIds = user.getFavouriteItemsList();
            for (BookResponseModel bookResponseModel : bookResponseModels) {
                BookModel favouriteBook = new BookModel(bookResponseModel);
                float rating = reviewRepository.getAverageRating(bookResponseModel.getBookId());
                favouriteBook.setRating(rating);
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
