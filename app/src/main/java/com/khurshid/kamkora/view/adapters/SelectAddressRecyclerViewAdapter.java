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
        View v = inflater.inflate(R.layout.single_item_address, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Address address = addresses.get(position);
//        holder.tvSingleFullAddress.setText(address.getCity() + "\n"
//                + address.getState() + "\n"
//                + address.getPincode());
//        holder.btSelect.setOnClickListener(v -> {
//            Intent intent = new Intent(context, OrderSummaryActivity.class);
//            intent.putExtra("AddressObject", address);
//            context.startActivity(intent);

//    });

        String name, addressLine1, addressLine2, city, state, pincode;
        if (address.getName() != null)
            name = address.getName();
        else
            name = "Name not defined";
        if (address.getAddressLine1() != null)
            addressLine1 = address.getAddressLine1();
        else
            addressLine1 = "AddressLine1 not defined";
        if (address.getAddressLine2() != null)
            addressLine2 = address.getAddressLine2();
        else
            addressLine2 = "AddressLine2 not defined";

        if (address.getCity() != null)
            city = address.getCity();
        else
            city = "City not defined";

        if (address.getState() != null)
            state = address.getState();
        else
            state = "State not defined";

        if (address.getPincode() != null)
            pincode = address.getPincode();
        else
            pincode = "Pincode not defined";

        holder.tvAddressFull.setText(name + "\n" + addressLine1 + "\n" + addressLine2 + "\n" +
                city + "\n" + state + "\n" + pincode);

    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvAddressFull;
        Button btSelect;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAddressFull = itemView.findViewById(R.id.tv_single_address_full);
            itemView.setOnClickListener(this);
//            tvSingleAddressName = itemView.findViewById(R.id.tv_single_address_name);
//            tvSingleFullAddress = itemView.findViewById(R.id.tv_single_full_address);
//            btSelect = itemView.findViewById(R.id.bt_single_address_select);

        }

        @Override
        public void onClick(View v) {
            Address address = addresses.get(getAdapterPosition());
            Intent intent = new Intent(context, OrderSummaryActivity.class);
            intent.putExtra("AddressObject", address);
            context.startActivity(intent);
        }
    }
}
