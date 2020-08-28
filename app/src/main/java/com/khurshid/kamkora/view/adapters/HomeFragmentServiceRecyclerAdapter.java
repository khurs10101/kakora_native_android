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
import com.khurshid.kamkora.model.Service;
import com.khurshid.kamkora.view.activities.ElectricianSubMenuActivity;

import java.util.List;

public class HomeFragmentServiceRecyclerAdapter extends RecyclerView.Adapter<HomeFragmentServiceRecyclerAdapter.MyViewHolder> {


    private List<Service> serviceList;
    private Context context;


    public HomeFragmentServiceRecyclerAdapter(Context context, List<Service> serviceList) {
        this.serviceList = serviceList;
        this.context = context;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_item_service, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Service service = serviceList.get(position);
        String name = service.getName();
        String url = service.getUrl();

        if (name != null)
            holder.tv.setText(name);

//        if (url != null)
//            Glide.with(context)
//                    .load(ApiClient.Base_URL + "/" + url)
//                    .into(holder.iv);

//        if (url != null)
//            GlideToVectorYou
//                    .init()
//                    .with(context)
//                    .load(Uri.parse(url), holder.iv);

        holder.iv.setImageResource(service.getSampleImage());

    }

    @Override
    public int getItemCount() {
        if (serviceList != null)
            return serviceList.size();
        return 4;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView iv;
        TextView tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv_single_home_service_image);
            tv = itemView.findViewById(R.id.tv_single_home_service_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Service service = serviceList.get(getAdapterPosition());
            if (Integer.parseInt(service.getServiceId()) == 10) {
                Intent intent = new Intent(context, ElectricianSubMenuActivity.class);
                intent.putExtra("serviceId", service.getServiceId());
                context.startActivity(intent);
            }

            if (Integer.parseInt(service.getServiceId()) == 20) {
                Intent intent = new Intent(context, ElectricianSubMenuActivity.class);
                intent.putExtra("serviceId", service.getServiceId());
                context.startActivity(intent);
            }

            if (Integer.parseInt(service.getServiceId()) == 30) {
                Intent intent = new Intent(context, ElectricianSubMenuActivity.class);
                intent.putExtra("serviceId", service.getServiceId());
                context.startActivity(intent);
            }

            if (Integer.parseInt(service.getServiceId()) == 40) {


            }
        }
    }
}
