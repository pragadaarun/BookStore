package com.bridgeLabz.bookstore.UI.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bridgeLabz.bookstore.R;

import java.util.Objects;

public class PurchasedFragment extends Fragment {

        private TextView orderId;
        private Button continueShopping;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_purchased, container, false);
            orderId = view.findViewById(R.id.order_id_display);
            continueShopping = view.findViewById(R.id.continue_shopping_button);

            long orderNo = System.currentTimeMillis();
            orderId.setText(String.valueOf(orderNo) );
            continueShopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDestroy();
                }
            });
            onBackPressed(view);
            return view;
        }

        private void onBackPressed(View view) {

            Toolbar toolbar = (Toolbar) view.findViewById(R.id.purchased_toolbar);
            toolbar.setTitle("Cart List");
            toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDestroy();
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