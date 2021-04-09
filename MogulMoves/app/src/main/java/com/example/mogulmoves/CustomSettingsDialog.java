package com.example.mogulmoves;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Custom dialog class for ExperimentSettingsFragment.
 */
public class CustomSettingsDialog extends AlertDialog {
    private Button negative;

    public CustomSettingsDialog(Context context, View view) {
        super(context);
        setContentView(view);
        negative = (Button)findViewById(R.id.back2);
    }

    public void setNegativeButton(String buttonText, View.OnClickListener listener) {
        negative.setText(buttonText);
        negative.setOnClickListener(listener);
        negative.setVisibility(View.VISIBLE);
    }

}