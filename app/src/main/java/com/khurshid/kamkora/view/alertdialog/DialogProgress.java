package com.khurshid.kamkora.view.alertdialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.khurshid.kamkora.R;

public class DialogProgress extends DialogFragment {

    private static DialogProgress dialogProgress = null;

    public static DialogProgress newInstance() {
        if (dialogProgress == null)
            dialogProgress = new DialogProgress();
        return dialogProgress;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_progress, container, false);
        return view;
    }
}
