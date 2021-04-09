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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class to represent the data from the experiment's trials as a histogram based on when the data was collected.
 */

public class HistogramFragment extends DialogFragment {
    private OnFragmentInteractionListener listener;
    private List<Float> floatData = new ArrayList<>();
    private List<Integer> integerData = new ArrayList<>();
    private List<Integer> binomialData = new ArrayList<>();
    private BarChart barChart;
    private int experimentType;
    private ArrayList<BarEntry> histogramData = new ArrayList<>();
    private int numPoints, graphWidth = 0;
    private List<Integer> occurrencesList = new ArrayList<>();

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
            IntegerCountTrial trial = (IntegerCountTrial) ObjectContext.getTrialById(experiment.getTrials().get(i));
            integerData.add(trial.getCount());
        }

        List<Integer> integerBuffer = new ArrayList<>();

        int originalSize = integerData.size();
        for (int i=0; i<originalSize; i++) {

            int occurrences = Collections.frequency(integerData, integerData.get(i));

            occurrencesList.add(occurrences);
            integerBuffer.add(integerData.get(0));

            for (int j=i; j<integerData.size(); j++) {
                if (integerData.get(j) == integerData.get(0)) {
                    integerData.remove(integerData.get(j));
                }
            }
        }

        for (int i=0; i<integerBuffer.size(); i++) {

            integerData.add(integerBuffer.get(i));
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
            NonNegativeCountTrial trial = (NonNegativeCountTrial) ObjectContext.getTrialById(experiment.getTrials().get(i));
            integerData.add(trial.getCount());
        }

        List<Integer> integerBuffer = new ArrayList<>();



        while (integerData.size() > 0) {

            System.out.print("\nintegerData: ");
            for (int i=0; i<integerData.size(); i++) {
                System.out.print(integerData.get(i) + " ");
            }
            System.out.print("\n");

            int occurrences = Collections.frequency(integerData, integerData.get(0));
            occurrencesList.add(occurrences);
            integerBuffer.add(integerData.get(0));

            int numToRemove = integerData.get(0);

            for (int i=0; i<integerData.size(); i++) {
                if (integerData.get(i) == numToRemove) {
                    integerData.remove(integerData.get(i));
                    i--;
                }
            }
        }

        for (int i=0; i<integerBuffer.size(); i++) {
            integerData.add(integerBuffer.get(i));
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
        binomialData.add(experiment.getTotalSuccesses());
        binomialData.add(experiment.getTotalFailures());

        experimentType = 1;
    }

    /**
     * Creates a histogram fragment for an MeasureExperiment.
     *
     * @param experiment the experiment which the class is creating a time plot for.
     */

    public HistogramFragment(MeasureExperiment experiment) {

        ArrayList<Integer> countTrials = experiment.getTrials();
        for (int i=0; i<countTrials.size(); i++) {
            MeasureTrial trial = (MeasureTrial) ObjectContext.getTrialById(experiment.getTrials().get(i));
            floatData.add(trial.getMeasurement());
        }

        List<Float> floatBuffer = new ArrayList<>();

        for (int i=0; i<floatData.size(); i++) {

            int occurrences = Collections.frequency(floatData, floatData.get(i));

            occurrencesList.add(occurrences);
            floatBuffer.add(floatData.get(0));

            for (int j=i; j<floatData.size(); j++) {
                if (floatData.get(j) == floatData.get(0)) {
                    floatData.remove(floatData.get(j));
                    i--;
                }
            }
        }

        for (int i=0; i<floatBuffer.size(); i++) {

            floatData.add(floatBuffer.get(i));
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

        System.out.print("\nfinal integerData: ");
        for (int i=0; i<integerData.size(); i++) {
            System.out.print(integerData.get(i) + " ");
        }
        System.out.print("\noccurrence list: ");
        for (int i=0; i<integerData.size(); i++) {
            System.out.print(occurrencesList.get(i) + " ");
        }
        System.out.println("\n");

        switch(experimentType) {
            case 0:
                for (int i=0; i<=Collections.max(integerData); i++) {
                    if (integerData.contains(i)) {
                        histogramData.add(new BarEntry(i+1, occurrencesList.get(integerData.indexOf(i))));
                    }
                    else {
                        histogramData.add(new BarEntry(i+1, 0));
                    }
                    numPoints++;
                }
                break;
            case 1:
                for (int i=0; i<binomialData.size(); i++) {
                    histogramData.add(new BarEntry(i+1, binomialData.get(i)));
                    numPoints++;
                }
                break;
            case 2:
                for (int i=0; i<=Collections.max(floatData); i++) {
                    if (floatData.contains(i)) {
                        histogramData.add(new BarEntry(i+1, occurrencesList.get(floatData.indexOf(i))));
                    }
                    else {
                        histogramData.add(new BarEntry(i+1, 0));
                    }
                    numPoints++;
                }

                break;
        }

        if (experimentType == 1) {
            barDataSet = new BarDataSet(histogramData, "Successes");
            barDataSet1 = new BarDataSet(histogramData, "Failures");

            barDataSet.setColor(Color.GREEN);
            barDataSet1.setColors(new int[] {Color.GREEN, Color.RED});

            barData = new BarData(barDataSet, barDataSet1);

        }
        else {
            barDataSet = new BarDataSet(histogramData, "Data");

            barDataSet.setColor(Color.GREEN);

            barData = new BarData();

            barData.addDataSet(barDataSet);
        }

        barChart.setNoDataText("No Data Yet");
        barChart.setData(barData);
        barChart.invalidate();

        XAxis xAxis = barChart.getXAxis();

        barChart.getAxisLeft().setAxisMinimum(0); // Y axis starting value
        barChart.getAxisRight().setDrawLabels(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularityEnabled(true);
        //xAxis.setEnabled(false);
        barChart.getDescription().setEnabled(false);

        float barSpace = 0.02f;
        float groupSpace = 0.3f;

        barData.setBarWidth(0.5f);
        barChart.getXAxis().setAxisMinimum(0);
        if (numPoints < 5) {
            graphWidth = 5;
        }
        else {
            graphWidth = numPoints;
        }
        barChart.getXAxis().setAxisMaximum(Collections.max(integerData)+2);

        return builder
                .setView(view)
                .setTitle("Histogram")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                }).create();
    }

    static HistogramFragment newInstance(int exp_id) {
        Bundle args = new Bundle();
        args.putSerializable("exp_id", exp_id);

        Experiment experiment = ObjectContext.getExperimentById(exp_id);

        HistogramFragment fragment;

        if (experiment instanceof BinomialExperiment) {
            fragment = new HistogramFragment((BinomialExperiment) experiment);

        } else if (experiment instanceof NonNegativeCountExperiment &&
                !(experiment instanceof IntegerCountExperiment)) {
            fragment = new HistogramFragment((NonNegativeCountExperiment) experiment);

        } else if(experiment instanceof IntegerCountExperiment) {
            fragment = new HistogramFragment((IntegerCountExperiment) experiment);

        } else {
            fragment = new HistogramFragment((MeasureExperiment) experiment);
        }


        fragment.setArguments(args);
        return fragment;
    }

    public int getExperimentType() {
        return experimentType;
    }
}
