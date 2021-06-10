package com.bridgeLabz.bookstore.UI.ViewHolders;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.Model.OrderModel;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderViewHolder extends RecyclerView.ViewHolder{
    TextView orderID, orderTotalPrice, orderDate;
    ImageView bookPic;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        orderID = itemView.findViewById(R.id.order_book_Id);
        bookPic = itemView.findViewById(R.id.order_book_image);
        orderTotalPrice = itemView.findViewById(R.id.order_books_price);
        orderDate = itemView.findViewById(R.id.order_date_preview);
    }

    @SuppressLint("SetTextI18n")
    public void bind(OrderModel order) {
        orderID.setText(String.valueOf(order.getOrderId()));
        String imageUri = "";
        for(int i=0; i<order.getCartModelList().size(); i++){
            imageUri =  order.getCartModelList().get(i).getBook().getBookImage();
            break;
        }
        orderTotalPrice.setText("Rs." + order.getOrderTotal());
        orderDate.setText(order.getOrderDate());
        Glide.with(itemView.getContext())
                .load(imageUri)
                .into(bookPic);
    }
}
