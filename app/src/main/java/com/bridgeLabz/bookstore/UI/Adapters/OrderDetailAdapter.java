package com.bridgeLabz.bookstore.UI.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bridgeLabz.bookstore.Model.CartModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.UI.Fragments.OrderDetailFragment;
import com.bridgeLabz.bookstore.UI.ViewHolders.OrderDetailViewHolder;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderDetailAdapter  extends RecyclerView.Adapter<OrderDetailViewHolder> {

    private ArrayList<CartModel> orderList = new ArrayList<>();

    public OrderDetailAdapter(ArrayList<CartModel> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_order_detail,parent,false);
        return new OrderDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailViewHolder holder, int position) {

        CartModel order = orderList.get(position);
        holder.bind(order);

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}