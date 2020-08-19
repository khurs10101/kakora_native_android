package com.khurshid.kamkora.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.khurshid.kamkora.R;
import com.khurshid.kamkora.api.ApiClient;
import com.khurshid.kamkora.model.UserLoginModel;
import com.khurshid.kamkora.utils.Constants;
import com.khurshid.kamkora.utils.ProgressBarManager;
import com.khurshid.kamkora.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String MYTAG = LoginActivity.class.getSimpleName();
    private static ProgressBar pb;
    private EditText username, password;
    private TextView tvLogin, tvSignup, tvForgotPassword;
    private String stUsername, stPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        checkLoginStatus();
    }

    private void checkLoginStatus() {
        //check if user is logged in or not

    }

    private void initView() {
        username = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);
        tvLogin = findViewById(R.id.tv_login);
        tvSignup = findViewById(R.id.tv_signup);
        tvForgotPassword = findViewById(R.id.tv_forgotpassword);
        pb = findViewById(R.id.pbLoginProgress);
        tvLogin.setOnClickListener(this);
        tvSignup.setOnClickListener(this);
        tvForgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_login) {
            stUsername = username.getText().toString();
            stPassword = password.getText().toString();

            int count = 0;

            if (stUsername.length() == 0) {
                count++;
                Toast.makeText(this, "Invalid Mobile", Toast.LENGTH_SHORT).show();
            }

            if (stPassword.length() == 0) {
                count++;
                Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
            }

            if (count == 0) {
//                editor.putString("username", stUsername);
//                editor.commit();
//                Intent intent = new Intent(this, DashboardActivity.class);
//                startActivity(intent);
//                finish();
                prepareParams();
            } else {
                Toast.makeText(this, "Validation Failed", Toast.LENGTH_SHORT).show();
            }

//            prepare for network call

        }

        if (v.getId() == R.id.tv_signup) {
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.tv_forgotpassword) {
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            startActivity(intent);
        }
    }

    private void prepareParams() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("email", stUsername);
            jsonObject.put("password", stPassword);

            networkCall(jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void networkCall(JSONObject jsonObject) {

        tvLogin.setVisibility(View.GONE);
        ProgressBarManager.startProgressBar(pb);

        Call<JsonObject> call = ApiClient
                .getInterface()
                .getSignIn((JsonObject) JsonParser.parseString(jsonObject.toString()));

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                ProgressBarManager.stopProgressBar(pb);
                tvLogin.setVisibility(View.VISIBLE);
                if (response.code() == 200) {
                    Log.d(MYTAG, "Login Response: " + response.body());
                    Gson gson = new Gson();
                    UserLoginModel userModel = gson.fromJson(response.body(), UserLoginModel.class);
                    String userId = userModel.getUser().getId();
                    String token = userModel.getToken();
                    SessionManager.startSession(LoginActivity.this, userModel.getUser(), token);

                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    intent.putExtra(Constants.USEROBJECT, userModel.getUser());
                    startActivity(intent);
                    finish();


                }

                if (response.code() == 404) {
                    Toast.makeText(LoginActivity.this, "user not registered", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                ProgressBarManager.stopProgressBar(pb);
                tvLogin.setVisibility(View.VISIBLE);
                Toast.makeText(LoginActivity.this, "Internal App error", Toast.LENGTH_SHORT).show();
                Log.e(MYTAG, "Login Error: " + t.getMessage());
            }
        });
    }
}