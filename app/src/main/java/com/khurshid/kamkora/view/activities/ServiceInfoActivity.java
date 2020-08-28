package com.khurshid.kamkora.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.khurshid.kamkora.R;
import com.khurshid.kamkora.api.ApiClient;
import com.khurshid.kamkora.model.AddOrderModel;
import com.khurshid.kamkora.model.AddToCart;
import com.khurshid.kamkora.model.Order;
import com.khurshid.kamkora.model.SubService;
import com.khurshid.kamkora.utils.CentralData;
import com.khurshid.kamkora.utils.ProgressBarManager;
import com.khurshid.kamkora.utils.SessionManager;
import com.khurshid.kamkora.view.alertdialog.DialogInfo;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String MYTAG = ServiceInfoActivity.class.getSimpleName();
    private static ProgressBar pb;
    @BindView(R.id.tvServiceName)
    TextView tvTitle;
    @BindView(R.id.tvServiceRate)
    TextView tvRate;
    @BindView(R.id.tv_service_order)
    TextView tvOrderService;
    @BindView(R.id.tv_service_add_to_cart)
    TextView tvAddToCart;
    @BindView(R.id.tv_service_cart_count)
    TextView tvCartCount;
    @BindView(R.id.rl_add_cart)
    RelativeLayout rlAddToCart;
    @BindView(R.id.iv_service_info_image)
    ImageView ivServiceInfoImage;
    @BindView(R.id.iv_service_info_service_image)
    ImageView ivSubServiceImage;
    private String stService, stRate, stUserId, stToken;

    private AddToCart addToCart;
    private SubService subService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_info);
        ButterKnife.bind(this);
        subService = (SubService) getIntent().getSerializableExtra("subServiceObject");
        initView();
        fakeNetworkCall();

    }

    private void fakeNetworkCall() {

    }

    private void initView() {
//        stService = getIntent().getStringExtra("service");
//        stRate = getIntent().getStringExtra("rate");
        pb = findViewById(R.id.pbOrder);
        if (subService != null) {
            tvTitle.setText(subService.getName());
            tvRate.setText(subService.getRate() + " per hour");
            ivServiceInfoImage.setImageResource(subService.getSampleImageCarousel());
            ivSubServiceImage.setImageResource(subService.getSampleImage());
        }


        if (CentralData.getCartList() != null) {
            tvCartCount.setText(String.valueOf(CentralData.getCartList().size()));
        }
        tvOrderService.setOnClickListener(this);
        tvAddToCart.setOnClickListener(this);
        rlAddToCart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_service_order) {
            if (!SessionManager.isLoggedIn(this)) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
//                prepareParams();
//                Intent intent = new Intent(this, OrderStatusActivity.class);
//                intent.putExtra("service", stService);
//                intent.putExtra("rate", stRate);
//                startActivity(intent);
                if (CentralData.getCartList() == null) {
                    Toast.makeText(this, "Cart empty, null", Toast.LENGTH_SHORT).show();
                } else if (CentralData.getCartList().size() == 0) {
                    Toast.makeText(this, "Cart empty", Toast.LENGTH_SHORT).show();
                } else {
                    prepareParams();
                }
            }
        }

        if (v.getId() == R.id.tv_service_add_to_cart) {
            Order order = new Order();
            order.setSubServiceName(subService.getName());
            order.setRate(subService.getRate());
            order.setServiceId(subService.getServiceId());
            order.setSubServiceId(subService.getSubServiceId());
            order.setSampleImage(subService.getSampleImage());
            order.setStatus("pending");
            if (CentralData.getCartList().contains(order)) {
                Toast.makeText(this, "Service already added to cart", Toast.LENGTH_SHORT).show();
            } else {
                CentralData.getCartList().add(order);
                int cartSize = CentralData.getCartList().size();
                tvCartCount.setText(String.valueOf(cartSize));
                showInfoDialog("Item Added To Cart");
                Log.d(MYTAG, "Cart item count: " + cartSize);
            }
        }

        if (v.getId() == R.id.rl_add_cart) {

            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);

        }
    }

    private void showInfoDialog(String message) {
        FragmentManager manager = getSupportFragmentManager();
        DialogInfo dialogInfo = DialogInfo.newInstance(message);
        dialogInfo.show(manager, "DialogInfo");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCartCount();
    }

    private void getCartCount() {
        int cartSize = CentralData.getCartList().size();
        tvCartCount.setText(String.valueOf(cartSize));
        Log.d(MYTAG, "Cart item count: " + cartSize);
    }

    private void prepareParams() {
        stUserId = SessionManager.getLoggedInUserId(this);
        if (stUserId != null) {
            Intent intent = new Intent(this, CartActivity.class);
//            intent.putExtra("subServicrObject", subService);
            startActivity(intent);
            finish();
//            JSONObject object = new JSONObject();
//            try {
//                object.put("userId", stUserId);
//                object.put("serviceId", stService);
//                object.put("rate", stRate);
//                object.put("status", "pending");
//                networkCall(object);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            Gson gson = new Gson();
//            AddToCart addToCart = new AddToCart();
//            addToCart.setOrders(CentralData.getCartList());
//            String cartJson = gson.toJson(addToCart, AddToCart.class);
//            Log.d(MYTAG, "Cart Json: " + cartJson);

        } else {
            Toast.makeText(this, "invalid token", Toast.LENGTH_SHORT).show();
        }
    }

    private void networkCall(JSONObject object) {

        tvOrderService.setVisibility(View.GONE);
        ProgressBarManager.startProgressBar(pb);

        Call<JsonObject> call = ApiClient
                .getInterface()
                .setOrder(stUserId, (JsonObject) JsonParser.parseString(object.toString()));

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                ProgressBarManager.stopProgressBar(pb);
                tvOrderService.setVisibility(View.VISIBLE);
                if (response.code() == 201) {
                    Log.d(MYTAG, "order response: " + response.body());
                    Gson gson = new Gson();
                    AddOrderModel addOrderModel = gson.fromJson(response.body(), AddOrderModel.class);
                    Order order = addOrderModel.getOrder();
                    SessionManager.setOrderObjectJson(ServiceInfoActivity.this, order);
                    Intent intent = new Intent(ServiceInfoActivity.this, OrderStatusActivity.class);
                    startActivity(intent);
                    finish();
                }

                if (response.code() == 500) {
                    Toast.makeText(ServiceInfoActivity.this, "server error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                ProgressBarManager.stopProgressBar(pb);
                tvOrderService.setVisibility(View.VISIBLE);
                Log.d(MYTAG, "order error: " + t.getMessage());
                Toast.makeText(ServiceInfoActivity.this, "internel error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}