package com.khurshid.kamkora.view.alertdialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.khurshid.kamkora.R;

public class DialogUpdateSpinner extends DialogFragment implements View.OnClickListener {

    private static DialogUpdateSpinner dialogUpdateSpinner;

    private Spinner spSpinner;
    private Button btOk, btCancel;
    private TextView tvMessage;
    private ImageView ivIcon;
    private String[] stArray;
    private String stMessage;
    private int type;
    private String stGender;
    private ArrayAdapter<String> arrayAdapter;

    public static DialogUpdateSpinner newInstance(String message, int type, String[] array) {
        if (dialogUpdateSpinner == null)
            dialogUpdateSpinner = new DialogUpdateSpinner();
        Bundle bundle = new Bundle();
        bundle.putStringArray("array", array);
        bundle.putString("message", message);
        bundle.putInt("type", type);
        dialogUpdateSpinner.setArguments(bundle);
        return dialogUpdateSpinner;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_spinner, container, false);
        spSpinner = view.findViewById(R.id.sp_dialog_spinner_field);
        tvMessage = view.findViewById(R.id.tv_dialog_spinner_message);
        btOk = view.findViewById(R.id.bt_dialog_spinner_ok);
        btCancel = view.findViewById(R.id.bt_dialog_spinner_close);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        stMessage = bundle.getString("message");
        type = bundle.getInt("type");
        stArray = bundle.getStringArray("array");
        btOk.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        arrayAdapter =
                new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, stArray);
        spSpinner.setAdapter(arrayAdapter);
        spSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stGender = stArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_dialog_spinner_ok) {

        }

        if (v.getId() == R.id.bt_dialog_spinner_close) {
            getDialog().dismiss();
        }
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
