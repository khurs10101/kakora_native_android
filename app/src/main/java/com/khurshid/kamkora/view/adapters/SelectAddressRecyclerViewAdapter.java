package com.khurshid.kamkora.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khurshid.kamkora.R;
import com.khurshid.kamkora.model.Address;
import com.khurshid.kamkora.view.activities.OrderSummaryActivity;

import java.util.ArrayList;
import java.util.List;

public class SelectAddressRecyclerViewAdapter extends RecyclerView.Adapter<SelectAddressRecyclerViewAdapter.MyViewHolder> {

    Context context;
    private List<Address> addresses = new ArrayList<>();

    public SelectAddressRecyclerViewAdapter(Context context, List<Address> addresses) {
        this.addresses = addresses;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.single_item_address, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Address address = addresses.get(position);
        holder.tvSingleFullAddress.setText(address.getCity() + "\n"
                + address.getState() + "\n"
                + address.getPincode());
        holder.btSelect.setOnClickListener(v -> {
            Intent intent = new Intent(context, OrderSummaryActivity.class);
            intent.putExtra("AddressObject", address);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvSingleAddressName, tvSingleFullAddress;
        Button btSelect;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSingleAddressName = itemView.findViewById(R.id.tv_single_address_name);
            tvSingleFullAddress = itemView.findViewById(R.id.tv_single_full_address);
            btSelect = itemView.findViewById(R.id.bt_single_address_select);

        }

    }
}
