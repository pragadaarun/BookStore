package com.bridgeLabz.bookstore.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.UI.Fragments.Authentication.LoginFragment;

public class LoginRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        getSupportFragmentManager().beginTransaction().replace(R.id.sign_in_container, new LoginFragment()).commit();
    }
}