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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.khurshid.kamkora.R;
import com.khurshid.kamkora.api.ApiClient;
import com.khurshid.kamkora.model.Ads;
import com.khurshid.kamkora.model.AllServiceResponseModel;
import com.khurshid.kamkora.model.Service;
import com.khurshid.kamkora.utils.CentralData;
import com.khurshid.kamkora.utils.SessionManager;
import com.khurshid.kamkora.view.activities.CartActivity;
import com.khurshid.kamkora.view.activities.MapLocationPickerActivity;
import com.khurshid.kamkora.view.adapters.AdRecyclerViewAdapter;
import com.khurshid.kamkora.view.adapters.HomeFragmentServiceRecyclerAdapter;
import com.khurshid.kamkora.view.adapters.HomeFragmentSquareAdsRecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener {

    public static final String MYTAG = HomeFragment.class.getSimpleName();
    private RecyclerView recyclerView, rvServices, rvSquareAds;
    private AdRecyclerViewAdapter adRecyclerViewAdapter;
    private HomeFragmentServiceRecyclerAdapter homeFragmentServiceRecyclerAdapter;
    private LinearLayout lvAcRepairMenu, lvPhotographyMenu, lvPestControlMenu, lvElectricianMenu;
    private TextView tvSelectLocation, tvHomeCartCount;
    private int PLACE_PICKER_REQUEST_CODE = 1;
    private String currentLocation;
    private List<String> currentLocationSubList;
    private RelativeLayout rlAddToCart;
    private List<Service> serviceList;
    private List<Ads> adsList;
    private List<Ads> squareAdsList;
    private HomeFragmentSquareAdsRecyclerView homeFragmentSquareAdsRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        recyclerView = v.findViewById(R.id.rvAdsRecyclerView);
        rvSquareAds = v.findViewById(R.id.rv_square_ads);
        rvServices = v.findViewById(R.id.rv_home_service);
//        lvAcRepairMenu = v.findViewById(R.id.lvAcRepairMenu);
//        lvElectricianMenu = v.findViewById(R.id.lvElectricianMenu);
//        lvPhotographyMenu = v.findViewById(R.id.lvPhotographyMenu);
//        lvPestControlMenu = v.findViewById(R.id.lvPestControlMenu);
        tvSelectLocation = v.findViewById(R.id.tvHomeFragmentSelectLocation);
        rlAddToCart = v.findViewById(R.id.rl_home_add_cart);
        tvHomeCartCount = v.findViewById(R.id.tv_home_cart_count);
//        lvAcRepairMenu.setOnClickListener(this);
//        lvPhotographyMenu.setOnClickListener(this);
//        lvPestControlMenu.setOnClickListener(this);
//        lvElectricianMenu.setOnClickListener(this);
        tvSelectLocation.setOnClickListener(this);
        rlAddToCart.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (CentralData.getCartList() != null) {
            tvHomeCartCount.setText(String.valueOf(CentralData.getCartList().size()));
        }
//        if (SessionManager.isLoggedIn(getActivity())) {
////            prepareNetworkCall();
//            fakeNetworkCall();
//        }

        fakeNetworkCall();
    }

    @Override
    public void onResume() {
        super.onResume();
        getCartCount();
    }

    private void getCartCount() {
        int cartSize = CentralData.getCartList().size();
        tvHomeCartCount.setText(String.valueOf(cartSize));
        Log.d(MYTAG, "Cart item count: " + cartSize);
    }

    private void fakeNetworkCall() {
        //service
        serviceList = new ArrayList<>();
        String[] staNames = {"electrician", "photography", "pest control", "plumber"};
        int[] intServiceId = {10, 20, 30, 40};
        int[] staImages = {R.drawable.ic_electrician, R.drawable.ic_photo, R.drawable.ic_mosquito, R.drawable.ic_plumber};
        for (int i = 0; i < staImages.length; i++) {
            Service service = new Service();
            service.setName(staNames[i]);
            service.setSampleImage(staImages[i]);
            service.setServiceId(String.valueOf(intServiceId[i]));
            serviceList.add(service);
        }

        //ads
        adsList = new ArrayList<>();
        int[] staAdsImage = {R.drawable.ads1, R.drawable.ads2, R.drawable.ads3,
                R.drawable.omg, R.drawable.square_demo5, R.drawable.square_demo3, R.drawable.square_demo4,
                R.drawable.ads1, R.drawable.ads2, R.drawable.ads3};

        String[] adType = {"banner", "banner", "banner",
                "square", "square", "square", "square",
                "banner", "banner", "banner"};

        for (int i = 0; i < staAdsImage.length; i++) {
            Ads ads = new Ads();
            ads.setAdsType(adType[i]);
            ads.setSampleImage(staAdsImage[i]);
            adsList.add(ads);
        }


        startSampleRecyclerView();

    }

    private void startSampleRecyclerView() {
        List<Ads> bannerAdsList = new ArrayList<>();
        List<Ads> squareAdsList = new ArrayList<>();
        for (Ads ads : adsList) {
            if (ads.getAdsType() == "banner") {
                bannerAdsList.add(ads);
            } else if (ads.getAdsType() == "square") {
                squareAdsList.add(ads);
            }
        }

//        squareAdsList = new ArrayList<>();
//        for (Ads ads : adsList) {
//            if (ads.getAdsType() == "square") {
//                adsList.add(ads);
//            }
//        }

        homeFragmentSquareAdsRecyclerView = new HomeFragmentSquareAdsRecyclerView(getActivity(), squareAdsList);
        rvSquareAds.setHasFixedSize(true);
        rvSquareAds.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));
        rvSquareAds.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        rvSquareAds.setLayoutManager(new GridLayoutManager(getActivity(), 2,
                GridLayoutManager.VERTICAL, false));
        rvSquareAds.setAdapter(homeFragmentSquareAdsRecyclerView);


        adRecyclerViewAdapter = new AdRecyclerViewAdapter(getActivity(), bannerAdsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adRecyclerViewAdapter);

        homeFragmentServiceRecyclerAdapter = new HomeFragmentServiceRecyclerAdapter(getActivity(), serviceList);
        rvServices.setHasFixedSize(true);
        rvServices.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));
        rvServices.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        rvServices.setLayoutManager(new GridLayoutManager(getActivity(), 4,
                GridLayoutManager.VERTICAL, false));
        rvServices.setAdapter(homeFragmentServiceRecyclerAdapter);

    }

    private void prepareNetworkCall() {
        networkCallService();
        networkCallAds();
    }

    private void networkCallService() {

        Call<JsonObject> call = ApiClient
                .getInterface()
                .getAllServices(SessionManager.getToken(getActivity()));

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 200) {
                    Gson gson = new Gson();
                    AllServiceResponseModel allServiceResponseModel
                            = gson.fromJson(response.body(), AllServiceResponseModel.class);
                    serviceList = allServiceResponseModel.getServiceList();
                    startServiceRecyclerView();
                }

                if (response.code() == 500) {

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(MYTAG, "Error getAllService: " + t.getMessage());
            }
        });

    }

    private void startServiceRecyclerView() {
        homeFragmentServiceRecyclerAdapter = new HomeFragmentServiceRecyclerAdapter(getActivity(), serviceList);
        rvServices.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        rvServices.setAdapter(homeFragmentServiceRecyclerAdapter);
    }

    private void networkCallAds() {

    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.lvAcRepairMenu) {
//            Intent intent = new Intent(getActivity(), AirConditionSubMenuActivity.class);
//            startActivity(intent);
//        }
//
//        if (v.getId() == R.id.lvElectricianMenu) {
//            Intent intent = new Intent(getActivity(), ElectricianSubMenuActivity.class);
//            startActivity(intent);
//        }
//
//        if (v.getId() == R.id.lvPhotographyMenu) {
//            Intent intent = new Intent(getActivity(), PhotographySubMenuActivity.class);
//            startActivity(intent);
//        }
//
//        if (v.getId() == R.id.lvPestControlMenu) {
//            Intent intent = new Intent(getActivity(), PestControlSubMenuActivity.class);
//            startActivity(intent);
//        }

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
