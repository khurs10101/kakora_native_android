package com.khurshid.kamkora.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.khurshid.kamkora.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotographySubMenuActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.lvBirthDayPhoto)
    LinearLayout lvBirthDayPhoto;
    @BindView(R.id.lvWeddingPhoto)
    LinearLayout lvWeddingPhoto;
    @BindView(R.id.lvCorporatePhoto)
    LinearLayout lvCorporatePhoto;
    @BindView(R.id.lvMoviePhoto)
    LinearLayout lvMoviePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photography_sub_menu);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        lvBirthDayPhoto.setOnClickListener(this);
        lvWeddingPhoto.setOnClickListener(this);
        lvCorporatePhoto.setOnClickListener(this);
        lvMoviePhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.lvBirthDayPhoto:
                intent = new Intent(this, ServiceInfoActivity.class);
                intent.putExtra("service", "Birthday photo");
                intent.putExtra("rate", "100");
                startActivity(intent);
                break;
            case R.id.lvWeddingPhoto:
                intent = new Intent(this, ServiceInfoActivity.class);
                intent.putExtra("service", "Wedding Photo");
                intent.putExtra("rate", "150");
                startActivity(intent);
                break;
            case R.id.lvCorporatePhoto:
                intent = new Intent(this, ServiceInfoActivity.class);
                intent.putExtra("service", "Corporate Photo");
                intent.putExtra("rate", "200");
                startActivity(intent);
                break;
            case R.id.lvMoviePhoto:
                intent = new Intent(this, ServiceInfoActivity.class);
                intent.putExtra("service", "Movie Photo");
                intent.putExtra("rate", "250");
                startActivity(intent);
                break;

        }
    }
}