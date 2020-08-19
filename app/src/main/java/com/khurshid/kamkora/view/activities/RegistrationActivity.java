package com.khurshid.kamkora.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.khurshid.kamkora.R;
import com.khurshid.kamkora.api.ApiClient;
import com.khurshid.kamkora.model.CCodeModel;
import com.khurshid.kamkora.model.UserRegistrationModel;
import com.khurshid.kamkora.utils.Constants;
import com.khurshid.kamkora.utils.ProgressBarManager;
import com.khurshid.kamkora.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String MYTAG = RegistrationActivity.class.getSimpleName();
    private static ProgressBar pb;
    @BindView(R.id.sp_area_code)
    Spinner spAreaCode;
    @BindView(R.id.sp_register_gender)
    Spinner spGender;
    @BindView(R.id.sp_register_city)
    Spinner spCity;
    private EditText etName, etPhone, etEmail, etPassword, etRepassword;
    private TextView tvSignUp, tvLogin;
    private String stName, stEmail, stPhone, stPassword, stRepassword, stCodeSelect, stGender, stCity;
    private List<CCodeModel> cCodeModels;
    private String[] ccodeArray, genderArray, cityArray;
    private ArrayAdapter spGenderAdapter, spCityAdapter, spCcodeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        cCodeModels = new ArrayList<>();
        etName = findViewById(R.id.et_reg_name);
        etEmail = findViewById(R.id.et_reg_email);
        etPhone = findViewById(R.id.et_reg_mobile);
        etPassword = findViewById(R.id.et_reg_password);
        etRepassword = findViewById(R.id.et_reg_repassword);
        tvLogin = findViewById(R.id.tv_reg_login);
        tvSignUp = findViewById(R.id.tv_signup);
        pb = findViewById(R.id.pbRegistration);
        tvSignUp.setOnClickListener(this);
        tvLogin.setOnClickListener(this);

        ccodeArray = getResources().getStringArray(R.array.ccode);
        genderArray = getResources().getStringArray(R.array.genderArray);
        cityArray = getResources().getStringArray(R.array.cityArray);

        spCcodeAdapter = new ArrayAdapter<>(this, R.layout.spinner_area_code_layout, ccodeArray);
        spCcodeAdapter.setDropDownViewResource(R.layout.spinner_area_code_layout);
        spAreaCode.setAdapter(spCcodeAdapter);

        spGenderAdapter = new ArrayAdapter<>(this, R.layout.spinner_area_code_layout, genderArray);
        spGenderAdapter.setDropDownViewResource(R.layout.spinner_area_code_layout);
        spGender.setAdapter(spGenderAdapter);

        spCityAdapter = new ArrayAdapter<>(this, R.layout.spinner_area_code_layout, cityArray);
        spCityAdapter.setDropDownViewResource(R.layout.spinner_area_code_layout);
        spCity.setAdapter(spCityAdapter);

        spAreaCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stCodeSelect = ccodeArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stGender = genderArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stCity = cityArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_reg_login) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.tv_signup) {
            actionSignUp();
        }
    }

    private void actionSignUp() {
        //validator
        stName = etName.getText().toString();
        stEmail = etEmail.getText().toString();
        stPhone = etPhone.getText().toString();
        stPassword = etPassword.getText().toString();
        stRepassword = etRepassword.getText().toString();


        int count = 0;

        if (stName.length() == 0) {
            Toast.makeText(this, "Invalid Name", Toast.LENGTH_SHORT).show();
            count++;
        }

        if (stEmail.length() == 0) {
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
            count++;
        }

        if (stPhone.length() == 0) {
            Toast.makeText(this, "Invalid Mobile", Toast.LENGTH_SHORT).show();
            count++;
        }

        if (!stPassword.equals(stRepassword)) {
            Toast.makeText(this, "Password dont match", Toast.LENGTH_SHORT).show();
            count++;
        }

        if (count == 0) {

//            editor.putString("username", stName);
//            editor.commit();
//            Intent intent = new Intent(this, DashboardActivity.class);
//            startActivity(intent);
//            finish();
            prepareParams();

        } else {
            Toast.makeText(this, "Validation Failed", Toast.LENGTH_SHORT).show();
        }


    }

    private void prepareParams() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", stName);
            jsonObject.put("email", stEmail);
            jsonObject.put("phone", stPhone);
            jsonObject.put("gender", stGender);
            jsonObject.put("city", stCity);
            jsonObject.put("password", stPassword);
            jsonObject.put("isVerified", false);

            networkCall(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void networkCall(JSONObject jsonObject) {

        tvSignUp.setVisibility(View.GONE);
        ProgressBarManager.startProgressBar(pb);

        Call<JsonObject> call = ApiClient
                .getInterface()
                .getSignUp((JsonObject) JsonParser.parseString(jsonObject.toString()));

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                ProgressBarManager.stopProgressBar(pb);
                tvSignUp.setVisibility(View.VISIBLE);
                if (response.code() == 201) {
                    Gson gson = new Gson();
                    UserRegistrationModel model = gson.fromJson(response.body(), UserRegistrationModel.class);
                    String userId = model.getUser().getId();
                    String token = model.getToken();
                    SessionManager.startSession(RegistrationActivity.this, model.getUser(), token);
                    Intent intent = new Intent(RegistrationActivity.this, DashboardActivity.class);
                    intent.putExtra(Constants.USEROBJECT, model.getUser());
                    startActivity(intent);
                    finish();
                }

                if (response.code() == 409) {
                    Toast.makeText(RegistrationActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                }

                if (response.code() == 500) {
                    Toast.makeText(RegistrationActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                ProgressBarManager.stopProgressBar(pb);
                tvSignUp.setVisibility(View.VISIBLE);
                Toast.makeText(RegistrationActivity.this, "Internal App error", Toast.LENGTH_SHORT).show();
                Log.d(MYTAG, "SignUp failed: " + t.getMessage());
            }
        });

    }


}