package com.khurshid.kamkora.view.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.khurshid.kamkora.R;
import com.khurshid.kamkora.model.User;
import com.khurshid.kamkora.utils.SessionManager;
import com.khurshid.kamkora.view.activities.DashboardActivity;
import com.khurshid.kamkora.view.activities.LoginActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private static final String MYTAG = ProfileFragment.class.getSimpleName();

    //    @BindView(R.id.bt_profile_login)
//    Button btLogin;
    //
//    @BindView(R.id.bt_profile_logout)
//    Button btLogout;
    //
//    @BindView(R.id.textView)
//    TextView tvText;

    private String stUser = null;
    private LinearLayout lvNotLoggedIn, lvLoggedIn;
    private Button btProfileLogout, btProfileLogin;
    private User currentUser;
    private TextView tvProfileName, tvProfileEmail,
            tvProfileGender, tvProfileCity, tvAge;
    private CircleImageView ivAvatar;
    private Uri imageUri;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
//        ButterKnife.bind(getActivity());
        initView(v);
        return v;
    }

    private void initView(View v) {
        lvNotLoggedIn = v.findViewById(R.id.lvNotLoggedIn);
        lvLoggedIn = v.findViewById(R.id.lvLoggedIn);
        btProfileLogin = v.findViewById(R.id.bt_profile_login);
        btProfileLogout = v.findViewById(R.id.bt_profile_logout);
//        tvText = v.findViewById(R.id.textView);
        tvProfileName = v.findViewById(R.id.tvProfileName);
        tvProfileEmail = v.findViewById(R.id.tvProfileEmail);
        tvProfileGender = v.findViewById(R.id.tvProfileGender);
        tvAge = v.findViewById(R.id.tvProfileAge);
        ivAvatar = v.findViewById(R.id.iv_profile_fragment_avatar);
//        tvProfileCity = v.findViewById(R.id.tvProfileCity);
        btProfileLogin.setOnClickListener(this);
        btProfileLogout.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!SessionManager.isLoggedIn(getActivity())) {
            lvLoggedIn.setVisibility(View.GONE);
            lvNotLoggedIn.setVisibility(View.VISIBLE);
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
            if (currentUser.getCity() != null) ;
//                tvProfileCity.setText(currentUser.getCity());
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

        if (v.getId() == R.id.iv_profile_fragment_avatar) {
            pickImageAndCrop();
        }
    }

    private void pickImageAndCrop() {
        CropImage
                .activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(getContext(), this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                imageUri = result.getUri();
                displayImage();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Log.e(MYTAG, "Image crop error: " + result.getError().getMessage());
            }
        }
    }

    private void displayImage() {

        Glide.with(getActivity())
                .load(imageUri)
                .into(ivAvatar);
    }
}
