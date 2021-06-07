package com.bridgeLabz.bookstore.UI.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bridgeLabz.bookstore.Model.CartModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.UI.ViewHolders.CartViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<CartModel> cartBooksList = new ArrayList<>();

    public CartAdapter(List<CartModel> cartBooksList) {
        this.cartBooksList = cartBooksList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_in_cart_preview, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartModel cartModel = cartBooksList.get(position);
        holder.bind(cartModel);
    }

    @Override
    public int getItemCount() {
        return cartBooksList.size();
    }
}
