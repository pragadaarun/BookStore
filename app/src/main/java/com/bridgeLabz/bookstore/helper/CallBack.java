package com.bridgeLabz.bookstore.helper;

public interface CallBack<T> {
    void onSuccess(T data);
    void onFailure(Exception exception);

}
