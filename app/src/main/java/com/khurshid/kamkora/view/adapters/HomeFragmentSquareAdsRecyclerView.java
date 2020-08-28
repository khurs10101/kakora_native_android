package com.khurshid.kamkora.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.khurshid.kamkora.R;
import com.khurshid.kamkora.model.Ads;

import java.util.List;

public class HomeFragmentSquareAdsRecyclerView extends RecyclerView.Adapter<HomeFragmentSquareAdsRecyclerView.SquareAdsViewHolder> {

    private Context context;
    private List<Ads> adsList;

    public HomeFragmentSquareAdsRecyclerView(Context context, List<Ads> adsList) {
        this.context = context;
        this.adsList = adsList;
    }

    @NonNull
    @Override
    public SquareAdsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_item_square_ads, parent, false);
        return new SquareAdsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SquareAdsViewHolder holder, int position) {
        Ads ads = adsList.get(position);
//        Glide.with(context)
//                .load(ads.getUrl())
//                .into(holder.iv);
        holder.iv.setImageResource(ads.getSampleImage());

    }

    @Override
    public int getItemCount() {
        return adsList.size();
    }

    class SquareAdsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iv;

        public SquareAdsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            iv = itemView.findViewById(R.id.iv_single_home_square_ads_image);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
