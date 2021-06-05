package com.bridgeLabz.bookstore.UI.Fragments.Authentication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bridgeLabz.bookstore.Model.UserModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.UI.Activities.StoreActivity;
import com.bridgeLabz.bookstore.helper.SharedPreference;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RegisterFragment extends Fragment {
    
    private Button registerButton;
    private EditText userName, userEmail, userPassword, verifyPassword;
    private TextView loginText;
    SharedPreference sharedPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews();
        viewListeners();

    }

    private void findViews() {
        userName = getView().findViewById(R.id.register_name);
        userEmail = getView().findViewById(R.id.register_email);
        userPassword = getView().findViewById(R.id.register_password);
        verifyPassword = getView().findViewById(R.id.register_password_verify);
        registerButton = getView().findViewById(R.id.register_button);
        loginText = getView().findViewById(R.id.back_to_login);
        sharedPreference = new SharedPreference(getContext());

    }

    private void viewListeners() {
        registerButton.setOnClickListener(v -> {
            registerNewUser();
        });

        loginText.setOnClickListener(v -> {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.sign_in_container, new LoginFragment())
                        .addToBackStack(null).commit();
        });
    }

    private void registerNewUser() {
        String name = userName.getText().toString();
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();
        String confirmPassword = verifyPassword.getText().toString();
        String jsonString;

        if (!isValidName(name) && (!isValidEmail(email))
                && (!isValidPassword(password, confirmPassword))
                && (name.isEmpty() && email.isEmpty() && password.isEmpty())) {
            Toast.makeText(getContext(), "Please provide required fields",
                    Toast.LENGTH_SHORT).show();
        } else  if (!(email.isEmpty() && password.isEmpty())){
            try{
                File file = new File(getActivity().getFilesDir(), "users.json");
                ObjectMapper mapper = new ObjectMapper();
                ArrayList<UserModel> userList = new ArrayList<>();
                List<Integer> favouriteItemList = new ArrayList<>();
                int userId = checkRegisters();
                sharedPreference.setRegisteredUsersCount(userId);
                sharedPreference.setPresentUserId(userId);
                UserModel user = new UserModel(userId, name, email, password, favouriteItemList);
                userList.add(user);

                if (file.exists()){
                    ArrayList<UserModel>  userList1 = mapper.readValue(new File(getActivity().getFilesDir(),
                            "users.json"),new TypeReference<List<UserModel>>(){} );

                    List<UserModel> joined = new ArrayList<UserModel>();

                    joined.addAll(userList1);
                    joined.addAll(userList);

                    jsonString = mapper.writeValueAsString(joined);

                    FileOutputStream fos = getActivity().openFileOutput("users.json", Context.MODE_PRIVATE);
                    fos.write(jsonString.getBytes());
                    fos.close();
                } else {
                    jsonString = mapper.writeValueAsString(userList);
                    FileOutputStream fos = getActivity().openFileOutput("users.json", Context.MODE_PRIVATE);
                    fos.write(jsonString.getBytes());
                    fos.close();
                }
                moveToStoreActivity();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int checkRegisters() {
        return sharedPreference.getRegisteredUsersCount() + 1;
    }

    private void moveToStoreActivity() {
        sharedPreference.setLoggedIN(true);
        startActivity(new Intent(getContext(), StoreActivity.class));
    }

    private boolean isValidName(String name){

        if (name.isEmpty()) {
            userName.setError("Please enter name id");
            userName.requestFocus();
            return false;
        } else if (name.matches("[0-9*$%#&^()@!_+{}';]*")) {
            userName.setError("Please enter proper name id");
            userName.requestFocus();
            return false;

        } else {
            return true;
        }
    }

    private boolean isValidEmail(String email){

        if (email.isEmpty()) {
            userEmail.setError("Please enter email id");
            userEmail.requestFocus();
            return false;
        } else if (!email.matches("^[a-zA-Z]+([._+-]{0,1}[a-zA-Z0-9]+)*@[a-zA-Z0-9]+.[a-zA-Z]{2,4}+(?:\\.[a-z]{2,}){0,1}$")) {
            userEmail.setError("Please enter valid email id");
            userEmail.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidPassword(String password, String confirmPassword){

        if (password.isEmpty()) {
            userPassword.setError("Please enter your password");
            userPassword.requestFocus();
            return false;
        } else  if (!password.matches("(^(?=.*[A-Z]))(?=.*[0-9])(?=.*[a-z])(?=.*[@*&^%#-*+!]{1}).{8,}$")) {
            userPassword.setError("Please enter Valid password");
            userPassword.requestFocus();
            return false;
        } else if (!password.equals(confirmPassword)) {
            verifyPassword.setError("Password not Matches");
            verifyPassword.requestFocus();
            return false;
        } else {
            return true;
        }
    }
}
