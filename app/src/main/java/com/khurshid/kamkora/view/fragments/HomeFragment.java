package com.khurshid.kamkora.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khurshid.kamkora.R;
import com.khurshid.kamkora.utils.CentralData;
import com.khurshid.kamkora.view.activities.AirConditionSubMenuActivity;
import com.khurshid.kamkora.view.activities.CartActivity;
import com.khurshid.kamkora.view.activities.ElectricianSubMenuActivity;
import com.khurshid.kamkora.view.activities.MapLocationPickerActivity;
import com.khurshid.kamkora.view.activities.PestControlSubMenuActivity;
import com.khurshid.kamkora.view.activities.PhotographySubMenuActivity;
import com.khurshid.kamkora.view.adapters.AdRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {

    public static final String MYTAG = HomeFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private AdRecyclerViewAdapter adRecyclerViewAdapter;
    private LinearLayout lvAcRepairMenu, lvPhotographyMenu, lvPestControlMenu, lvElectricianMenu;
    private TextView tvSelectLocation, tvHomeCartCount;
    private int PLACE_PICKER_REQUEST_CODE = 1;
    private String currentLocation;
    private List<String> currentLocationSubList;
    private RelativeLayout rlAddToCart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        initView(v);
        adRecyclerViewAdapter = new AdRecyclerViewAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adRecyclerViewAdapter);
        return v;
    }

    private void initView(View v) {
        recyclerView = v.findViewById(R.id.rvAdsRecyclerView);
        lvAcRepairMenu = v.findViewById(R.id.lvAcRepairMenu);
        lvElectricianMenu = v.findViewById(R.id.lvElectricianMenu);
        lvPhotographyMenu = v.findViewById(R.id.lvPhotographyMenu);
        lvPestControlMenu = v.findViewById(R.id.lvPestControlMenu);
        tvSelectLocation = v.findViewById(R.id.tvHomeFragmentSelectLocation);
        rlAddToCart = v.findViewById(R.id.rl_home_add_cart);
        tvHomeCartCount = v.findViewById(R.id.tv_home_cart_count);
        lvAcRepairMenu.setOnClickListener(this);
        lvPhotographyMenu.setOnClickListener(this);
        lvPestControlMenu.setOnClickListener(this);
        lvElectricianMenu.setOnClickListener(this);
        tvSelectLocation.setOnClickListener(this);
        rlAddToCart.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (CentralData.getCartList() != null) {
            tvHomeCartCount.setText(String.valueOf(CentralData.getCartList().size()));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.lvAcRepairMenu) {
            Intent intent = new Intent(getActivity(), AirConditionSubMenuActivity.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.lvElectricianMenu) {
            Intent intent = new Intent(getActivity(), ElectricianSubMenuActivity.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.lvPhotographyMenu) {
            Intent intent = new Intent(getActivity(), PhotographySubMenuActivity.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.lvPestControlMenu) {
            Intent intent = new Intent(getActivity(), PestControlSubMenuActivity.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.tvHomeFragmentSelectLocation) {
            Intent intent = new Intent(getActivity(), MapLocationPickerActivity.class);
            startActivityForResult(intent, PLACE_PICKER_REQUEST_CODE);
        }

        if (v.getId() == R.id.rl_home_add_cart) {
            Intent intent = new Intent(getActivity(), CartActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(MYTAG, "On Activity result called: ReqCode->" + requestCode + " ResCode->" + resultCode);
        if (requestCode == PLACE_PICKER_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                currentLocation = data.getStringExtra("getLocation");
                Log.d(MYTAG, "Current Location: " + currentLocation);
                if (currentLocation != null) {
                    currentLocationSubList = new ArrayList<>();
                    for (String m : TextUtils.split(currentLocation, ",")) {
                        Log.d(MYTAG, m);
                        currentLocationSubList.add(m);
                    }

                    String newLoc = "";

                    for (String m : currentLocationSubList.subList(currentLocationSubList.size() - 3, currentLocationSubList.size() - 1)) {
                        newLoc += m + " ";
                    }

                    tvSelectLocation.setText(newLoc);
                }
            }
        }
    }
}
