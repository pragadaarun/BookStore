package com.bridgeLabz.bookstore.UI.Fragments;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bridgeLabz.bookstore.Model.UserModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.UI.Fragments.BooksListFragment;
import com.bridgeLabz.bookstore.helper.SharedPreference;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class PaymentFragment extends Fragment {
    private static final char space = ' ';
    SharedPreference sharedPreference;
    boolean isBackShowing = false;
    BooksListFragment booksListFragment;
    String amount = "₹ 399.00";
    private EditText cardName;
    private EditText cardNumber;
    private TextView paymentAmountTextHolder;
    private TextView previewCardType;
    private EditText cvc;
    private EditText expiryDate;
    private TextView previewCardName;
    private TextView previewCardNumber;
    private TextView previewCvc;
    private TextView previewExpiry;
    private TextView paymentAmount;
    private ViewGroup cardBack;
    private ViewGroup cardFront;
    private Button btnPay;
    private char slash = '/';

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
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        cardName = view.findViewById(R.id.card_name);
        cardNumber = view.findViewById(R.id.card_number);
        cvc = view.findViewById(R.id.cvc);
        expiryDate = view.findViewById(R.id.expiry_date);
        previewCardName = view.findViewById(R.id.card_preview_name);
        previewCardNumber = view.findViewById(R.id.card_preview_number);
        previewCvc = view.findViewById(R.id.card_preview_cvc);
        previewExpiry = view.findViewById(R.id.card_preview_expiry);
        paymentAmount = view.findViewById(R.id.payment_amount);
        paymentAmountTextHolder = view.findViewById(R.id.payment_amount_holder);
        previewCardType = view.findViewById(R.id.card_preview_type);
        cardFront = view.findViewById(R.id.card_preview_front);
        cardBack = view.findViewById(R.id.card_preview_back);
        btnPay = view.findViewById(R.id.btn_pay);
        paymentAmount.setText(amount);
        sharedPreference = new SharedPreference(this.getContext());
        btnPay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String jsonStr = null;
                boolean subscribed;
                ObjectMapper mapper = new ObjectMapper();

                try {
                    List<UserModel> userList1 = mapper.readValue(new File(getContext().getFilesDir(),
                            "Users.json"), new TypeReference<List<UserModel>>() {
                    });
                    //subscribed = userList1.get(sharedPreference.getPresentUserId()).isSubScribed();
                    userList1.get(sharedPreference.getPresentUserId()).setUserSubscription(true);

                    jsonStr = mapper.writeValueAsString(userList1);

                    FileOutputStream fos = getContext().openFileOutput("Users.json", Context.MODE_PRIVATE);
                    fos.write(jsonStr.getBytes());
                    fos.close();
                } catch (IOException jsonParseException) {
                    jsonParseException.printStackTrace();
                }
                booksListFragment = new BooksListFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.home_fragment_container, booksListFragment).commit();
            }
        });
        cardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                previewCardNumber.setText(editable.toString());
            }
        });

        expiryDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                previewExpiry.setText(editable.toString());
            }
        });


        cardName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().trim().length() > 0) {
                    previewCardName.setText(editable.toString());
                }
            }
        });


        cvc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().trim().length() > 0) {
                    previewCvc.setText(editable.toString());
                }
            }
        });


        cvc.setOnFocusChangeListener(
                new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        if (b) showBack();
                    }
                });

        cardName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) showFront();
            }
        });

        cardNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) showFront();
            }
        });

        expiryDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) showFront();
            }
        });

        return view;
    }

    private void showBack() {
        if (!isBackShowing) {
            Animator cardFlipLeftIn = AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_left_in);
            cardFlipLeftIn.setTarget(cardFront);
            cardFlipLeftIn.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    cardFront.setVisibility(GONE);
                    cardBack.setVisibility(VISIBLE);
                    isBackShowing = true;
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            cardFlipLeftIn.start();
        }
    }

    private void showFront() {
        if (isBackShowing) {
            Animator cardFlipRightIn = AnimatorInflater.loadAnimator(getContext(), R.animator.card_flip_right_in);
            cardFlipRightIn.setTarget(cardBack);
            cardFlipRightIn.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    cardBack.setVisibility(GONE);
                    cardFront.setVisibility(VISIBLE);
                    isBackShowing = false;
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            cardFlipRightIn.start();
        }

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