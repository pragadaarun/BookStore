package com.bridgeLabz.bookstore.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.Repository.CartRepository;
import com.bridgeLabz.bookstore.Repository.ReviewRepository;
import com.bridgeLabz.bookstore.Repository.UserRepository;
import com.bridgeLabz.bookstore.UI.Fragments.BooksListFragment;
import com.bridgeLabz.bookstore.UI.Fragments.CartFragment;
import com.bridgeLabz.bookstore.UI.Fragments.FavouriteFragment;
import com.bridgeLabz.bookstore.UI.Fragments.OrdersFragment;
import com.bridgeLabz.bookstore.UI.Fragments.ProfileFragment;
import com.bridgeLabz.bookstore.helper.AddBadge;
import com.bridgeLabz.bookstore.helper.BookAssetLoader;
import com.bridgeLabz.bookstore.helper.SharedPreference;

import java.io.File;

public class HomeActivity extends AppCompatActivity  implements AddBadge {

    private SharedPreference sharedPreference;
    private BooksListFragment booksListFragment;
    private FavouriteFragment favouriteFragment;
    private CartFragment cartFragment;
    private OrdersFragment ordersFragment;
    private ProfileFragment profileFragment;
    private Fragment fragment;
    public static final String BACK_STACK_TAG_CART_FLOW = "cart_fragment_call";
    private TextView textCartItemCount;
    private int badges;
    private CartRepository cartRepository;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPreference = new SharedPreference(this);
        booksListFragment = new BooksListFragment();
        favouriteFragment = new FavouriteFragment();
        cartFragment = new CartFragment();
        ordersFragment = new OrdersFragment();
        profileFragment = new ProfileFragment();
        File userListFile = new File(getFilesDir(), "users.json");
        File reviewsFile = new File(getFilesDir(), "reviews.json");
        BookAssetLoader bookAssetLoader = new BookAssetLoader(this);
        sharedPreference = new SharedPreference(this);
        userRepository = new UserRepository(userListFile, sharedPreference, bookAssetLoader, new ReviewRepository(reviewsFile));
        cartRepository = new CartRepository(userListFile, userRepository, bookAssetLoader, new ReviewRepository(reviewsFile));
        badges = cartRepository.getCartList().size();

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
        MenuItem profile = menu.findItem(R.id.profile);

        View actionView = cart.getActionView();

        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);
//
        //setupBadge();
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(cart);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.cart: {
                // Do something
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                        cartFragment).addToBackStack(BACK_STACK_TAG_CART_FLOW).commit();

                return true;
            }
            case R.id.sign_out: {
                // Do something
                sharedPreference.setLoggedIN(false);
                startActivity(new Intent(HomeActivity.this, LoginRegisterActivity.class));

                return true;
            }
            case R.id.favourite: {
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                        favouriteFragment).addToBackStack(null).commit();
                return true;
            }
            case R.id.orders: {
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                        ordersFragment).addToBackStack(null).commit();

                return true;
            }
            case R.id.profile: {
                getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                        profileFragment).addToBackStack(null).commit();
                return true;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void fragmentCall(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.home_fragment_container,
                fragment).addToBackStack(null).commit();
    }

    @Override
    public void onAddCart(int count) {

        if (textCartItemCount != null) {
            if (count == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(count, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }

    }
}