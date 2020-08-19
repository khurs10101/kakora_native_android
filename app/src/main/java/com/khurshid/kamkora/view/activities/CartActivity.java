package com.khurshid.kamkora.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khurshid.kamkora.R;
import com.khurshid.kamkora.model.AddToCart;
import com.khurshid.kamkora.model.Order;
import com.khurshid.kamkora.utils.CentralData;
import com.khurshid.kamkora.view.adapters.CartRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String MYTAG = CartActivity.class.getSimpleName();
    @BindView(R.id.tv_cart_items)
    TextView tvCartItems;
    @BindView(R.id.tv_cart_amount)
    TextView tvCartAmount;
    @BindView(R.id.tv_cart_continue)
    TextView tvContinue;
    private AddToCart addToCart;
    private String addToCartJson;
    private RecyclerView rvCart;
    private CartRecyclerViewAdapter adapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        initView();


    }

    private void initView() {
        rvCart = findViewById(R.id.rvCart);
        tvContinue.setOnClickListener(this);
//        addToCartJson = SessionManager.getCartObjectJson(this);
//        if (addToCartJson != null) {
//            Gson gson = new Gson();
//            addToCart = gson.fromJson(addToCartJson, AddToCart.class);
//            initRecyclerView();
//        } else {
//            Log.d(MYTAG, "Cart json is empty");
//        }
        initRecyclerView();
        if (CentralData.getCartList() != null) {
            int size = CentralData.getCartList().size();
            int total = 0;
            for (Order order : CentralData.getCartList()) {
                total += Integer.parseInt(order.getRate());
            }

            tvCartItems.setText("Items: " + size);
            tvCartAmount.setText("Total: " + total);
        }
    }

    private void initRecyclerView() {
        Log.d(MYTAG, "Size of orders: " + CentralData.getCartList().size());
        adapter = new CartRecyclerViewAdapter(CentralData.getCartList());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        rvCart.setLayoutManager(manager);
        rvCart.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_cart_continue) {
            Intent intent = new Intent(this, SelectAddressActivity.class);
            startActivity(intent);
        }
    }
}