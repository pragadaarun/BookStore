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
import com.bridgeLabz.bookstore.UI.Fragments.CartFragment;
import com.bridgeLabz.bookstore.UI.Fragments.FavouriteFragment;
import com.bridgeLabz.bookstore.helper.SharedPreference;

public class StoreActivity extends AppCompatActivity {

    private SharedPreference sharedPreference;
    private BooksListFragment booksListFragment;
    private FavouriteFragment favouriteFragment;
    private CartFragment cartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        sharedPreference = new SharedPreference(this);
        booksListFragment = new BooksListFragment();
        favouriteFragment = new FavouriteFragment();
        cartFragment = new CartFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                    booksListFragment).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.store_menu, menu);
        MenuItem logout = menu.findItem(R.id.sign_out);
        MenuItem wishList = menu.findItem(R.id.favourite);
        MenuItem cartList = menu.findItem(R.id.cart);
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                sharedPreference.setLoggedIN(false);
                startActivity(new Intent(StoreActivity.this, LoginRegisterActivity.class));
                return false;
            }
        });

        wishList.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                        favouriteFragment).addToBackStack(null).commit();
                return false;
            }
        });

        cartList.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                        cartFragment).addToBackStack(null).commit();
                return false;
            }
        });
        return true;
    }
}