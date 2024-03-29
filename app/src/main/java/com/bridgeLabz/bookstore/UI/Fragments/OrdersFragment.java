package com.bridgeLabz.bookstore.UI.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bridgeLabz.bookstore.Model.CartModel;
import com.bridgeLabz.bookstore.Model.OrderModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.Repository.CartRepository;
import com.bridgeLabz.bookstore.Repository.ReviewRepository;
import com.bridgeLabz.bookstore.Repository.UserRepository;
import com.bridgeLabz.bookstore.UI.Adapters.OrderAdapter;
import com.bridgeLabz.bookstore.helper.BookAssetLoader;
import com.bridgeLabz.bookstore.helper.OnOrderClickListener;
import com.bridgeLabz.bookstore.helper.SharedPreference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrdersFragment extends Fragment {

    private static final String TAG = "OrdersFragment";
    private OrderModel order;
    private long orderID;
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private UserRepository userRepository;
    private OrderDetailFragment orderDetailFragment;

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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        File userListFile = new File(getContext().getFilesDir(), "users.json");
        File reviewsFile = new File(getContext().getFilesDir(), "reviews.json");
        BookAssetLoader bookAssetLoader = new BookAssetLoader(getContext());
        userRepository = new UserRepository(userListFile, new SharedPreference(getContext()), bookAssetLoader, new ReviewRepository(reviewsFile));
        List<OrderModel> orderList = userRepository.getAllOrders();
        final RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView = view.findViewById(R.id.order_RecyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        orderAdapter = new OrderAdapter(orderList, new OnOrderClickListener() {
            @Override
            public void onOrderClick(int position, View viewHolder) {
                long orderId = orderList.get(position).getOrderId();
                Log.e(TAG, "onOrderClick: " + orderId );
                orderDetailFragment = new OrderDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("orderId", orderId);
                orderDetailFragment.setArguments(bundle);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.home_fragment_container, orderDetailFragment)
                        .addToBackStack(null).commit();

            }
        });
        recyclerView.setAdapter(orderAdapter);
        orderAdapter.notifyDataSetChanged();
        onBackPressed(view);
        return view;
    }

    private void onBackPressed(View view) {
        Toolbar favoriteToolbar = (Toolbar) view.findViewById(R.id.order_toolbar);
        favoriteToolbar.setTitle("Orders");
        favoriteToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        favoriteToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });
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