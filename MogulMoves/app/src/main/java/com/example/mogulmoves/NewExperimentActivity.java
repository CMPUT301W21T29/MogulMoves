package com.example.mogulmoves;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import static android.view.View.VISIBLE;
import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class NewExperimentActivity extends AppCompatActivity {

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_experiment);

        spinner = findViewById(R.id.experiment_type);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.experiment_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }

    public void goBack (View view)
    {
        finish();
    }

    public void publishExperiment(View view) {

        EditText description = findViewById(R.id.experiment_description);
        EditText region = findViewById(R.id.experiment_region);
        EditText minimum = findViewById(R.id.minimum_trials);
        CheckBox location_req = findViewById(R.id.location_required);

        String description_string = description.getText().toString();
        String region_string = region.getText().toString();
        String minimum_int_str = minimum.getText().toString();
        boolean location_bool = location_req.isChecked();

        TextView error = findViewById(R.id.error_message);

        if (description_string.equals("") || region_string.equals("") || minimum_int_str.equals("")) {
            error.setVisibility(VISIBLE);
        } else {

            if (region_string.equals("Region (optional)")) {
                region_string = NULL;
            }
            int minimum_int = Integer.parseInt(minimum_int_str);

            String type = spinner.getSelectedItem().toString();
            switch (type) {
                case "Count": {
                    IntegerCountExperiment experiment = new IntegerCountExperiment(69420, description_string, region_string, minimum_int, location_bool);
                    ObjectContext.addExperiment(experiment);
                    break;
                }
                case "Binomial": {
                    BinomialExperiment experiment = new BinomialExperiment(69420, description_string, region_string, minimum_int, location_bool);
                    ObjectContext.addExperiment(experiment);
                    break;
                }
                case "Non-Negative Count": {
                    NonNegativeCountExperiment experiment = new NonNegativeCountExperiment(69420, description_string, region_string, minimum_int, location_bool);
                    ObjectContext.addExperiment(experiment);
                    break;
                }
                default: {
                    MeasureExperiment experiment = new MeasureExperiment(69420, description_string, region_string, minimum_int, location_bool);
                    ObjectContext.addExperiment(experiment);
                    break;
                }
            }

            finish();
        }

    }

}