package com.khurshid.kamkora.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.khurshid.kamkora.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PestControlSubMenuActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.lvFlyControl)
    LinearLayout lvFlyControl;
    @BindView(R.id.lvMosquitoControl)
    LinearLayout lvMosquitoControl;
    @BindView(R.id.lvBedBugControl)
    LinearLayout lvBedBugControl;
    @BindView(R.id.lvTermiteControl)
    LinearLayout lvTermiteControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pest_control_sub_menu);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        lvFlyControl.setOnClickListener(this);
        lvMosquitoControl.setOnClickListener(this);
        lvBedBugControl.setOnClickListener(this);
        lvTermiteControl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.lvFlyControl:
                intent = new Intent(this, ServiceInfoActivity.class);
                intent.putExtra("service", "Fly control");
                intent.putExtra("rate", "100");
                startActivity(intent);
                break;
            case R.id.lvMosquitoControl:
                intent = new Intent(this, ServiceInfoActivity.class);
                intent.putExtra("service", "Mosquito control");
                intent.putExtra("rate", "150");
                startActivity(intent);
                break;
            case R.id.lvBedBugControl:
                intent = new Intent(this, ServiceInfoActivity.class);
                intent.putExtra("service", "Bedbug control");
                intent.putExtra("rate", "200");
                startActivity(intent);
                break;
            case R.id.lvTermiteControl:
                intent = new Intent(this, ServiceInfoActivity.class);
                intent.putExtra("service", "Termite control");
                intent.putExtra("rate", "250");
                startActivity(intent);
                break;

        }
    }
}