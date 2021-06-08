package com.bridgeLabz.bookstore.Repository;

import android.content.Context;

import com.bridgeLabz.bookstore.Model.UserModel;
import com.bridgeLabz.bookstore.helper.SharedPreference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class UserRepository {

    private Context context;
    private SharedPreference sharedPreference;

    public UserRepository(Context context) {
        this.context = context;
        sharedPreference = new SharedPreference(context);
    }

    public List<UserModel> getUsersList() {
        ObjectMapper mapper = new ObjectMapper();
        List<UserModel> usersList = null;
        try {
            usersList = mapper.readValue(new File(context.getFilesDir(),
                    "users.json"), new TypeReference<List<UserModel>>() {
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public void writeUsersList(List<UserModel> usersList) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String updatedFile = mapper.writeValueAsString(usersList);
            FileOutputStream fos = context.openFileOutput("users.json", Context.MODE_PRIVATE);
            fos.write(updatedFile.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
