package com.khurshid.kamkora.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khurshid.kamkora.R;
import com.khurshid.kamkora.model.SubService;
import com.khurshid.kamkora.view.activities.ServiceInfoActivity;

import java.util.ArrayList;
import java.util.List;

public class SubMenuElectricianRecyclerAdapter extends RecyclerView.Adapter<SubMenuElectricianRecyclerAdapter.ElectricianSubMenuViewHolder> {

    private Context context;
    private List<SubService> subServiceList = new ArrayList<>();

    public SubMenuElectricianRecyclerAdapter(Context context, List<SubService> subServiceList) {
        this.context = context;
        this.subServiceList = subServiceList;
    }

    @NonNull
    @Override
    public ElectricianSubMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.single_item_sub_service, parent, false);
        return new ElectricianSubMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ElectricianSubMenuViewHolder holder, int position) {
        SubService subService = subServiceList.get(position);
        holder.iv.setImageResource(subService.getSampleImage());
        holder.tv.setText(subService.getName());
    }

    @Override
    public int getItemCount() {
        return subServiceList.size();
    }

    class ElectricianSubMenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iv;
        TextView tv;

        public ElectricianSubMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_single_home_sub_service_image);
            tv = itemView.findViewById(R.id.tv_single_home_sub_service_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            SubService subService = subServiceList.get(getAdapterPosition());
            Intent intent = new Intent(context, ServiceInfoActivity.class);
            intent.putExtra("subServiceObject", subService);
            context.startActivity(intent);
        }
    }
}
