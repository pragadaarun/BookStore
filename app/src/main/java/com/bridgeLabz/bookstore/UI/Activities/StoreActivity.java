package com.bridgeLabz.bookstore.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.UI.Fragments.BooksListFragment;
import com.bridgeLabz.bookstore.UI.Fragments.FavouriteFragment;
import com.bridgeLabz.bookstore.helper.SharedPreference;

public class StoreActivity extends AppCompatActivity {

    SharedPreference sharedPreference;
    BooksListFragment booksListFragment;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        sharedPreference = new SharedPreference(this);
        booksListFragment = new BooksListFragment();

        toolbar = (Toolbar) findViewById(R.id.store_toolbar);
        toolbar.setTitle("Book Store");
        toolbar.inflateMenu(R.menu.store_menu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId()==R.id.sign_out)
                {
                    sharedPreference.setLoggedIN(false);
                    startActivity(new Intent(StoreActivity.this, LoginRegisterActivity.class));
                    return false;                }
                else if(item.getItemId() == R.id.favourite){

                    getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                           new FavouriteFragment()).addToBackStack(null).commit();
                }

                return false;
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                    booksListFragment).commit();
        }

    }

}