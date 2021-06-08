package com.bridgeLabz.bookstore.Model;

import java.util.Date;
import java.util.List;

public class OrderModel {

    private long orderId;
    private float orderTotal;
    private List<CartResponseModel> cartModelList;

    public OrderModel() {

    }

    public OrderModel(long orderId, float orderTotal, List<CartResponseModel> cartModelList) {
        this.orderId = orderId;
        this.orderTotal = orderTotal;
        this.cartModelList = cartModelList;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
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


    //    orderId : Int
//    orderTotal : Double
//    cartItems : List<CartItem>
//    orderDate : Date
//    transactionId : String
}
