package com.bridgeLabz.bookstore.UI.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.Repository.CartRepository;
import com.bridgeLabz.bookstore.Repository.ReviewRepository;
import com.bridgeLabz.bookstore.Repository.UserRepository;
import com.bridgeLabz.bookstore.UI.Activities.HomeActivity;
import com.bridgeLabz.bookstore.helper.AddBadge;
import com.bridgeLabz.bookstore.helper.BookAssetLoader;
import com.bridgeLabz.bookstore.helper.MyWorker;
import com.bridgeLabz.bookstore.helper.SharedPreference;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PurchasedFragment extends Fragment {

    private TextView orderId, dateDisplay;
    private Button continueShopping;
    private UserRepository userRepository;
    private String date;
    private long orderNo;
    private CartRepository cartRepository;

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
        View view = inflater.inflate(R.layout.fragment_purchased, container, false);
        orderId = view.findViewById(R.id.order_id_display);
        dateDisplay = view.findViewById(R.id.purchased_date_display);
        continueShopping = view.findViewById(R.id.continue_shopping_button);
        File userListFile = new File(getContext().getFilesDir(), "users.json");
        File reviewsFile = new File(getContext().getFilesDir(), "reviews.json");
        BookAssetLoader bookAssetLoader = new BookAssetLoader(getContext());
        userRepository = new UserRepository(userListFile, new SharedPreference(getContext()),
                bookAssetLoader, new ReviewRepository(reviewsFile));
        cartRepository = new CartRepository(userListFile, userRepository,
                bookAssetLoader, new ReviewRepository(reviewsFile));
        orderNo = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new
                SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());
        dateDisplay.setText(date);
        createOrderList(orderNo, date);
        orderId.setText(String.valueOf(orderNo));
        //Badge Notification set as Zero
        try{
            ((AddBadge) getActivity()).onAddCart(cartRepository.getCartList().size());

        }catch (ClassCastException e){
            e.printStackTrace();
        }

        //WorkManager
        final OneTimeWorkRequest.Builder workRequest =
                new OneTimeWorkRequest.Builder(MyWorker.class);
        Data.Builder data = new Data.Builder();
        data.putString(MyWorker.NOTIFICATION_CHANNEL_ID, "order");
        data.putString(MyWorker.NOTIFICATION_CHANNEL, "ORDERS");
        data.putString(MyWorker.NOTIFICATION_TITLE, "Order Placed Success");
        data.putString(MyWorker.NOTIFICATION_MESSAGE, "Track the Order with Order Id : " + orderNo );
        workRequest.setInputData(data.build());
        WorkManager.getInstance(getContext()).enqueue(workRequest.build());

        continueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = ((AppCompatActivity) getActivity());
                if(activity != null) {
                    activity.getSupportFragmentManager()
                            .popBackStack(HomeActivity.BACK_STACK_TAG_CART_FLOW,
                                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }
        });
        return view;
    }

    private void createOrderList(long orderNo, String date) {
        userRepository.addOrdersList(orderNo, date);
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