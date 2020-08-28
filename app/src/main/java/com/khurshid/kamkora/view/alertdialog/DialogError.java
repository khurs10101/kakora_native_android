package com.khurshid.kamkora.view.alertdialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.khurshid.kamkora.R;

public class DialogError extends DialogFragment {

    private static DialogError dialogError;
    private EditText etMessage;
    private String message;

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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setCanceledOnTouchOutside(false);
        message = getArguments().getString("message");
        getDialog().setTitle("Error Message");
    }
}
