package com.example.mogulmoves;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class QRDisplayFragment extends DialogFragment {

    public QRDisplayFragment() {
        // probably just get whatever info needed to generate the qr code here
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.qr_display_fragment, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("QR Code")
                .setPositiveButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).create();
    }

    static QRDisplayFragment newInstance() {
        Bundle args = new Bundle();
        // args.putSerializable("isQR", isQR);

        QRDisplayFragment fragment = new QRDisplayFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
