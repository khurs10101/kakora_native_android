package com.khurshid.kamkora.view.adapters;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.khurshid.kamkora.R;

import java.util.ArrayList;
import java.util.List;


public class AdRecyclerViewAdapter extends RecyclerView.Adapter<AdRecyclerViewAdapter.MyViewHolder> {

    private static final String MYTAG = AdRecyclerViewAdapter.class.getSimpleName();
    List<Integer> myList = new ArrayList<>();
    private double screenWidth;
    private Context context;

    public AdRecyclerViewAdapter(Context context) {
        this.context = context;

        myList.add(R.drawable.ads1);
        myList.add(R.drawable.ads2);
        myList.add(R.drawable.ads3);

        Log.d(MYTAG, "AdsRecycler is called");

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater inflater = LayoutInflater.from(context);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;


        View v = inflater.inflate(R.layout.scroll_ads_single, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        double itemWidth = screenWidth / 1.2;
        ViewGroup.LayoutParams lp = holder.cardView.getLayoutParams();
        lp.height = lp.height;
        lp.width = (int) itemWidth;
        holder.itemView.setLayoutParams(lp);
        holder.ivAds.setImageResource(myList.get(position));
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivAds;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAds = itemView.findViewById(R.id.ivAds);
            cardView = itemView.findViewById(R.id.cv_scroll_ads_single_cardView);
        }
    }
}
