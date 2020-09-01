package com.khurshid.kamkora.view.alertdialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.khurshid.kamkora.R;
import com.khurshid.kamkora.api.ApiClient;
import com.khurshid.kamkora.model.User;
import com.khurshid.kamkora.model.UserUpdateResponse;
import com.khurshid.kamkora.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogUpdateText extends DialogFragment implements View.OnClickListener {

    private static final String MYTAG = DialogUpdateText.class.getSimpleName();
    private static DialogUpdateText dialogUpdateText = null;
    private TextView tvMessage;
    private EditText etField;
    private Button btOk, btCancel;
    private int type;
    private LinearLayout lvDialogUpdate;
    private ProgressBar pb;

    public static DialogUpdateText newInstance(String message, int type) {
        if (dialogUpdateText == null)
            dialogUpdateText = new DialogUpdateText();

        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        bundle.putInt("type", type);
        dialogUpdateText.setArguments(bundle);
        return dialogUpdateText;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_update_text, container, false);
        tvMessage = view.findViewById(R.id.tv_dialog_update_message);
        etField = view.findViewById(R.id.et_dialog_update_field);
        btOk = view.findViewById(R.id.bt_dialog_update_ok);
        btCancel = view.findViewById(R.id.bt_dialog_update_close);
        lvDialogUpdate = view.findViewById(R.id.lvDialogUpdate);
        pb = view.findViewById(R.id.pbDialogUpdateTextProgress);
        btOk.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String message = getArguments().getString("message");
        type = getArguments().getInt("type");
        getDialog().setCanceledOnTouchOutside(false);
        if (type == 1) {
            tvMessage.setText(message);
            etField.setHint("Enter name");
            etField.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        if (type == 2) {
            tvMessage.setText(message);
            etField.setHint("Enter phone");
            etField.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        if (type == 3) {
            tvMessage.setText(message);
        }


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_dialog_update_ok) {

            if (type == 2) {

            } else {
                if (etField.getText().toString().length() != 0) {
                    String name = etField.getText().toString();
                    prepareNetworkCall(name);
                }
            }

        }

        if (v.getId() == R.id.bt_dialog_update_close) {
            dismiss();
        }
    }

    private void prepareNetworkCall(String name) {
        lvDialogUpdate.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);
        JsonObject object = new JsonObject();

        if (type == 1) {
            object.addProperty("name", name);
        }

        if (type == 3) {
            object.addProperty("age", name);
        }

        Call<JsonObject> call = ApiClient
                .getInterface()
                .updateUserDetails(SessionManager.getLoggedInUserId(getActivity()), object);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.d(MYTAG, "response code: " + response.code());
                lvDialogUpdate.setVisibility(View.VISIBLE);
                pb.setVisibility(View.GONE);
                if (response.code() == 201) {
                    Gson gson =
                            new Gson();
                    UserUpdateResponse userUpdateResponse =
                            gson.fromJson(response.body(), UserUpdateResponse.class);
                    User user =
                            userUpdateResponse.getUser();
                    SessionManager.setUserObject(getActivity(), user);
                    Log.d(MYTAG, "User Id: " + user.getId());
//                    sendDataToFragment(name);
                    DialogInfo
                            .newInstance("Updated successfully")
                            .show(getFragmentManager(), "");
                    dismiss();
                } else {
                    DialogError
                            .newInstance("Server response code: " + response.code())
                            .show(getFragmentManager(), "");
                    dismiss();
                }

//                if (response.code() == 404) {
//                    Log.d(MYTAG, "Response code: " + response.code());
//                }
//
//                if (response.code() == 500) {
//                    Log.d(MYTAG, "Response code: " + response.code());
//                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                lvDialogUpdate.setVisibility(View.VISIBLE);
                pb.setVisibility(View.GONE);
                DialogError
                        .newInstance("Retrofit Error")
                        .show(getFragmentManager(), "");
                Log.d(MYTAG, "Error retrofit " + t.getMessage());
            }
        });

    }

    private void sendDataToFragment(String name) {
        if (getTargetFragment() == null) {
            Log.d(MYTAG, "Target fragment is empty");
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("name", name);
        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

}
