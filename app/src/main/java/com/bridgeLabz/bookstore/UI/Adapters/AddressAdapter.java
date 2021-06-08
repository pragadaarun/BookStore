package com.bridgeLabz.bookstore.UI.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bridgeLabz.bookstore.Model.AddressModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.UI.ViewHolders.AddressViewHolder;
import com.bridgeLabz.bookstore.helper.OnAddressListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddressAdapter extends RecyclerView.Adapter<AddressViewHolder> {

    private List<AddressModel> addressArrayList = new ArrayList<>();
    private OnAddressListener onAddressListener;
    private static final String TAG = "Address_Pick_Adapter";

    public AddressAdapter(List<AddressModel> addressArrayList, OnAddressListener onAddressListener) {

        this.addressArrayList = addressArrayList;
        this.onAddressListener = onAddressListener;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.address_preview, parent, false);
        return new AddressViewHolder(view, onAddressListener);

    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        AddressModel address = addressArrayList.get(position);
        holder.bind(address);
    }

    @Override
    public int getItemCount() {
        return addressArrayList.size();
    }
}
