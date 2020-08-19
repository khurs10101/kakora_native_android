package com.khurshid.kamkora.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khurshid.kamkora.R;
import com.khurshid.kamkora.model.Order;

import java.util.List;

public class BookingRecyclerViewAdapter extends RecyclerView.Adapter<BookingRecyclerViewAdapter.MyViewHolder> {


    private List<Order> orders;

    public BookingRecyclerViewAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.single_item_booking, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String stDocketId = orders.get(position).getDocketId();
        String stService = orders.get(position).getServiceId();
        String stRate = orders.get(position).getRate();
        String stStatus = orders.get(position).getStatus();

        if (stDocketId != null) {
            holder.tvDocketId.setText("Docket ID: " + stDocketId);
        }

        if (stService != null) {
            holder.tvServiceName.setText(stService);
        }

        if (stRate != null) {
            holder.tvRate.setText("Rate: " + stRate + "/hour");
        }

        if (stStatus != null) {
            holder.tvStatus.setText("Order status: " + stStatus);
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvDocketId, tvServiceName, tvRate, tvStatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDocketId = itemView.findViewById(R.id.tv_booking_single_item_docket_id);
            tvServiceName = itemView.findViewById(R.id.tv_booking_single_item_service);
            tvRate = itemView.findViewById(R.id.tv_booking_single_item_rate);
            tvStatus = itemView.findViewById(R.id.tv_booking_single_item_status);
        }
    }
}
