package com.khurshid.kamkora.view.alertdialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.khurshid.kamkora.R;
import com.khurshid.kamkora.view.activities.MapLocationPickerActivity;
import com.khurshid.kamkora.view.fragments.HomeFragment;

public class DialogLocation extends DialogFragment implements View.OnClickListener {

    private static DialogLocation dialogLocation = null;
    private static HomeFragment homeFragmentHere;
    private TextView tvMessage;
    private Button btLocation, btExit;

    public DialogLocation() {
    }

    public static DialogLocation newInstance(HomeFragment homeFragment, String message) {
        if (dialogLocation == null)
            dialogLocation = new DialogLocation();
        homeFragmentHere = homeFragment;
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        dialogLocation.setArguments(bundle);
        return dialogLocation;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_location, container, false);
        tvMessage = view.findViewById(R.id.tv_dialog_location_message);
        btLocation = view.findViewById(R.id.bt_dialog_location_select);
        btExit = view.findViewById(R.id.bt_dialog_location_close);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        btLocation.setOnClickListener(this);
        btExit.setOnClickListener(this);
        tvMessage.setText(getArguments().getString("message"));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_dialog_location_select) {
            Intent intent = new Intent(homeFragmentHere.getContext(), MapLocationPickerActivity.class);
            homeFragmentHere.startActivityForResult(intent, 1);
            dismiss();
        }

        if (v.getId() == R.id.bt_dialog_location_close) {
            getActivity().finish();
        }
    }
}
