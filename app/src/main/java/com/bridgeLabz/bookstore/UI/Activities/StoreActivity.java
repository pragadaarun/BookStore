package com.bridgeLabz.bookstore.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.UI.Fragments.BooksListFragment;
import com.bridgeLabz.bookstore.helper.SharedPreference;

public class StoreActivity extends AppCompatActivity {

    SharedPreference sharedPreference;
    BooksListFragment booksListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        sharedPreference = new SharedPreference(this);
        booksListFragment = new BooksListFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                    booksListFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.store_menu, menu);
        MenuItem logout = menu.findItem(R.id.sign_out);
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                sharedPreference.setLoggedIN(false);
                startActivity(new Intent(StoreActivity.this, LoginRegisterActivity.class));
                return false;
            }
        });

        return true;
    }
}