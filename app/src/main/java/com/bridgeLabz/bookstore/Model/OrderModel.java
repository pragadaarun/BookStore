package com.bridgeLabz.bookstore.Model;

import java.util.Date;
import java.util.List;

public class OrderModel {

    private int orderId;
    private float orderTotal;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public float getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(float orderTotal) {
        this.orderTotal = orderTotal;
    }

    public List<CartResponseModel> getCartModelList() {
        return cartModelList;
    }

    public void setCartModelList(List<CartResponseModel> cartModelList) {
        this.cartModelList = cartModelList;
    }

    public OrderModel(int orderId, float orderTotal, List<CartResponseModel> cartModelList) {
        this.orderId = orderId;
        this.orderTotal = orderTotal;
        this.cartModelList = cartModelList;
    }

    private List<CartResponseModel> cartModelList;

    //    orderId : Int
//    orderTotal : Double
//    cartItems : List<CartItem>
//    orderDate : Date
//    transactionId : String
}
