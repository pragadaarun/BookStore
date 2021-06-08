package com.bridgeLabz.bookstore.UI.ViewHolders;

import android.view.View;
import android.widget.TextView;

import com.bridgeLabz.bookstore.Model.AddressModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.helper.OnAddressListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView mobilePreview, houseNoPreview, streetPreview, cityPreview, statePreview, pinPreview;
    private OnAddressListener onAddressListener;
    private static final String TAG = "AddressPick_ViewHolder";

    public AddressViewHolder(@NonNull View itemView, OnAddressListener onAddressListener) {
        super(itemView);
        houseNoPreview = itemView.findViewById(R.id.preview_house);
        streetPreview = itemView.findViewById(R.id.preview_street);
        cityPreview = itemView.findViewById(R.id.preview_city);
        statePreview = itemView.findViewById(R.id.preview_state);
        pinPreview = itemView.findViewById(R.id.preview_pin);
        mobilePreview = itemView.findViewById(R.id.preview_mobile);
        this.onAddressListener = onAddressListener;
        itemView.setOnClickListener(this);
    }
    public void bind(AddressModel address) {
        houseNoPreview.setText(address.getHouseNo());
        streetPreview.setText(address.getStreet());
        cityPreview.setText(address.getCity());
        statePreview.setText(address.getState());
        pinPreview.setText(address.getPinCode());
        mobilePreview.setText(address.getMobile());
    }

    @Override
    public void onClick(View v) {
        onAddressListener.onAddressClick(getBindingAdapterPosition(),v);
    }
}
