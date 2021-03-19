package com.example.mogulmoves;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class CustomDialog extends AlertDialog {
    private Button positive, negative;

    public CustomDialog(Context context) {
        super(context);
        initialize(context);
    }

    protected CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initialize(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
        initialize(context);
    }

    private void initialize(Context c) {
        //Inflate your layout, get a handle for the buttons

        positive = (Button)findViewById(R.id.increment_button);
        negative = (Button)findViewById(R.id.back_button);

    }

    public void setPositiveButton(String buttonText, View.OnClickListener listener) {
        positive.setText(buttonText);
        positive.setOnClickListener(listener);
        positive.setVisibility(View.VISIBLE);
    }

    public void setNegativeButton(String buttonText, View.OnClickListener listener) {
        negative.setText(buttonText);
        negative.setOnClickListener(listener);
        negative.setVisibility(View.VISIBLE);
    }


}
