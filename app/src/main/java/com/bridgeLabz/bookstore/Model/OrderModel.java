package com.bridgeLabz.bookstore.Model;

import java.util.List;

public class OrderModel {

    private long orderId;
    private float orderTotal;
    private List<CartModel> cartModelList;
    private String orderDate;
    private long deliveryAddressId;

    public OrderModel() {

    }

    public OrderModel(long orderId, float orderTotal, List<CartModel> cartModelList, String orderDate, long deliveryAddressId) {
        this.orderId = orderId;
        this.orderTotal = orderTotal;
        this.cartModelList = cartModelList;
        this.orderDate = orderDate;
        this.deliveryAddressId = deliveryAddressId;
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

    public List<CartModel> getCartModelList() {
        return cartModelList;
    }

    public void setCartModelList(List<CartModel> cartModelList) {
        this.cartModelList = cartModelList;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public long getDeliveryAddressId() {
        return deliveryAddressId;
    }

    public void setDeliveryAddressId(long deliveryAddressId) {
        this.deliveryAddressId = deliveryAddressId;
    }

    //    orderId : Int
//    orderTotal : Double
//    cartItems : List<CartItem>
//    orderDate : Date
//    transactionId : String
}
