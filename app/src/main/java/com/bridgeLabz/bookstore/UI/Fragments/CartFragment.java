package com.bridgeLabz.bookstore.UI.Fragments;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bridgeLabz.bookstore.Model.AddressModel;
import com.bridgeLabz.bookstore.Model.BookModel;
import com.bridgeLabz.bookstore.Model.CartModel;
import com.bridgeLabz.bookstore.Model.UserModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.Repository.CartRepository;
import com.bridgeLabz.bookstore.Repository.ReviewRepository;
import com.bridgeLabz.bookstore.Repository.UserRepository;
import com.bridgeLabz.bookstore.UI.Adapters.CartAdapter;
import com.bridgeLabz.bookstore.helper.AddBadge;
import com.bridgeLabz.bookstore.helper.BookAssetLoader;
import com.bridgeLabz.bookstore.helper.CartBookClickListener;
import com.bridgeLabz.bookstore.helper.SharedPreference;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class CartFragment extends Fragment {

    private static final String TAG = "CartFragment";
    private CartRepository cartRepository;
    private UserRepository userRepository;
    private int spanCount;
    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private Button cartBuyButton;
    private TextView cartTotalPrice;
    private SharedPreference sharedPreference;
    private float totalPrice;
    private Fragment fragment;

    @Override
    public void onStart() {
        super.onStart();
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if(activity != null) {
            if(activity.getSupportActionBar() != null){
                activity.getSupportActionBar().hide();
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        File userListFile = new File(getContext().getFilesDir(), "users.json");
        File reviewsFile = new File(getContext().getFilesDir(), "reviews.json");
        BookAssetLoader bookAssetLoader = new BookAssetLoader(getContext());
        sharedPreference = new SharedPreference(getContext());
        userRepository = new UserRepository(userListFile, sharedPreference, bookAssetLoader, new ReviewRepository(reviewsFile));
        cartRepository = new CartRepository(userListFile, userRepository, bookAssetLoader, new ReviewRepository(reviewsFile));
        List<CartModel> cartItemBooks = cartRepository.getCartList();
        cartBuyButton = view.findViewById(R.id.cart_buy_button);
        cartTotalPrice = view.findViewById(R.id.cart_total_price);
        totalPrice = cartRepository.calculateTotalPrice(cartItemBooks);
        cartTotalPrice.setText(String.valueOf(totalPrice));
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            spanCount = 2;
        } else {
            // In portrait
            spanCount = 1;
        }
//        spanCount = (orientation == Configuration.ORIENTATION_LANDSCAPE)? 2 : 1;
        final RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        cartRecyclerView = view.findViewById(R.id.cart_RecyclerView);
        cartRecyclerView.setLayoutManager(layoutManager);
        cartRecyclerView.setHasFixedSize(true);
        cartAdapter = new CartAdapter(cartItemBooks, new CartBookClickListener() {
            @Override
            public void onAddItemQuantity(CartModel cart) {
                cartRepository.incrementCartItemQuantity(cart.getBook().getBookId());
                List<CartModel> updatedCart = cartRepository.getCartList();
                totalPrice = cartRepository.calculateTotalPrice(updatedCart);
                cartTotalPrice.setText(String.valueOf(totalPrice));
            }

            @Override
            public void onMinusItemQuantity(CartModel cart, int position) {
                if(cart.getItemQuantities() < 2){
                    cartAdapter.removeAt(position);
                }
                cartRepository.decrementCartItemQuantity(cart.getBook().getBookId());
                List<CartModel> updatedCart = cartRepository.getCartList();
                totalPrice = cartRepository.calculateTotalPrice(updatedCart);
                cartTotalPrice.setText(String.valueOf(totalPrice));
                cartAdapter.setCartBooksList(updatedCart);
                if(updatedCart.size() == 0){
                    cartAdapter.notifyDataSetChanged();
                    cartBuyButton.setEnabled(false);
                    try{
                        ((AddBadge) requireActivity()).onAddCart(updatedCart.size());
                    }catch (ClassCastException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onBookImageClick(BookModel book, int position) {
                int bookId = cartAdapter.getItem(position).getBookId();
                fragment = new BookFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("BookId", bookId);
                fragment.setArguments(bundle);
                fragmentCall();
            }
        });
        cartRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        onBackPressed(view);
        buyBooks();

        return view;
    }

    private void buyBooks() {
        if (cartRepository.getCartList().size() == 0) {
            cartBuyButton.setEnabled(false);
        } else {
            cartBuyButton.setEnabled(true);
            cartBuyButton.setOnClickListener(v -> {
                fragment = new AddressFragment();
                fragmentCall();
            });
        }
    }

    private void onBackPressed(View view) {

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.cart_toolbar);
        toolbar.setTitle("Cart List");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle any click event
                getParentFragmentManager().popBackStack();

            }
        });
    }

    public void fragmentCall() {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.home_fragment_container, fragment)
                .addToBackStack(null).commit();
    }

    @Override
    public void onStop() {
        super.onStop();
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if(activity != null) {
            if(activity.getSupportActionBar() != null){
                activity.getSupportActionBar().show();
            }
        }
    }

}
