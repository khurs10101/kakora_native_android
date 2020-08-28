package com.khurshid.kamkora.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.khurshid.kamkora.R;
import com.khurshid.kamkora.api.ApiClient;
import com.khurshid.kamkora.model.Order;
import com.khurshid.kamkora.model.OrderSingleUserModelResponse;
import com.khurshid.kamkora.utils.ProgressBarManager;
import com.khurshid.kamkora.utils.SessionManager;
import com.khurshid.kamkora.view.adapters.BookingRecyclerViewAdapter;
import com.khurshid.kamkora.view.alertdialog.DialogInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingFragment extends Fragment {

    private static final String MYTAG = BookingFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private BookingRecyclerViewAdapter adapter;
    private List<Order> orderList;
    private ProgressBar pb;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_booking, container, false);
        recyclerView = v.findViewById(R.id.rv_bookings);
        pb = v.findViewById(R.id.pbBooking);
        swipeRefreshLayout = v.findViewById(R.id.sr_fragment_booking);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            getBookingDataFromServer();
        });
        getBookingDataFromServer();
    }

    private void getBookingDataFromServer() {
        if (SessionManager.isLoggedIn(getActivity())) {
            ProgressBarManager.startProgressBar(pb);
            String token = SessionManager.getToken(getActivity());
            String userId = SessionManager.getLoggedInUserId(getActivity());

            Call<JsonObject> call = ApiClient
                    .getInterface()
                    .getOrderOfUser(token, userId);

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    swipeRefreshLayout.setRefreshing(false);
                    ProgressBarManager.stopProgressBar(pb);
                    if (response.code() == 200) {
                        Gson gson = new Gson();
                        OrderSingleUserModelResponse orderSingleUserModelResponse;
                        orderSingleUserModelResponse = gson.fromJson(response.body(), OrderSingleUserModelResponse.class);
                        prepareRecyclerView(orderSingleUserModelResponse.getOrders());

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                    ProgressBarManager.stopProgressBar(pb);
                    Log.d(MYTAG, "Booking error: " + t.getMessage());
                }
            });
        }
    }

    private void prepareRecyclerView(List<Order> orders) {
        if (orders.size() == 0) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            DialogInfo dialogInfo = DialogInfo.newInstance("No Bookings Found");
            dialogInfo.show(fragmentManager, "DialogInfo");
        }
        adapter = new BookingRecyclerViewAdapter(orders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }
}
