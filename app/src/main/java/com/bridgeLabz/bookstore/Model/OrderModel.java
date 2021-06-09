package com.bridgeLabz.bookstore.Model;

import java.util.List;

public class OrderModel {

    private long orderId;
    private float orderTotal;
    private List<CartResponseModel> cartModelList;
    private String orderDate;

    public OrderModel() {

    }

    public OrderModel(long orderId, float orderTotal, List<CartResponseModel> cartModelList, String orderDate) {
        this.orderId = orderId;
        this.orderTotal = orderTotal;
        this.cartModelList = cartModelList;
        this.orderDate = orderDate;
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }


    //    orderId : Int
//    orderTotal : Double
//    cartItems : List<CartItem>
//    orderDate : Date
//    transactionId : String
}
