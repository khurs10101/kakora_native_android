package com.khurshid.kamkora.view.fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.khurshid.kamkora.R;
import com.khurshid.kamkora.api.ApiClient;
import com.khurshid.kamkora.model.Avatar;
import com.khurshid.kamkora.model.AvatarResponseModel;
import com.khurshid.kamkora.model.User;
import com.khurshid.kamkora.utils.SessionManager;
import com.khurshid.kamkora.view.activities.DashboardActivity;
import com.khurshid.kamkora.view.activities.LoginActivity;
import com.khurshid.kamkora.view.alertdialog.DialogError;
import com.khurshid.kamkora.view.alertdialog.DialogInfo;
import com.khurshid.kamkora.view.alertdialog.DialogUpdateSpinner;
import com.khurshid.kamkora.view.alertdialog.DialogUpdateText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private FragmentManager fragmentManager;
    private ImageView ivEditName, ivEditPhone, ivEditEmail, ivEditGender, ivEditAge;
    private String[] staGender;


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
        ivEditName = v.findViewById(R.id.iv_profile_edit_name);
        ivEditAge = v.findViewById(R.id.iv_profile_edit_age);
        ivEditEmail = v.findViewById(R.id.iv_profile_edit_email);
        ivEditPhone = v.findViewById(R.id.iv_profile_edit_phone);
        ivEditGender = v.findViewById(R.id.iv_profile_edit_gender);
