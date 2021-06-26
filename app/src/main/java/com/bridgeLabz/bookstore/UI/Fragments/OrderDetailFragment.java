package com.bridgeLabz.bookstore.UI.Fragments;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bridgeLabz.bookstore.Model.AddressModel;
import com.bridgeLabz.bookstore.Model.CartModel;
import com.bridgeLabz.bookstore.Model.OrderModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.Repository.ReviewRepository;
import com.bridgeLabz.bookstore.Repository.UserRepository;
import com.bridgeLabz.bookstore.UI.Adapters.OrderDetailAdapter;
import com.bridgeLabz.bookstore.helper.BookAssetLoader;
import com.bridgeLabz.bookstore.helper.SharedPreference;

import java.io.File;
import java.util.ArrayList;

public class OrderDetailFragment extends Fragment {

    RecyclerView recyclerView;
    private OrderDetailAdapter orderDetailAdapter;
    private UserRepository userRepository;
    int spanCount;
    private TextView orderIdTextView, orderDateTextView, orderTotalPriceTextView, deliveredAddressTextView;

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
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        findViews(view);
        File userListFile = new File(getContext().getFilesDir(), "users.json");
        File reviewsFile = new File(getContext().getFilesDir(), "reviews.json");
        BookAssetLoader bookAssetLoader = new BookAssetLoader(getContext());
        userRepository = new UserRepository(userListFile, new SharedPreference(getContext()), bookAssetLoader, new ReviewRepository(reviewsFile));
        Bundle bundle = this.getArguments();
        long orderId = bundle.getLong("orderId");
        OrderModel order = userRepository.getOrderById(orderId);
        orderIdTextView.setText(String.valueOf(order.getOrderId()));
        orderDateTextView.setText(order.getOrderDate());
        orderTotalPriceTextView.setText(String.valueOf(order.getOrderTotal()));
        AddressModel deliveredAddress = userRepository.getAddressById(order.getDeliveryAddressId());
        String deliveredAddressString = deliveredAddress.getHouseNo() + ", \n"
                + deliveredAddress.getStreet() + ", \n"
                + deliveredAddress.getCity() + ", \n"
                + deliveredAddress.getState() + ", "
                + deliveredAddress.getPinCode();
        deliveredAddressTextView.setText(deliveredAddressString);
        ArrayList<CartModel> list = (ArrayList<CartModel>) order.getCartModelList();
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            spanCount = 2;
        } else {
            // In portrait
            spanCount = 1;
        }
        final RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        recyclerView = view.findViewById(R.id.order_detail_recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        orderDetailAdapter = new OrderDetailAdapter(list);
        recyclerView.setAdapter(orderDetailAdapter);
        orderDetailAdapter.notifyDataSetChanged();

        onBackPressed(view);

        return view;
    }

    private void findViews(View view) {
        orderIdTextView = view.findViewById(R.id.order_detail_id_text_view);
        orderDateTextView = view.findViewById(R.id.order_detail_id_date_text_view);
        orderTotalPriceTextView = view.findViewById(R.id.order_detail_total_price_text_view);
        deliveredAddressTextView = view.findViewById(R.id.order_detail_delivered_address_text_view);
    }

    private void onBackPressed(View view) {
        Toolbar favoriteToolbar = (Toolbar) view.findViewById(R.id.order_detail_toolbar);
        favoriteToolbar.setTitle("Order");
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