package com.khurshid.kamkora.view.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.khurshid.kamkora.R;
import com.khurshid.kamkora.model.CarouselAds;
import com.khurshid.kamkora.model.SubService;
import com.khurshid.kamkora.view.adapters.SubMenuElectricianRecyclerAdapter;
import com.synnapps.carouselview.CarouselView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ElectricianSubMenuActivity extends AppCompatActivity implements View.OnClickListener {

    //    @BindView(R.id.lvSwitchRepair)
//    LinearLayout lvSwitchRepair;
//    @BindView(R.id.lvSolarPanelInstall)
//    LinearLayout lvSolarPanelInstall;
//    @BindView(R.id.lvHomeAppliancesRepair)
//    LinearLayout lvHomeAppliancesRepair;
//    @BindView(R.id.lvHomeWiringRepair)
//    LinearLayout lvHomeWiringRepair;
    @BindView(R.id.rv_sub_electrician)
    RecyclerView rvSubService;
    @BindView(R.id.cv_electrician)
    CarouselView carouselView;
    private int SERVICE_ID;
    private List<SubService> subServiceList;
    private List<CarouselAds> carouselAdsList;
    private SubMenuElectricianRecyclerAdapter subMenuElectricianRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrician_sub_menu);
        ButterKnife.bind(this);
        SERVICE_ID = Integer.parseInt(getIntent().getStringExtra("serviceId"));
        initView();
        fakeNetworkCall();
    }

    private void fakeNetworkCall() {

        subServiceList = new ArrayList<>();
        carouselAdsList = new ArrayList<>();
        if (SERVICE_ID == 10) {
            String[] names = {"AC repair", "Home Appliance", "Home wiring", "Solar Panel"};
            int[] images = {R.drawable.ic_air_conditioner, R.drawable.ic_electricity,
                    R.drawable.ic_switchboard, R.drawable.ic_electrician};
            int[] subServiceId = {11, 12, 13, 14};
            int[] subServiceRate = {100, 150, 200, 250};
            for (int i = 0; i < names.length; i++) {
                SubService subService = new SubService();
                subService.setServiceId(String.valueOf(SERVICE_ID));
                subService.setSubServiceId(String.valueOf(subServiceId[i]));
                subService.setName(names[i]);
                subService.setSampleImage(images[i]);
                subService.setRate(String.valueOf(subServiceRate[i]));
                subService.setSampleImageCarousel(R.drawable.electrician_1);
                subServiceList.add(subService);
            }

//            prepareNetworkCall();

            int[] carouselImg = {R.drawable.electrician_1, R.drawable.electrician_2,
                    R.drawable.electrician_3};
            for (int i = 0; i < carouselImg.length; i++) {
                CarouselAds carouselAds = new CarouselAds();
                carouselAds.setSampleImage(carouselImg[i]);
                carouselAds.setServiceId(String.valueOf(SERVICE_ID));
                carouselAdsList.add(carouselAds);
            }
        }

        if (SERVICE_ID == 20) {
            String[] names = {"Wedding", "Birthday", "Corporate", "Movies"};
            int[] images = {R.drawable.ic_bride, R.drawable.ic_birthday,
                    R.drawable.ic_teamwork, R.drawable.ic_clapperboard};
            int[] subServiceId = {21, 22, 23, 24};
            int[] subServiceRate = {100, 150, 200, 250};
            for (int i = 0; i < names.length; i++) {
                SubService subService = new SubService();
                subService.setServiceId(String.valueOf(SERVICE_ID));
                subService.setSubServiceId(String.valueOf(subServiceId[i]));
                subService.setSampleImage(images[i]);
                subService.setName(names[i]);
                subService.setRate(String.valueOf(subServiceRate[i]));
                subService.setSampleImageCarousel(R.drawable.photographer_non);
                subServiceList.add(subService);
            }

            int[] carouselImg = {R.drawable.photographer_non, R.drawable.photography_2,
                    R.drawable.photography_3};
            for (int i = 0; i < carouselImg.length; i++) {
                CarouselAds carouselAds = new CarouselAds();
                carouselAds.setSampleImage(carouselImg[i]);
                carouselAds.setServiceId(String.valueOf(SERVICE_ID));
                carouselAdsList.add(carouselAds);
            }
        }

        if (SERVICE_ID == 30) {
            String[] names = {"Mosquito", "Bedbug", "Termite", "Fly"};
            int[] images = {R.drawable.ic_mosquito, R.drawable.ic_bedbug,
                    R.drawable.ic_termite, R.drawable.ic_fly};
            int[] subServiceId = {31, 32, 33, 34};
            int[] subServiceRate = {100, 150, 200, 250};
            for (int i = 0; i < names.length; i++) {
                SubService subService = new SubService();
                subService.setServiceId(String.valueOf(SERVICE_ID));
                subService.setSubServiceId(String.valueOf(subServiceId[i]));
                subService.setSampleImage(images[i]);
                subService.setName(names[i]);
                subService.setRate(String.valueOf(subServiceRate[i]));
                subService.setSampleImageCarousel(R.drawable.cleaner_2);
                subServiceList.add(subService);
            }

            int[] carouselImg = {R.drawable.cleaner_1, R.drawable.cleaner_2,
                    R.drawable.cleaner_3};
            for (int i = 0; i < carouselImg.length; i++) {
                CarouselAds carouselAds = new CarouselAds();
                carouselAds.setSampleImage(carouselImg[i]);
                carouselAds.setServiceId(String.valueOf(SERVICE_ID));
                carouselAdsList.add(carouselAds);
            }
        }

        if (SERVICE_ID == 40) {

        }

        startRecyclerView();
    }

    private void prepareNetworkCall() {

    }

    private void startRecyclerView() {
        startCarouselView();
        subMenuElectricianRecyclerAdapter =
                new SubMenuElectricianRecyclerAdapter(this, subServiceList);
        rvSubService.setHasFixedSize(true);
        rvSubService.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL));
        rvSubService.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL));
        rvSubService.setLayoutManager(new GridLayoutManager(this, 4,
                GridLayoutManager.VERTICAL, false));
        rvSubService.setAdapter(subMenuElectricianRecyclerAdapter);
    }

    private void startCarouselView() {
        carouselView.setPageCount(carouselAdsList.size());
        carouselView.setImageListener((position, imageView) -> {
            CarouselAds carouselAds = carouselAdsList.get(position);
            imageView.setImageResource(carouselAds.getSampleImage());
        });
    }

    private void initView() {
//        lvSwitchRepair.setOnClickListener(this);
//        lvSolarPanelInstall.setOnClickListener(this);
//        lvHomeAppliancesRepair.setOnClickListener(this);
//        lvHomeWiringRepair.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        Intent intent = null;
//        switch (v.getId()) {
//            case R.id.lvSwitchRepair:
//                intent = new Intent(this, ServiceInfoActivity.class);
//                intent.putExtra("service", "Switch Repair");
//                intent.putExtra("rate", "100");
//                startActivity(intent);
//                break;
//            case R.id.lvSolarPanelInstall:
//                intent = new Intent(this, ServiceInfoActivity.class);
//                intent.putExtra("service", "Solar panel Install");
//                intent.putExtra("rate", "150");
//                startActivity(intent);
//                break;
//            case R.id.lvHomeAppliancesRepair:
//                intent = new Intent(this, ServiceInfoActivity.class);
//                intent.putExtra("service", "Home appliances Repair");
//                intent.putExtra("rate", "200");
//                startActivity(intent);
//                break;
//            case R.id.lvHomeWiringRepair:
//                intent = new Intent(this, ServiceInfoActivity.class);
//                intent.putExtra("service", "Home wiring");
//                intent.putExtra("rate", "250");
//                startActivity(intent);
//                break;

        //       }
    }
}