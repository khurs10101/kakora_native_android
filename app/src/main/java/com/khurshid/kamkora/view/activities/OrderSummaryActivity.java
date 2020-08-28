package com.khurshid.kamkora.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.khurshid.kamkora.R;
import com.khurshid.kamkora.api.ApiClient;
import com.khurshid.kamkora.model.Address;
import com.khurshid.kamkora.model.CartModelRequest;
import com.khurshid.kamkora.model.CartModelResponse;
import com.khurshid.kamkora.utils.CentralData;
import com.khurshid.kamkora.utils.SessionManager;
import com.khurshid.kamkora.view.adapters.SummaryRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderSummaryActivity extends AppCompatActivity implements View.OnClickListener {

    private static String MYTAG = OrderSummaryActivity.class.getSimpleName();
    @BindView(R.id.rv_summary)
    RecyclerView rv;
    @BindView(R.id.tv_summary_order_address)
    TextView tvAddress;
    @BindView(R.id.bt_summary_cancel_order)
    Button btCancelOrder;
    @BindView(R.id.bt_summary_place_order)
    Button btPlaceOrder;
    private Address address;
    private SummaryRecyclerViewAdapter summaryRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        address = (Address) getIntent().getSerializableExtra("AddressObject");
        btPlaceOrder.setOnClickListener(this);
        btCancelOrder.setOnClickListener(this);
        if (address != null) {
            tvAddress.setText(address.getName() + "\n" + address.getAddressLine1() + "\n"
                    + address.getAddressLine2() + "\n"
                    + address.getCity() + "\n"
                    + address.getState()
                    + "\n" + address.getPincode());
        }
        initRecyclerView();
    }

    private void initRecyclerView() {
        summaryRecyclerViewAdapter = new SummaryRecyclerViewAdapter(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(summaryRecyclerViewAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_summary_place_order) {
            if (SessionManager.isLoggedIn(this)) {
                prepareParams();
            } else {
                Toast.makeText(this, "Please Log in", Toast.LENGTH_SHORT).show();
            }

        }

        if (v.getId() == R.id.bt_summary_cancel_order) {
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void prepareParams() {
        CartModelRequest cartModelRequest = new CartModelRequest();
        String userId = SessionManager.getLoggedInUserId(this);
        cartModelRequest.setOrders(CentralData.getCartList());
        Gson gson = new Gson();
        String jsonString = gson.toJson(cartModelRequest, CartModelRequest.class);
        Log.d(MYTAG, "Cart Order json: " + jsonString);

        Call<JsonObject> call = ApiClient
                .getInterface()
                .setCartOrder(userId, (JsonObject) JsonParser.parseString(jsonString));

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 200) {
                    Gson gson = new Gson();
                    CartModelResponse cartModelResponse = gson
                            .fromJson(response.body(), CartModelResponse.class);

                    Intent intent = new Intent(OrderSummaryActivity.this, OrderStatusActivity.class);
                    intent.putExtra("CartModelResponse", cartModelResponse);
                    startActivity(intent);
                    finish();
                }

                if (response.code() == 500) {

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(MYTAG, "Errors in order: " + t.getMessage());
            }
        });

    }
}