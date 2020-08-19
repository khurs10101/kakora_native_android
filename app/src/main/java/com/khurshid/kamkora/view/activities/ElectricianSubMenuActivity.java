package com.khurshid.kamkora.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.khurshid.kamkora.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ElectricianSubMenuActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.lvSwitchRepair)
    LinearLayout lvSwitchRepair;
    @BindView(R.id.lvSolarPanelInstall)
    LinearLayout lvSolarPanelInstall;
    @BindView(R.id.lvHomeAppliancesRepair)
    LinearLayout lvHomeAppliancesRepair;
    @BindView(R.id.lvHomeWiringRepair)
    LinearLayout lvHomeWiringRepair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrician_sub_menu);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        lvSwitchRepair.setOnClickListener(this);
        lvSolarPanelInstall.setOnClickListener(this);
        lvHomeAppliancesRepair.setOnClickListener(this);
        lvHomeWiringRepair.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.lvSwitchRepair:
                intent = new Intent(this, ServiceInfoActivity.class);
                intent.putExtra("service", "Switch Repair");
                intent.putExtra("rate", "100");
                startActivity(intent);
                break;
            case R.id.lvSolarPanelInstall:
                intent = new Intent(this, ServiceInfoActivity.class);
                intent.putExtra("service", "Solar panel Install");
                intent.putExtra("rate", "150");
                startActivity(intent);
                break;
            case R.id.lvHomeAppliancesRepair:
                intent = new Intent(this, ServiceInfoActivity.class);
                intent.putExtra("service", "Home appliances Repair");
                intent.putExtra("rate", "200");
                startActivity(intent);
                break;
            case R.id.lvHomeWiringRepair:
                intent = new Intent(this, ServiceInfoActivity.class);
                intent.putExtra("service", "Home wiring");
                intent.putExtra("rate", "250");
                startActivity(intent);
                break;

        }
    }
}