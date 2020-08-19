package com.khurshid.kamkora.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.khurshid.kamkora.R;
import com.khurshid.kamkora.model.User;
import com.khurshid.kamkora.utils.SessionManager;
import com.khurshid.kamkora.view.activities.DashboardActivity;
import com.khurshid.kamkora.view.activities.LoginActivity;

import butterknife.ButterKnife;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    //    @BindView(R.id.bt_profile_login)
//    Button btLogin;
    //
//    @BindView(R.id.bt_profile_logout)
//    Button btLogout;
    //
//    @BindView(R.id.textView)
    TextView tvText;

    private String stUser = null;
    private LinearLayout lvNotLoggedIn, lvLoggedIn;
    private Button btProfileLogout, btProfileLogin;
    private User currentUser;
    private TextView tvProfileName, tvProfileEmail, tvProfileGender, tvProfileCity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(getActivity());
        initView(v);
        return v;
    }

    private void initView(View v) {
        lvNotLoggedIn = v.findViewById(R.id.lvNotLoggedIn);
        lvLoggedIn = v.findViewById(R.id.lvLoggedIn);
        btProfileLogin = v.findViewById(R.id.bt_profile_login);
        btProfileLogout = v.findViewById(R.id.bt_profile_logout);
        tvText = v.findViewById(R.id.textView);
        tvProfileName = v.findViewById(R.id.tvProfileName);
        tvProfileEmail = v.findViewById(R.id.tvProfileEmail);
        tvProfileGender = v.findViewById(R.id.tvProfileGender);
        tvProfileCity = v.findViewById(R.id.tvProfileCity);
        btProfileLogin.setOnClickListener(this);
        btProfileLogout.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!SessionManager.isLoggedIn(getActivity())) {
            lvLoggedIn.setVisibility(View.GONE);
            lvNotLoggedIn.setVisibility(View.VISIBLE);
            tvText.setText("Login Please");
        } else if (SessionManager.isLoggedIn(getActivity())) {

            String userJson = SessionManager.getUserObjectJson(getActivity());
            Gson gson = new Gson();
            currentUser = gson.fromJson(userJson, User.class);
            lvLoggedIn.setVisibility(View.VISIBLE);
            lvNotLoggedIn.setVisibility(View.GONE);

            if (currentUser.getName() != null)
                tvProfileName.setText(currentUser.getName());
            if (currentUser.getEmail() != null)
                tvProfileEmail.setText(currentUser.getEmail());
            if (currentUser.getGender() != null)
                tvProfileGender.setText(currentUser.getGender());
            if (currentUser.getCity() != null)
                tvProfileCity.setText(currentUser.getCity());
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_profile_logout) {
            SessionManager.endSession();
            Intent intent = new Intent(getActivity(), DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        if (v.getId() == R.id.bt_profile_login) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }
}
