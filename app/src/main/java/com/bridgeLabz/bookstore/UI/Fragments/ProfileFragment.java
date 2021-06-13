package com.bridgeLabz.bookstore.UI.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bridgeLabz.bookstore.Model.AddressModel;
import com.bridgeLabz.bookstore.Model.UserModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.Repository.UserRepository;
import com.bridgeLabz.bookstore.UI.Adapters.AddressAdapter;
import com.bridgeLabz.bookstore.helper.BookAssetLoader;
import com.bridgeLabz.bookstore.helper.OnAddressListener;
import com.bridgeLabz.bookstore.helper.SharedPreference;
import com.bumptech.glide.Glide;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private TextView profileUserName, profileUserEmail;
    private Button profileUserAddress;
    private ImageView profileUserPicture;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profileUserName = view.findViewById(R.id.profile_name_id);
        profileUserEmail = view.findViewById(R.id.profile_email_id);
        profileUserPicture = view.findViewById(R.id.profile_user_image);
        sharedPreference = new SharedPreference(this.getContext());
        File userListFile = new File(getContext().getFilesDir(), "users.json");
        BookAssetLoader bookAssetLoader = new BookAssetLoader(getContext());
        userRepository = new UserRepository(userListFile, sharedPreference, bookAssetLoader);
        UserModel user = userRepository.getLoggedInUser();
        List<AddressModel> userAddressList = user.getAddressList();
        String userName = user.getUserName();
        String userEmail = user.getUserEmail();
        String userImage = user.getUserImage();
        profileUserName.setText(userName);
        profileUserEmail.setText(userEmail);
        Glide.with(getContext()).load(userImage).into(profileUserPicture);
        onBackPressed(view);
        ActivityResultLauncher<Intent> launchImageActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            Uri imageUri = data.getData();
                            Glide.with(getContext()).load(imageUri).into(profileUserPicture);
                            String uriImage = imageUri.toString();
                            userRepository.uploadImageToUserFile(uriImage);
                        }
                    }
                });

        profileUserPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                launchImageActivity.launch(openGalleryIntent);
            }
        });

        final RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView = view.findViewById(R.id.profile_address_recycler_view_id);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        addressAdapter = new AddressAdapter(userAddressList, new OnAddressListener() {
            @Override
            public void onAddressClick(int position, View viewHolder) {
                Toast.makeText(getContext(), "Address is in profile", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(addressAdapter);
        addressAdapter.notifyDataSetChanged();

        return view;
    }

    private void onBackPressed(View view) {
        Toolbar favoriteToolbar = (Toolbar) view.findViewById(R.id.profile_toolbar);
        favoriteToolbar.setTitle("Profile");
        favoriteToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        favoriteToolbar.setNavigationOnClickListener(new View.OnClickListener() {
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