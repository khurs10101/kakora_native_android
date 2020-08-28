package com.khurshid.kamkora.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khurshid.kamkora.R;
import com.khurshid.kamkora.model.Order;
import com.khurshid.kamkora.utils.CentralData;

import java.util.ArrayList;
import java.util.List;

public class SummaryRecyclerViewAdapter extends RecyclerView.Adapter<SummaryRecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private List<Order> orderList = new ArrayList<>();

    public SummaryRecyclerViewAdapter(Context context) {
        this.context = context;
        if (CentralData.getCartList() != null) {
            orderList = CentralData.getCartList();
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.single_item_summary, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvService.setText(orderList.get(position).getServiceId());
        holder.tvRate.setText("Rate: Rs. " + orderList.get(position).getRate() + " per hour");
        holder.ivServiceImage.setImageResource(orderList.get(position).getSampleImage());

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvService, tvRate;
        ImageView ivServiceImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvService = itemView.findViewById(R.id.tv_summary_single_item_service);
            tvRate = itemView.findViewById(R.id.tv_summary_single_item_rate);
            ivServiceImage = itemView.findViewById(R.id.iv_single_summary_image);
        }
    }
}
