package com.bridgeLabz.bookstore.UI.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.provider.MediaStore;
import android.util.Log;
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
import com.bridgeLabz.bookstore.Repository.ReviewRepository;
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
    private static final String TAG = "ProfileFragment";

    private ActivityResultLauncher<Intent> launchImageActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Uri imageUri = data.getData();
                        Glide.with(getContext()).load(imageUri).into(profileUserPicture);
                        String uriImage = imageUri.toString();
                        userRepository.uploadImageToUserFile(uriImage); }
                }
            });


    private  ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            new ActivityResultCallback<Boolean>() {
                @Override
                public void onActivityResult(Boolean result) {
                    if(result) {
                        navigateToGallery(launchImageActivity);
                        Log.e(TAG, "onActivityResult: PERMISSION GRANTED");
                    } else {
                        Toast.makeText(getContext(), "Permissions are Denied", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onActivityResult: PERMISSION DENIED");
                    }
                }
            });

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
        File reviewsFile = new File(getContext().getFilesDir(), "reviews.json");
        BookAssetLoader bookAssetLoader = new BookAssetLoader(getContext());
        userRepository = new UserRepository(userListFile, sharedPreference, bookAssetLoader, new ReviewRepository(reviewsFile));
        UserModel user = userRepository.getLoggedInUser();
        List<AddressModel> userAddressList = user.getAddressList();
        String userName = user.getUserName();
        String userEmail = user.getUserEmail();
        String userImage = user.getUserImage();
        profileUserName.setText(userName);
        profileUserEmail.setText(userEmail);
        Glide.with(getContext()).load(userImage).into(profileUserPicture);
        onBackPressed(view);
        profileUserAddress = view.findViewById(R.id.profile_address_button);
        profileUserAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.home_fragment_container, new AddressEditFragment())
                        .addToBackStack(null).commit();
            }
        });

        profileUserPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isExternalStoragePermissionGranted()) {
                    navigateToGallery(launchImageActivity);
                } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // In an educational UI, explain to the user why your app requires this
                    // permission for a specific feature to behave as expected. In this UI,
                    // include a "cancel" or "no thanks" button that allows the user to
                    // continue using your app without granting the permission.
                    showExternalStoragePermissionDialog();
                }
                else{
                    requestExternalStoragePermission();
                }
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

    private void navigateToGallery(ActivityResultLauncher<Intent> launchImageActivity) {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        launchImageActivity.launch(openGalleryIntent);
    }

    private void showExternalStoragePermissionDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("Storage Permission Required")
                .setMessage("This Feature requires storage permissions to choose Profile picture")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestExternalStoragePermission();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    private void requestExternalStoragePermission() {
        requestPermissionLauncher.launch(
                Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    private boolean isExternalStoragePermissionGranted() {
        return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
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