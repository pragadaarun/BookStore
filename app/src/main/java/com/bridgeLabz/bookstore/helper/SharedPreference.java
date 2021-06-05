package com.bridgeLabz.bookstore.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String IS_LOGGED_IN = "Logged_In";
    public static final String LOGGED_IN_USER_ID_KEY = "User_ID";
    public static final String REGISTERED_USERS = "User_Register";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SharedPreference(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFS,Context.MODE_PRIVATE);
    }

    public void setLoggedIN(boolean value){
        editor  = sharedPreferences.edit();
        editor.putBoolean(IS_LOGGED_IN,value);
        editor.apply();
    }

    public boolean getLoggedIN(){
        return sharedPreferences.getBoolean(IS_LOGGED_IN,false);
    }

    public int getPresentUserId() {
        return sharedPreferences.getInt(LOGGED_IN_USER_ID_KEY,-1);
    }

    public void setPresentUserId(int presentUserId) {
        editor  = sharedPreferences.edit();
        editor.putInt(LOGGED_IN_USER_ID_KEY, presentUserId);
        editor.apply();
    }

    public int getRegisteredUsersCount() {
        return sharedPreferences.getInt(REGISTERED_USERS,-1);
    }

    public void setRegisteredUsersCount(int registeredUsersCount) {
        editor  = sharedPreferences.edit();
        editor.putInt(REGISTERED_USERS, registeredUsersCount);
        editor.apply();    }
}
