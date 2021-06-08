package com.bridgeLabz.bookstore.helper;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class BookAssetLoader {

    private Context context;

    public BookAssetLoader(Context context) {
        this.context = context;
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
        return json;
    }

}
