package com.bridgeLabz.bookstore.UI.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bridgeLabz.bookstore.Model.AddressModel;
import com.bridgeLabz.bookstore.Model.UserModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.Repository.UserRepository;
import com.bridgeLabz.bookstore.helper.SharedPreference;

import java.util.List;
import java.util.Objects;

public class AddressEditFragment extends Fragment {

    private EditText houseNO, street, city, state, pinCode, mobile;
    private Button Submit;
    private SharedPreference sharedPreference;
    private UserRepository userRepository;
    private static final String TAG = "AddressFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_edit, container, false);
        sharedPreference = new SharedPreference(getContext());
        userRepository = new UserRepository(getContext());

        houseNO = view.findViewById(R.id.address_house_no);
        street = view.findViewById(R.id.address_street);
        city = view.findViewById(R.id.address_city);
        state = view.findViewById(R.id.address_state);
        pinCode = view.findViewById(R.id.address_pincode);
        mobile = view.findViewById(R.id.address_mobile);
        Submit = view.findViewById(R.id.address_submit);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long addressIdLong = System.currentTimeMillis();
                String houseNoString = houseNO.getText().toString();
                String streetString = street.getText().toString();
                String cityString = city.getText().toString();
                String stateString = state.getText().toString();
                String pinCodeString = pinCode.getText().toString();
                String mobileString = mobile.getText().toString();
                AddressModel userAddress = new AddressModel(addressIdLong, houseNoString, streetString, cityString, stateString, pinCodeString, mobileString);

                List<UserModel> usersList = userRepository.getUsersList();
                List<AddressModel> address = usersList.get(sharedPreference.getPresentUserId()).getAddressList();
                address.add(userAddress);
                usersList.get(sharedPreference.getPresentUserId()).setAddressList(address);
                userRepository.writeUsersList(usersList);


                getParentFragmentManager().beginTransaction().replace(R.id.home_fragment_container, new AddressFragment())
                        .addToBackStack(null).commit();
            }
        });
        onBackPressed(view);
        return view;
    }

    private void onBackPressed(View view) {

        Toolbar addressToolbar = (Toolbar) view.findViewById(R.id.address_edit_toolbar);
        addressToolbar.setTitle("Address");
        addressToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        addressToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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