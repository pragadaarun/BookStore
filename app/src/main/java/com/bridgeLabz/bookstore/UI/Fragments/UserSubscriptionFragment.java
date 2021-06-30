package com.bridgeLabz.bookstore.UI.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bridgeLabz.bookstore.Model.UserModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.Repository.CartRepository;
import com.bridgeLabz.bookstore.Repository.ReviewRepository;
import com.bridgeLabz.bookstore.Repository.UserRepository;
import com.bridgeLabz.bookstore.helper.BookAssetLoader;
import com.bridgeLabz.bookstore.helper.SharedPreference;

import java.io.File;
import java.util.List;

public class UserSubscriptionFragment extends Fragment {

    private Button button;

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
        View view = inflater.inflate(R.layout.fragment_user_subscription, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.subscription_toolbar);
        toolbar.setTitle("Subscription");
        File userListFile = new File(getContext().getFilesDir(), "users.json");
        File reviewsFile = new File(getContext().getFilesDir(), "reviews.json");
        BookAssetLoader bookAssetLoader = new BookAssetLoader(getContext());
        SharedPreference sharedPreference = new SharedPreference(getContext());
        UserRepository userRepository = new UserRepository(userListFile, sharedPreference, bookAssetLoader, new ReviewRepository(reviewsFile));
        button = view.findViewById(R.id.pay_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                List<UserModel> userList = userRepository.getUsersList();
//                UserModel user = userRepository.getLoggedInUser();
//                userList.get(user.getUserId()).setUserSubscription(true);
//                userRepository.writeUsersList(userList);
                PaymentFragment paymentFragment = new PaymentFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.home_fragment_container, paymentFragment).commit();
            }
        });
        return view;
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