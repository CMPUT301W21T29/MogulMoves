package com.example.mogulmoves;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent the data from the experiment's trials as a histogram based on when the data was collected.
 */

public class HistogramFragment extends DialogFragment {
    private OnFragmentInteractionListener listener;
    private List<Float> floatData = new ArrayList<>();
    private List<Integer> integerData = new ArrayList<>();
    private List<int[]> binomialData = new ArrayList<>();
    private BarChart barChart;
    private int experimentType;
    private ArrayList<BarEntry> histogramData = new ArrayList<>();
    private ArrayList<BarEntry> histogramData1 = new ArrayList<>();

    // different constructors for the fragment based on what kind of experiment is needing a histogram

    /**
     * Creates a histogram fragment for an IntegerCountExperiment.
     *
     * @param experiment the experiment which the class is creating a time plot for.
     */
    public HistogramFragment(IntegerCountExperiment experiment) {
        // count
        ArrayList<Integer> countTrials = experiment.getTrials();
        for (int i=0; i<countTrials.size(); i++) {
            IntegerCountTrial trial = (IntegerCountTrial) ObjectContext.getObjectById(experiment.getTrials().get(i));
            integerData.add(trial.getCount());
        }
        experimentType = 0;
    }

    /**
     * Creates a histogram fragment for an NonNegativeCountExperiment.
     *
     * @param experiment the experiment which the class is creating a time plot for.
     */

    public HistogramFragment(NonNegativeCountExperiment experiment) {
        // non negative count
        ArrayList<Integer> countTrials = experiment.getTrials();
        for (int i=0; i<countTrials.size(); i++) {
            NonNegativeCountTrial trial = (NonNegativeCountTrial) ObjectContext.getObjectById(experiment.getTrials().get(i));
            integerData.add(trial.getCount());
        }
        experimentType = 0;
    }

    /**
     * Creates a histogram fragment for an BinomialExperiment.
     *
     * @param experiment the experiment which the class is creating a time plot for.
     */

    public HistogramFragment(BinomialExperiment experiment) {
        // binomial
        ArrayList<Integer> countTrials = experiment.getTrials();
        for (int i=0; i<countTrials.size(); i++) {
            BinomialTrial trial = (BinomialTrial) ObjectContext.getObjectById(experiment.getTrials().get(i));
            int[] trialPair = {trial.getSuccesses(), trial.getFailures()};
            binomialData.add(trialPair);
        }

        experimentType = 1;
    }

    /**
     * Creates a histogram fragment for an MeasureExperiment.
     *
     * @param experiment the experiment which the class is creating a time plot for.
     */

    public HistogramFragment(MeasureExperiment experiment) {
        // measurement
        ArrayList<Integer> countTrials = experiment.getTrials();
        for (int i=0; i<countTrials.size(); i++) {
            MeasureTrial trial = (MeasureTrial) ObjectContext.getObjectById(experiment.getTrials().get(i));
            floatData.add(trial.getMeasurement());
        }
        experimentType = 2;
    }

    /**
     * Part of setup for a fragment of any kind.
     */

    public interface OnFragmentInteractionListener {
        void onOkPressed(Experiment newExperiment);
    }

    /**
     * Part of the setup for a fragment of any kind.
     *
     * @param context Part of some android studio setup.
     */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * Builds the actual fragment with the xml file for a histogram fragment.
     *
     * @param savedInstanceState
     * @return a fragment for the time plot
     */

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.histogram_fragment, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        barChart = view.findViewById(R.id.histogram_graph);
        BarData barData;
        BarDataSet barDataSet, barDataSet1;

        switch(experimentType) {
            case 0:
                for (int i=0; i<integerData.size(); i++) {
                    histogramData.add(new BarEntry(i, integerData.get(i)));
                }
                break;
            case 1:
                for (int i=0; i<binomialData.size(); i++) {
                    histogramData.add(new BarEntry(i, binomialData.get(i)[0]));
                    histogramData1.add(new BarEntry(i, binomialData.get(i)[1]));
                }
                break;
            case 2:
                for (int i=0; i<floatData.size(); i++) {
                    histogramData.add(new BarEntry(i, floatData.get(i)));
                }
                break;
        }

        if (experimentType == 1) {
            barDataSet = new BarDataSet(histogramData, "Successes");
            barDataSet1 = new BarDataSet(histogramData, "Failures");

            barData = new BarData(barDataSet, barDataSet1);

            barDataSet.setColor(Color.GREEN);
            barDataSet1.setColor(Color.RED);
        }
        else {
            barDataSet = new BarDataSet(histogramData, "Data");

            barDataSet.setColor(Color.GREEN);

            barData = new BarData(barDataSet);

        }

        barChart.setData(barData);

        XAxis xAxis = barChart.getXAxis();
        String[] months = new String[] {}; // what is going on
        xAxis.setValueFormatter(new IndexAxisValueFormatter(months)); // what is going on pt. 2

        barChart.getAxisLeft().setAxisMinimum(0);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularityEnabled(true);

        float barSpace = 0.02f;
        float groupSpace = 0.3f;
        int groupCount;
        if (experimentType == 1) groupCount = 2;
        else groupCount = 1;

        barData.setBarWidth(0.15f);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * groupCount);
        barChart.groupBars(0, groupSpace, barSpace); // perform the "explicit" grouping

        return builder
                .setView(view)
                .setTitle("Histogram")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                }).create();
    }

}
