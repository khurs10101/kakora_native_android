package com.khurshid.kamkora.view.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.khurshid.kamkora.R;
import com.khurshid.kamkora.model.CartModelResponse;
import com.khurshid.kamkora.model.Order;
import com.khurshid.kamkora.model.Results;
import com.khurshid.kamkora.utils.SessionManager;
import com.kofigyan.stateprogressbar.StateProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderStatusActivity extends AppCompatActivity {

    @BindView(R.id.tv_order_docket_id)
    TextView tvDocketId;
    @BindView(R.id.tv_order_userid)
    TextView tvUserId;
    @BindView(R.id.tv_order_service_id)
    TextView tvServiceId;
    @BindView(R.id.tv_order_subcategory)
    TextView tvSubCategoryId;
    @BindView(R.id.tv_order_subtotal)
    TextView tvSubTotal;
    @BindView(R.id.spbTimeView)
    StateProgressBar spbTimeView;

    private Order order;
    private String stObjectJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        stObjectJson = SessionManager.getOrderobjectJson(this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String[] descriptionData = {"Pending", "Ready", "Delivered", "Cancelled"};

        CartModelResponse cartModelResponse = (CartModelResponse) getIntent()
                .getSerializableExtra("CartModelResponse");

        if (cartModelResponse != null) {
            Results results = cartModelResponse.getResults();
            if (results != null) {
                if (results.getDocketId() != null)
                    tvDocketId.setText("Docket Id: " + results.getDocketId());
//                if (order.getServiceId() != null)
//                    tvServiceId.setText(order.getServiceId());
//                if (order.getSubCategory() != null)
//                    tvSubCategoryId.setText(order.getSubCategory());
//                if (order.getRate() != null)
//                    tvSubTotal.setText(order.getRate());

            }
        }

        if (stObjectJson != null) {
            Gson gson = new Gson();


        }
        spbTimeView.setStateDescriptionData(descriptionData);
        spbTimeView.checkStateCompleted(true);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}