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

import com.bridgeLabz.bookstore.Model.BookModel;
import com.bridgeLabz.bookstore.Model.CartModel;
import com.bridgeLabz.bookstore.Model.UserModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.Repository.CartRepository;
import com.bridgeLabz.bookstore.UI.Adapters.CartAdapter;
import com.bridgeLabz.bookstore.helper.SharedPreference;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartFragment extends Fragment {

    private static final String TAG = "CartFragment";
    private CartRepository cartRepository;
    private SharedPreference sharedPreference;
    private int spanCount;
    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        cartRepository = new CartRepository(getContext());
        List<CartModel> cartItemBooks = cartRepository.getCartList();
        sharedPreference = new SharedPreference(getContext());
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            spanCount = 2;
        } else {
            // In portrait
            spanCount = 1;
        }
        final RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        cartRecyclerView = view.findViewById(R.id.cart_RecyclerView);
        cartRecyclerView.setLayoutManager(layoutManager);
        cartRecyclerView.setHasFixedSize(true);
        cartAdapter = new CartAdapter(cartItemBooks);
        cartRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
        onBackPressed(view);

        return view;
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

    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
    }
}
