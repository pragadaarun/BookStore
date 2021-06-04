package com.bridgeLabz.bookstore.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {

    private static int presentUserId;
    private static int registeredUsersCount;
    private Context context;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String IS_LOGGED_IN = "Logged_In";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SharedPreference(Context context) {
        this.context = context;
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
        return presentUserId;
    }

    public void setPresentUserId(int presentUserId) {
        this.presentUserId = presentUserId;
    }

    public int getRegisteredUsersCount() {
        return registeredUsersCount;
    }

    public void setRegisteredUsersCount(int registeredUsersCount) {
        this.registeredUsersCount = registeredUsersCount;
    }
}
