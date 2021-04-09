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
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
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

        while (integerData.size() > 0) {
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
        // measurement
        ArrayList<Integer> countTrials = experiment.getTrials();
        for (int i=0; i<countTrials.size(); i++) {
            MeasureTrial trial = (MeasureTrial) ObjectContext.getTrialById(experiment.getTrials().get(i));
            floatData.add(trial.getMeasurement());
        }

        List<Float> floatBuffer = new ArrayList<>();

        while (floatData.size() > 0) {


            int occurrences = Collections.frequency(floatData, floatData.get(0));
            occurrencesList.add(occurrences);
            floatBuffer.add(floatData.get(0));

            float numToRemove = floatData.get(0);

            for (int i=0; i<floatData.size(); i++) {
                if (floatData.get(i) == numToRemove) {
                    floatData.remove(floatData.get(i));
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
        BarData barData = new BarData();
        BarDataSet barDataSet, barDataSet1;
        Legend legend = barChart.getLegend();
        LegendEntry[] legendEntries;
        String measureLabel = "";
        int[] colourList;
        String[] labelList;

        switch(experimentType) {
            case 0:
                int maxValue = 0;
                if (integerData.size() > 0) {
                    maxValue = Collections.max(integerData);
                }

                for (int i=0; i<=maxValue; i++) {
                    if (integerData.contains(i)) {
                        histogramData.add(new BarEntry(i+1, occurrencesList.get(integerData.indexOf(i))));
                    }
                    else {
                        histogramData.add(new BarEntry(i+1, 0));
                    }
                    numPoints++;
                }

                barDataSet = new BarDataSet(histogramData, "Data");
                barDataSet.setColor(Color.GREEN);
                barData.addDataSet(barDataSet);

                break;
            case 1:
                for (int i=0; i<binomialData.size(); i++) {
                    histogramData.add(new BarEntry(i+1, binomialData.get(i)));
                    numPoints++;
                }
                barDataSet = new BarDataSet(histogramData, "Successes");
                barDataSet1 = new BarDataSet(histogramData, "Failures");

                colourList = new int[] {Color.GREEN, Color.RED};
                labelList = new String[] {"Success", "Failure"};
                legendEntries = new LegendEntry[2];
                for (int i=0; i<2; i++) {
                    LegendEntry entry = new LegendEntry();
                    entry.formColor = colourList[i];
                    entry.label = labelList[i];
                    legendEntries[i] = entry;
                }
                legend.setCustom(legendEntries);
                legend.setEnabled(true);

                barDataSet.setColor(Color.GREEN);
                barDataSet1.setColors(new int[] {Color.GREEN, Color.RED});
                barData.addDataSet(barDataSet);
                barData.addDataSet(barDataSet1);

                break;
            case 2:
                doubleListSort(floatData, occurrencesList);
                int[] colours = new int[floatData.size()];
                for (int i=0; i<floatData.size(); i++) {
                    histogramData.add(new BarEntry(i+1, floatData.get(i)));

                    switch (occurrencesList.get(i)) {
                        case 1:
                            colours[i] = Color.RED;
                            break;
                        case 2:
                            colours[i] = Color.YELLOW;
                            break;
                        case 3:
                            colours[i] = Color.GREEN;
                            break;
                        case 4:
                            colours[i] = Color.CYAN;
                            break;
                        case 5:
                            colours[i] = Color.BLUE;
                            break;
                        default:
                            if (occurrencesList.get(i) > 0) {
                                colours[i] = Color.MAGENTA;
                            }
                            else {
                                colours[i] = Color.TRANSPARENT;
                            }
                            break;

                    }

                    numPoints++;
                }

                barDataSet = new BarDataSet(histogramData, "Data");
                colourList = new int[] {Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA};
                labelList = new String[] {"1", "2", "3", "4", "5", "6+"};
                legendEntries = new LegendEntry[6];
                for (int i=0; i<6; i++) {
                    LegendEntry entry = new LegendEntry();
                    entry.formColor = colourList[i];
                    entry.label = labelList[i];
                    legendEntries[i] = entry;
                }
                legend.setCustom(legendEntries);
                legend.setEnabled(true);

                barDataSet.setColors(colours);
                barData.addDataSet(barDataSet);

                break;
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

        switch (experimentType) {
            case 0:
                int maxValue = 0;
                if (integerData.size() > 0) {
                    maxValue = Collections.max(integerData);
                }
                barChart.getXAxis().setAxisMaximum(maxValue+2);
                break;
            case 1:
                barChart.getXAxis().setAxisMaximum(4);
                break;
            case 2:
                barChart.getXAxis().setAxisMaximum(floatData.size()+2);
                break;

        }

        return builder
                .setView(view)
                .setTitle("Histogram")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                }).create();
    }

    /**
     * Position i in list1 and list2 go together as a pair of values, so this method sorts list1 and has the elements of list2 follow their counterparts from list1.
     *
     * @param list1
     * @param list2
     *
     * @return list1 (sorted by value), and list2 (sorted by the list1 values).
     */
    private void doubleListSort(List<Float> list1, List<Integer> list2) {
        // sort list1 and have list2 follow with the same elements
        // arr was the sample array
            int n = list1.size();
            float tempFloat = 0;
            int tempInt = 0;
            for(int i=0; i < n; i++){
                for(int j=1; j < (n-i); j++){
                    if(list1.get(j-1) > list1.get(j)){
                        // swap elements in list1
                        tempFloat = list1.get(j-1);
                        list1.set(j-1, list1.get(j));
                        list1.set(j, tempFloat);
                        // swap elements in list2
                        tempInt = list2.get(j-1);
                        list2.set(j-1, list2.get(j));
                        list2.set(j, tempInt);
                    }

                }
            }


    }

    /**
     * Creates a new instance of a HistogramFragment.
     *
     * @param exp_id the object id of the experiment a histogram is needed for.
     *
     * @return a new instance of a HistogramFragment.
     */
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
