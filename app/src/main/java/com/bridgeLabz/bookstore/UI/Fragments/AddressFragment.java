package com.bridgeLabz.bookstore.UI.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bridgeLabz.bookstore.Model.AddressModel;
import com.bridgeLabz.bookstore.Model.UserModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.Repository.ReviewRepository;
import com.bridgeLabz.bookstore.Repository.UserRepository;
import com.bridgeLabz.bookstore.UI.Adapters.AddressAdapter;
import com.bridgeLabz.bookstore.helper.BookAssetLoader;
import com.bridgeLabz.bookstore.helper.OnAddressListener;
import com.bridgeLabz.bookstore.helper.SharedPreference;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class AddressFragment extends Fragment {

    private SharedPreference sharedPreference;
    private RecyclerView recyclerView;
    private AddressAdapter addressAdapter;
    private UserRepository userRepository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if(activity != null) {
            if(activity.getSupportActionBar() != null){
                activity.getSupportActionBar().hide();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_address, container, false);
        final RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        sharedPreference = new SharedPreference(getContext());
        File userListFile = new File(getContext().getFilesDir(), "users.json");
        File reviewsFile = new File(getContext().getFilesDir(), "reviews.json");
        BookAssetLoader bookAssetLoader = new BookAssetLoader(getContext());
        userRepository = new UserRepository(userListFile, sharedPreference, bookAssetLoader, new ReviewRepository(reviewsFile));
        UserModel user = userRepository.getLoggedInUser();
        List<AddressModel> userAddressList = user.getAddressList();
        Button addAddressButton = view.findViewById(R.id.address_add_button);
        addAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.home_fragment_container, new AddressEditFragment())
                        .addToBackStack(null).commit();
            }
        });
        recyclerView = view.findViewById(R.id.address_recycle_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        addressAdapter = new AddressAdapter(userAddressList, new OnAddressListener() {
            @Override
            public void onAddressClick(int position, View viewHolder) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.home_fragment_container, new PurchasedFragment()).addToBackStack(null).commit();
            }
        });
        recyclerView.setAdapter(addressAdapter);
        addressAdapter.notifyDataSetChanged();
        onBackPressed(view);
        return view;
    }
    private void onBackPressed(View view) {

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.address_toolbar);
        toolbar.setTitle("Select Delivery Address");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle any click event
                getParentFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        if(activity != null) {
            if(activity.getSupportActionBar() != null){
                activity.getSupportActionBar().show();
            }
        }
    }

}