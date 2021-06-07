package com.bridgeLabz.bookstore.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.helper.SharedPreference;

public class SplashActivity extends AppCompatActivity {


    private boolean isLoggedIn;
    SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        sharedPreference = new SharedPreference(this);


        new Handler().postDelayed(() -> {

            isLoggedIn = sharedPreference.getLoggedIN();
            Intent intent;
            if (isLoggedIn){
                intent = new Intent(SplashActivity.this, StoreActivity.class);
            }else{
                intent = new Intent(SplashActivity.this, LoginRegisterActivity.class);
            }
            startActivity(intent);
            finish();
        },4000);

    }
}