package com.khurshid.kamkora.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.khurshid.kamkora.R;
import com.khurshid.kamkora.api.ApiClient;
import com.khurshid.kamkora.model.Address;
import com.khurshid.kamkora.model.AddressSingleModelResponse;
import com.khurshid.kamkora.model.LocalLocation;
import com.khurshid.kamkora.utils.ProgressBarManager;
import com.khurshid.kamkora.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int PICK_LOCATION_FROM_ADDRESS = 1;
    private static final String MYTAG = AddAddressActivity.class.getSimpleName();

    @BindView(R.id.tv_address_pick_address)
    TextView tvAddressPick;
    @BindView(R.id.et_address_name)
    EditText etName;
    @BindView(R.id.et_address_house_no)
    EditText etHouseNo;
    @BindView(R.id.et_address_society)
    EditText etSociety;
    @BindView(R.id.et_address_line_1)
    EditText etAddressLine1;
    @BindView(R.id.et_address_line_2)
    EditText etAddressLine2;
    @BindView(R.id.et_address_city)
    EditText etCity;
    @BindView(R.id.et_address_state)
    EditText etState;
    @BindView(R.id.et_address_pincode)
    EditText etPincode;

    @BindView(R.id.tv_save_address)
    TextView btAddAddress;
    @BindView(R.id.lvAddressSaveButton)
    LinearLayout lvSaveButton;
    @BindView(R.id.pbAddressSave)
    ProgressBar pb;
    private Address address;
    private String stName, stHouseNo, stSociety,
            stAddressLine1, getStAddressLine2,
            stLocation, stCity, stState, stPincode;
    private LatLng latLng;
    private LocalLocation localLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvAddressPick.setOnClickListener(this);
        btAddAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_address_pick_address) {
            Intent intent = new Intent(this, MapLocationPickerActivity.class);
            startActivityForResult(intent, PICK_LOCATION_FROM_ADDRESS);
        }

        if (v.getId() == R.id.tv_save_address) {
            //validation
            int count = 0;
            count = validateInputs();

            if (count == 0) {
                prepareParams();
            } else {
                Toast
                        .makeText(this, "Please enter all required fields", Toast.LENGTH_SHORT)
                        .show();
            }


        }
    }

    private void prepareParams() {
        lvSaveButton.setVisibility(View.GONE);
        ProgressBarManager.startProgressBar(pb);
        if (SessionManager.isLoggedIn(this)) {

            Gson gson = new Gson();
            String jsonString = gson.toJson(address, Address.class);
            Call<JsonObject> call = ApiClient
                    .getInterface()
                    .setUserAddress(SessionManager.getLoggedInUserId(this), (JsonObject) JsonParser.parseString(jsonString));

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    lvSaveButton.setVisibility(View.VISIBLE);
                    ProgressBarManager.stopProgressBar(pb);
                    if (response.code() == 201) {
                        Log.d(MYTAG, "Body: " + response.body());
                        AddressSingleModelResponse addressSingleModelResponse;
                        Gson gson = new Gson();

                        addressSingleModelResponse = gson.fromJson(response.body(), AddressSingleModelResponse.class);

                        Log.d(MYTAG, "address: " + address.getState());
                        Intent intent = new Intent(AddAddressActivity.this, OrderSummaryActivity.class);
                        intent.putExtra("AddressObject", addressSingleModelResponse.getAddress());
                        startActivity(intent);
                    }

                    if (response.code() == 500) {
                        Log.d(MYTAG, "Server error 500");
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    lvSaveButton.setVisibility(View.VISIBLE);
                    ProgressBarManager.stopProgressBar(pb);
                    Log.d(MYTAG, "error saving address: " + t.getMessage());
                }
            });
        } else {
            Log.d(MYTAG, "Please Login");
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private int validateInputs() {
        int count = 0;
        address = new Address();
        address.setUserId(SessionManager.getLoggedInUserId(this));
        if (etName.getText().toString() != null || etName.getText().toString().length() != 0) {
            address.setName(etName.getText().toString());
        } else {
            count++;
        }

        if (etHouseNo.getText().toString() != null || etHouseNo.getText().toString().length() != 0) {
            address.setHouseNo(etHouseNo.getText().toString());
        } else {
            count++;
        }

        if (etSociety.getText().toString() != null || etSociety.getText().toString().length() != 0) {
            address.setSociety(etSociety.getText().toString());
        } else {

        }

        if (etAddressLine1.getText().toString() != null || etAddressLine1.getText().toString().length() != 0) {
            address.setAddressLine1(etAddressLine1.getText().toString());
        } else {
            count++;
        }

        if (etAddressLine2.getText().toString() != null || etAddressLine2.getText().toString().length() != 0) {
            address.setAddressLine2(etAddressLine2.getText().toString());
        } else {

        }

        if (etCity.getText().toString() != null || etCity.getText().toString().length() != 0) {
            address.setCity(etCity.getText().toString());
        } else {
            count++;
        }

        if (etState.getText().toString() != null || etState.getText().toString().length() != 0) {
            address.setState(etState.getText().toString());
        } else {
            count++;
        }

        if (etPincode.getText().toString() != null || etPincode.getText().toString().length() != 0) {
            address.setPincode(etPincode.getText().toString());
        } else {
            count++;
        }

        if (localLocation != null) {
            address.setLatitude(String.valueOf(localLocation.getLatitude()));
            address.setLongitude(String.valueOf(localLocation.getLongitude()));
        }

        return count;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_LOCATION_FROM_ADDRESS) {
            if (resultCode == Activity.RESULT_OK) {
                fillTheAddressFields(data);
            }
        }
    }

    private void fillTheAddressFields(Intent data) {
        stLocation = data.getStringExtra("getLocation");
        localLocation = (LocalLocation) data.getSerializableExtra("getLocationObject");
        String[] locationList = TextUtils.split(stLocation, ",");

//        stPincode = locationList[locationList.length - 1];
        stState = locationList[locationList.length - 2];
        stCity = locationList[locationList.length - 3];

        String[] temp = TextUtils.split(stState.trim(), " ");
        Log.d(MYTAG, stState + " " + temp[0] + " " + temp[1]);
        stState = temp[0];
        stPincode = temp[1];

        etCity.setText(stCity);
        etState.setText(stState);
        etPincode.setText(stPincode);

    }
}