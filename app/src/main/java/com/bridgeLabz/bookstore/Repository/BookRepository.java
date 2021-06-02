package com.bridgeLabz.bookstore.Repository;

import com.bridgeLabz.bookstore.Model.BookModel;

import java.util.ArrayList;

public class BookRepository {

    private ArrayList<BookModel> books = new ArrayList<>();

    BookModel book1 = new BookModel("Wings Of Fire",
            "APJ Abdul Kalam","Every common man who by his sheer grit and hard work " +
            "achieves success should share his story with the rest for they may find inspiration and strength to go on," +
            " in his story. The 'Wings of Fire' is one such autobiography by visionary scientist Dr. APJ Abdul Kalam, " +
            "who from very humble beginnings rose to be the President of India. The book is full of insights, personal " +
            "moments and life experiences of Dr. Kalam. It gives us an understanding on his journey of success.",
            (float) 123.90);



    public ArrayList<BookModel> onAddBooks() {

        books.add(book1);

        return books;
    }


}
