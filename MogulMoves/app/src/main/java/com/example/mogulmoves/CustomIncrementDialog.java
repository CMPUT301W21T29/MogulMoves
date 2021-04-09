package com.example.mogulmoves;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Custom dialog class for EditCountTrialFragment.
 */
public class CustomIncrementDialog extends AlertDialog {
    private Button positive;
    private Button negative;

    public CustomIncrementDialog(Context context, View view) {
        super(context);
        setContentView(view);
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