//        tvProfileCity = v.findViewById(R.id.tvProfileCity);

        btProfileLogin.setOnClickListener(this);
        btProfileLogout.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
        ivEditName.setOnClickListener(this);
        ivEditAge.setOnClickListener(this);
        ivEditGender.setOnClickListener(this);
        ivEditPhone.setOnClickListener(this);
        ivEditEmail.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentManager = getActivity().getSupportFragmentManager();
        staGender = getResources().getStringArray(R.array.genderArray);
        updateProfileFragmentInitailView();

    }

    private void updateProfileFragmentInitailView() {
        if (!SessionManager.isLoggedIn(getActivity())) {
            lvLoggedIn.setVisibility(View.GONE);
            lvNotLoggedIn.setVisibility(View.VISIBLE);
        } else if (SessionManager.isLoggedIn(getActivity())) {

//            String userJson = SessionManager.getUserObjectJson(getActivity());
//            Gson gson = new Gson();
//            currentUser = gson.fromJson(userJson, User.class);

            currentUser = SessionManager.getUserObject(getActivity());
            lvLoggedIn.setVisibility(View.VISIBLE);
            lvNotLoggedIn.setVisibility(View.GONE);

            if (currentUser != null) {
                Log.d(MYTAG, "Name : " + currentUser.getName());
                Log.d(MYTAG, "Age: " + currentUser.getAge());
                loadAvatarFromServer();
                if (currentUser.getName() != null)
                    tvProfileName.setText(currentUser.getName());
                if (currentUser.getEmail() != null)
                    tvProfileEmail.setText(currentUser.getEmail());
                if (currentUser.getGender() != null)
                    tvProfileGender.setText(currentUser.getGender());
                if (currentUser.getCity() != null) ;
//                tvProfileCity.setText(currentUser.getCity());
                if (currentUser.getAge() != null)
                    tvAge.setText(currentUser.getAge());
            } else {
                Log.d(MYTAG, "Current user is null " + currentUser);
            }
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

        if (v.getId() == R.id.iv_profile_edit_name) {
            DialogUpdateText
                    .newInstance("Update Name", 1)
                    .show(fragmentManager, "DialogUpdate");
        }

        if (v.getId() == R.id.iv_profile_edit_age) {
            DialogUpdateText
                    .newInstance("Update Age", 3)
                    .show(fragmentManager, "DialogUpdate");
        }

        if (v.getId() == R.id.iv_profile_edit_email) {

        }

        if (v.getId() == R.id.iv_profile_edit_phone) {

        }

        if (v.getId() == R.id.iv_profile_edit_gender) {
            DialogUpdateSpinner
                    .newInstance("Update Gender", 0, staGender)
                    .show(fragmentManager, "");
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

//                displayImage();
//                ContentResolver cR = getContext().getContentResolver();
//                MimeTypeMap mime = MimeTypeMap.getSingleton();
//                String type = mime.getExtensionFromMimeType(cR.getType(imageUri));
//                Log.d(MYTAG, "mimetype of image: " + cR.getType(imageUri) + "\n" + type);

                updateImageToServer();
//                displayImage();
//                displayImage("");
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Log.e(MYTAG, "Image crop error: " + result.getError().getMessage());
            }
        }
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getActivity()
                    .getContentResolver()
                    .query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor
                            .getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private void updateImageToServer() {
        File file = null;
        file = new File(imageUri.getPath());
        Log.d(MYTAG, "Image file is: " + file);
//        try {
//
//            file = new File(imageUri.getPath());
//
//            ParcelFileDescriptor parcelFileDescriptor =
//                    getActivity().getContentResolver()
//                            .openFileDescriptor(imageUri, "r", null);
//            InputStream inputStream = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
//            file = new File(getActivity().getCacheDir(), getFileName(imageUri));
//            OutputStream outputStream = new FileOutputStream(file);
//            byte[] buffer = new byte[8192];
//            int length;
//            while ((length = inputStream.read(buffer)) > 0) {
//                outputStream.write(buffer, 0, length);
//            }

//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ContentResolver cR = getActivity().getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        String type = mime.getExtensionFromMimeType(cR.getType(imageUri));

        Log.d(MYTAG, "mimetype of image: " + getMimeType(imageUri));

//        Log.d(MYTAG, "Image filetype: " + getActivity().getContentResolver().getType(imageUri));
//        RequestBody requestFile =
//                RequestBody.create(
//                        MediaType.parse("$contentType/*"),
//                        file
//                );

        RequestBody requestFile =
                RequestBody.create(file, MediaType.parse(getMimeType(imageUri)));

        MultipartBody.Part part = MultipartBody.Part.createFormData("userAvatar", file.getName(), requestFile);

        Call<JsonObject> call = ApiClient
                .getInterface()
                .uploadUserAvatar(SessionManager.getLoggedInUserId(getActivity()), part);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(MYTAG, "Image upload response code: " + response.code());
                Log.d(MYTAG, "response: " + response.body());
                if (response.code() == 201) {
                    loadAvatarFromServer();
                    DialogInfo
                            .newInstance("Image uploaded successfully")
                            .show(fragmentManager, "DialogInfo");

                }

                if (response.code() == 404) {
                    DialogError
                            .newInstance("User not found\n" + response.code())
                            .show(getFragmentManager(), "");
                }

                if (response.code() == 500) {
                    DialogError
                            .newInstance("Server error\n" + response.code())
                            .show(getFragmentManager(), "");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(MYTAG, "Retrofit error: " + t.getMessage());
                DialogError
                        .newInstance("Retrofit Error")
                        .show(fragmentManager, "DialogError");
            }
        });

    }

    private void loadAvatarFromServer() {

        Call<JsonObject> objectCall = ApiClient
                .getInterface()
                .loadAvatar(SessionManager.getLoggedInUserId(getActivity()));

        objectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(MYTAG, "Avatar response code: " + response.code());
                Log.d(MYTAG, "response: " + response.body());
                Gson gson = new Gson();
                if (response.code() == 200) {
                    AvatarResponseModel avatarResponseModel =
                            gson.fromJson(response.body(), AvatarResponseModel.class);

                    Avatar avatar = avatarResponseModel.getAvatar();
                    displayImage(avatar.getUrl());

                }
                if (response.code() == 404) {
                    DialogError
                            .newInstance("Server response code: " + response.code())
                            .show(getFragmentManager(), "");
                }

                if (response.code() == 500) {
                    DialogError
                            .newInstance("Server response code: " + response.code())
                            .show(getFragmentManager(), "");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(MYTAG, "avatar retrofit error: " + t.getMessage());
                DialogError
                        .newInstance("Retrofit Error")
                        .show(getFragmentManager(), "");
            }
        });

    }

    private void displayImage(String url) {

        url = ApiClient.Base_URL + "/" + url;

        Log.d(MYTAG, "displayImage imageUri :" + imageUri);
        Glide.with(getActivity())
                .load(url)
                .into(ivAvatar);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        Log.d(MYTAG, "Lifecycle: " + super.getLifecycle());
        return super.getLifecycle();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(MYTAG, "On resume is called");
//        updateProfileFragmentInitailView();
    }

    public String getMimeType(Uri uri) {
        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = getContext().getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }
}
