package com.khurshid.kamkora.view.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.khurshid.kamkora.R;
import com.khurshid.kamkora.model.User;
import com.khurshid.kamkora.utils.Constants;
import com.khurshid.kamkora.view.fragments.BookingFragment;
import com.khurshid.kamkora.view.fragments.HomeFragment;
import com.khurshid.kamkora.view.fragments.ProfileFragment;
import com.khurshid.kamkora.view.fragments.RewardsFragment;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private BottomNavigationView bottomNavigationView;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String isLogin;
    private User currentUser;
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.USEROBJECT, currentUser);
            switch (item.getItemId()) {
                case R.id.menu_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.menu_booking:
                    selectedFragment = new BookingFragment();

                    break;
                case R.id.menu_rewards:
                    selectedFragment = new RewardsFragment();
                    break;

                case R.id.menu_profile:
                    selectedFragment = new ProfileFragment();
                    selectedFragment.setArguments(bundle);
                    break;

            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();

            return true;
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard2);
        currentUser = (User) getIntent().getSerializableExtra(Constants.USEROBJECT);
        initViews();


        //initially open the home fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();
    }

    private void initViews() {

        sharedPreferences = getSharedPreferences(Constants.USERCREDS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        isLogin = sharedPreferences.getString("username", null);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

    }

    @Override
    public void onClick(View v) {

    }
}
