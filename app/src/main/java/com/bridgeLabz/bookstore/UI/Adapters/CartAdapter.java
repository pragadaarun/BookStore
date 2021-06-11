package com.bridgeLabz.bookstore.UI.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bridgeLabz.bookstore.Model.BookModel;
import com.bridgeLabz.bookstore.Model.CartModel;
import com.bridgeLabz.bookstore.R;
import com.bridgeLabz.bookstore.UI.ViewHolders.CartViewHolder;
import com.bridgeLabz.bookstore.helper.CartBookClickListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<CartModel> cartBooksList = new ArrayList<>();
    private CartBookClickListener cartBookClickListener;

    public CartAdapter(List<CartModel> cartBooksList, CartBookClickListener cartBookClickListener) {
        this.cartBooksList = cartBooksList;
        this.cartBookClickListener = cartBookClickListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_in_cart_preview, parent, false);
        return new CartViewHolder(view, cartBookClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartModel cartModel = cartBooksList.get(position);
        holder.bind(cartModel, position);
    }

    @Override
    public int getItemCount() {
        return cartBooksList.size();
    }

    public BookModel getItem(int position) {
        try{
            return cartBooksList.get(position).getBook();
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<CartModel> getCartBooksList() {
        return cartBooksList;
    }

    public void setCartBooksList(List<CartModel> cartBooksList) {
        this.cartBooksList = cartBooksList;
    }

    public void removeAt(int position) {
        try{
            cartBooksList.remove(position);
            notifyItemRemoved(position);
        } catch(IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }
}
