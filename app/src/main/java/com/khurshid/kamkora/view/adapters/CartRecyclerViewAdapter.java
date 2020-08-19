package com.khurshid.kamkora.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khurshid.kamkora.R;
import com.khurshid.kamkora.model.Order;

import java.util.ArrayList;
import java.util.List;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.MyViewHolder> {

    private static List<Order> orders = new ArrayList<>();

    public CartRecyclerViewAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.single_item_cart, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvService.setText(orders.get(position).getServiceId());
        holder.tvRate.setText(orders.get(position).getRate());
        holder.ivRemove.setOnClickListener(v -> {
            orders.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvService, tvRate;
        ImageView ivRemove;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvService = itemView.findViewById(R.id.tv_cart_single_item_service);
            tvRate = itemView.findViewById(R.id.tv_cart_single_item_rate);
            ivRemove = itemView.findViewById(R.id.tv_cart_single_item_delete);
        }
    }
}
