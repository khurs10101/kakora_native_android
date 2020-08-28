package com.khurshid.kamkora.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khurshid.kamkora.R;
import com.khurshid.kamkora.model.SubService;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragmentSubServiceAdapter extends RecyclerView.Adapter<HomeFragmentSubServiceAdapter.SubServiceViewHolder> {

    private Context context;
    private List<SubService> subServiceList;

    @NonNull
    @Override
    public SubServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_item_sub_service, parent, false);
        return new SubServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubServiceViewHolder holder, int position) {

        SubService subService = subServiceList.get(position);
        String name = subService.getName();
        String url = subService.getUrl();

        if (name != null)
            holder.tv.setText(name);

        if (url != null)
            Glide.with(context)
                    .load(url)
                    .into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return subServiceList.size();
    }

    class SubServiceViewHolder extends RecyclerView.ViewHolder {

        CircleImageView iv;
        TextView tv;

        public SubServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_single_home_sub_service_image);
            tv = itemView.findViewById(R.id.tv_single_home_sub_service_title);
        }
    }
}
