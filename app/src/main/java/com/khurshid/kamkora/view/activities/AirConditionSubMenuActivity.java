package com.khurshid.kamkora.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.khurshid.kamkora.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AirConditionSubMenuActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.lvAcRepair)
    LinearLayout lvAcRepair;
    @BindView(R.id.lvAcInstall)
    LinearLayout lvAcInstall;
    @BindView(R.id.lvFanRepair)
    LinearLayout lvFanRepair;
    @BindView(R.id.lvAcServicing)
    LinearLayout lvAcServicing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_condition_sub_menu);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        lvAcInstall.setOnClickListener(this);
        lvAcRepair.setOnClickListener(this);
        lvFanRepair.setOnClickListener(this);
        lvAcServicing.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.lvAcRepair:
                intent = new Intent(this, ServiceInfoActivity.class);
                intent.putExtra("service", "Ac Repair");
                intent.putExtra("rate", "100");
                startActivity(intent);
                break;
            case R.id.lvAcInstall:
                intent = new Intent(this, ServiceInfoActivity.class);
                intent.putExtra("service", "Ac Install");
                intent.putExtra("rate", "150");
                startActivity(intent);
                break;
            case R.id.lvFanRepair:
                intent = new Intent(this, ServiceInfoActivity.class);
                intent.putExtra("service", "Fan Repair");
                intent.putExtra("rate", "200");
                startActivity(intent);
                break;
            case R.id.lvAcServicing:
                intent = new Intent(this, ServiceInfoActivity.class);
                intent.putExtra("service", "Ac Servicing");
                intent.putExtra("rate", "250");
                startActivity(intent);
                break;

        }
    }
}