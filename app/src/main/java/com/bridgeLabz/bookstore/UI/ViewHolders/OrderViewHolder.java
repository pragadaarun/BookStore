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
    TextView orderID, orderTotalPrice;
    ImageView bookPic;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        orderID = itemView.findViewById(R.id.order_book_Id);
        bookPic = itemView.findViewById(R.id.order_book_image);
        orderTotalPrice = itemView.findViewById(R.id.order_books_price);
    }

    @SuppressLint("SetTextI18n")
    public void bind(OrderModel order) {
        orderID.setText("Rs." + order.getOrderId());
        String imageUri = "";
        for(int i=0; i<order.getCartModelList().size(); i++){
            int bookId =  order.getCartModelList().get(i).getBookId();
            break;
        }
        orderTotalPrice.setText(String.valueOf(order.getOrderTotal()));
        Glide.with(itemView.getContext())
                .load(imageUri)
                .into(bookPic);
    }
}
