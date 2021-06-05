package com.bridgeLabz.bookstore.UI.Fragments.Authentication;

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
import com.bridgeLabz.bookstore.Repository.UserRepository;
import com.bridgeLabz.bookstore.UI.Activities.StoreActivity;
import com.bridgeLabz.bookstore.helper.SharedPreference;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LoginFragment extends Fragment {

    private TextView registerText;
    private EditText loginEmail, loginPassword;
    private Button loginButton;
    private SharedPreference sharedPreference;
    private UserRepository userRepository;

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findViews();
        clickListeners();

    }

    private void clickListeners() {

        loginButton.setOnClickListener(v -> {
            try {
                logInValidation();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        registerText.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.sign_in_container, new RegisterFragment())
                    .addToBackStack(null).commit();
        });
    }

    private void findViews() {
        registerText = getView().findViewById(R.id.createAccount);
        loginEmail = getView().findViewById(R.id.login_email);
        loginPassword = getView().findViewById(R.id.login_password);
        loginButton = getView().findViewById(R.id.login_button);
        sharedPreference = new SharedPreference(getContext());
    }

    private void logInValidation() throws IOException {
        String email = loginEmail.getText().toString();
        String password = loginPassword.getText().toString();
        if(email.isEmpty()){
            loginEmail.setError("Please enter email id");
            loginEmail.requestFocus();
        } else if(!email.matches("^[a-zA-Z]+([._+-]{0,1}[a-zA-Z0-9]+)*@[a-zA-Z0-9]+.[a-zA-Z]{2,4}+(?:\\.[a-z]{2,}){0,1}$")) {
            loginEmail.setError("Please enter valid email id");
            loginEmail.requestFocus();
        }else  if(password.isEmpty()){
            loginPassword.setError("Please enter your password");
            loginPassword.requestFocus();
        } else  if(!password.matches("(^(?=.*[A-Z]))(?=.*[0-9])(?=.*[a-z])(?=.*[@*&^%#-*+!]{1}).{8,}$")) {
            loginPassword.setError("Please enter Valid password");
            loginPassword.requestFocus();
        }
        else {

            ObjectMapper mapper = new ObjectMapper();
            List<UserModel> userList1 = mapper.readValue(new File(getActivity().getFilesDir(),
                    "users.json"),new TypeReference<List<UserModel>>(){} );
            int i;
            boolean isLoggedIN = false;
            for (i=1;i<userList1.size();i++) {
                if(userList1.get(i).getUserEmail().equals(email) && userList1.get(i).getUserPassword().equals(password)) {
                    isLoggedIN = true;

                    break;
                }
            }
            if (isLoggedIN){
                Toast.makeText(getContext(), "Sign In Successful!", Toast.LENGTH_SHORT).show();
                sharedPreference.setLoggedIN(true);
                sharedPreference.setPresentUserId(i);
                startActivity(new Intent(getContext(), StoreActivity.class));
            }
            else {
                Toast.makeText(getContext(),"Credentials Wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
