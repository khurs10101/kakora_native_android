package com.khurshid.kamkora.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.khurshid.kamkora.R;
import com.khurshid.kamkora.api.ApiClient;
import com.khurshid.kamkora.model.Address;
import com.khurshid.kamkora.model.AddressModelResponse;
import com.khurshid.kamkora.utils.SessionManager;
import com.khurshid.kamkora.view.adapters.SelectAddressRecyclerViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectAddressActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String MYTAG = SelectAddressActivity.class.getSimpleName();
    @BindView(R.id.rvAddressList)
    RecyclerView rv;
    @BindView(R.id.tv_add_new_address)
    TextView btAddNewAddress;

    private SelectAddressRecyclerViewAdapter addressRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        btAddNewAddress.setOnClickListener(this);
        networkCall();
    }

    private void networkCall() {
        if (SessionManager.isLoggedIn(this)) {
            String userId = SessionManager.getLoggedInUserId(this);
            String token = SessionManager.getToken(this);
            Call<JsonObject> call = ApiClient.getInterface().getAddressOfUser(token, userId);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.code() == 200) {
                        Gson gson = new Gson();
                        AddressModelResponse addressModelResponse;
                        addressModelResponse = gson.fromJson(response.body(), AddressModelResponse.class);
                        loadRecyclerView(addressModelResponse.getAddresses());
                    }

                    if (response.code() == 500) {
                        Log.e(MYTAG, "response is 500");
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.d(MYTAG, "Error: " + t.getMessage());
                }
            });
        }
    }

    private void loadRecyclerView(List<Address> addresses) {
        addressRecyclerViewAdapter = new SelectAddressRecyclerViewAdapter(this, addresses);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(addressRecyclerViewAdapter);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.tv_add_new_address) {
            Intent intent = new Intent(this, AddAddressActivity.class);
            startActivity(intent);
        }
    }
}