package com.bridgeLabz.bookstore.UI.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bridgeLabz.bookstore.Model.OrderModel;
import com.bridgeLabz.bookstore.UI.ViewHolders.OrderViewHolder;

import java.util.ArrayList;
import java.util.List;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.helper.OnOrderClickListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderAdapter  extends RecyclerView.Adapter<OrderViewHolder> {

    private List<OrderModel> orderList = new ArrayList<>();
    private OnOrderClickListener onOrderClickListener;

    public OrderAdapter(List<OrderModel> orderList, OnOrderClickListener onOrderClickListener){
        this.orderList = orderList;
        this.onOrderClickListener = onOrderClickListener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.book_in_order_preview, parent, false);
        return new OrderViewHolder(view, onOrderClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderModel order = orderList.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
