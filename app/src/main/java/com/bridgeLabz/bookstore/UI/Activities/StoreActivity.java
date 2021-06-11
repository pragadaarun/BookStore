package com.bridgeLabz.bookstore.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.UI.Fragments.BooksListFragment;
import com.bridgeLabz.bookstore.UI.Fragments.CartFragment;
import com.bridgeLabz.bookstore.UI.Fragments.FavouriteFragment;
import com.bridgeLabz.bookstore.UI.Fragments.OrdersFragment;
import com.bridgeLabz.bookstore.helper.SharedPreference;

public class StoreActivity extends AppCompatActivity {

    private SharedPreference sharedPreference;
    private BooksListFragment booksListFragment;
    private FavouriteFragment favouriteFragment;
    private CartFragment cartFragment;
    private OrdersFragment ordersFragment;
    Fragment fragment;
    public static final String BACK_STACK_TAG_CART_FLOW = "cart_fragment_call";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        sharedPreference = new SharedPreference(this);
        booksListFragment = new BooksListFragment();
        favouriteFragment = new FavouriteFragment();
        cartFragment = new CartFragment();
        ordersFragment = new OrdersFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.home_fragment_container,
                    booksListFragment).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.store_menu, menu);
        MenuItem logout = menu.findItem(R.id.sign_out);
        MenuItem favourite = menu.findItem(R.id.favourite);
        MenuItem orders = menu.findItem(R.id.orders);
        MenuItem cart = menu.findItem(R.id.cart);
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                sharedPreference.setLoggedIN(false);
                startActivity(new Intent(StoreActivity.this, LoginRegisterActivity.class));
                return false;
            }
        });

        favourite.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                fragment = favouriteFragment;
                fragmentCall(fragment);
                return false;
            }
        });

        cart.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                fragment = cartFragment;
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                        fragment).addToBackStack(BACK_STACK_TAG_CART_FLOW).commit();
                return false;
            }
        });

        orders.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                fragment = ordersFragment;
                fragmentCall(fragment);
                return false;
            }
        });
        return true;
    }

    private void fragmentCall(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                fragment).addToBackStack(null).commit();
    }
}