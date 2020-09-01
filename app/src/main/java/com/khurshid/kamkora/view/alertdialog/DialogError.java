package com.khurshid.kamkora.view.alertdialog;

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

public class DialogError extends DialogFragment implements View.OnClickListener {

    private static DialogError dialogError;
    private TextView etMessage;
    private String message;
    private Button btClose;

    public DialogError() {
    }

    public static DialogFragment newInstance(String message) {
        if (dialogError == null)
            dialogError = new DialogError();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        dialogError.setArguments(bundle);
        return dialogError;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_error, container, false);
        etMessage = view.findViewById(R.id.tv_dialog_error_message);
        btClose = view.findViewById(R.id.bt_dialog_error_close);
        btClose.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setCanceledOnTouchOutside(false);
        message = getArguments().getString("message");
        etMessage.setText(message);
        getDialog().setTitle("Error Message");
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_dialog_error_close) {
            dismiss();
        }
    }
}
