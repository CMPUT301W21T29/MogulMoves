package com.example.mogulmoves;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent the data from the experiment's trials as a time plot based on when the data was collected.
 */

public class TimePlotFragment extends DialogFragment {
    private OnFragmentInteractionListener listener;
    private List<Float> floatData = new ArrayList<>();
    private List<Integer> integerData = new ArrayList<>();
    private List<Float> binomialData = new ArrayList<>();
    private int experimentType;
    private LineChart tpLineChart;
    private int numPoints, graphWidth = 0;
    private List<Long> timeData = new ArrayList<>();
    private long minTimeValue;
    private String timeInterval;
    private float maxTimeNum;


    // implement time storage whenever i figure out how exactly to store time variables

    /**
     * Creates a time plot fragment for an IntegerCountExperiment.
     *
     * @param experiment the experiment which the class is creating a time plot for.
     */
    public TimePlotFragment(IntegerCountExperiment experiment) {
        // count
        ArrayList<Integer> countTrials = experiment.getTrials();
        int totalCount = 0;
        for (int i=0; i<countTrials.size(); i++) {
            IntegerCountTrial trial = (IntegerCountTrial) ObjectContext.getTrialById(experiment.getTrials().get(i));
            if (i == 0) {
                minTimeValue = trial.getTimestamp();
            }
            integerData.add(totalCount + trial.getCount());
            timeData.add(trial.getTimestamp() - minTimeValue);
        }
        experimentType = 0;
    }

    /**
     * Creates a time plot fragment for an NonNegativeCountExperiment.
     *
     * @param experiment the experiment which the class is creating a time plot for.
     */
    public TimePlotFragment(NonNegativeCountExperiment experiment) {
        // non negative count
        ArrayList<Integer> countTrials = experiment.getTrials();
        float totalCount = 0;
        for (int i=0; i<countTrials.size(); i++) {
            NonNegativeCountTrial trial = (NonNegativeCountTrial) ObjectContext.getTrialById(experiment.getTrials().get(i));
            if (i == 0) {
                minTimeValue = trial.getTimestamp();
            }
            totalCount += trial.getCount();
            floatData.add(totalCount / (i+1));
            timeData.add(trial.getTimestamp() - minTimeValue);
        }
        experimentType = 2;
    }

    /**
     * Creates a time plot fragment for an BinomialExperiment.
     *
     * @param experiment the experiment which the class is creating a time plot for.
     */
    public TimePlotFragment(BinomialExperiment experiment) {
        // binomial

        ArrayList<Integer> binomialTrials = experiment.getTrials();
        float numSuccesses = 0;
        for (int i=0; i<binomialTrials.size(); i++) {
            BinomialTrial trial = (BinomialTrial) ObjectContext.getTrialById(experiment.getTrials().get(i));
            if (i == 0) {
                minTimeValue = trial.getTimestamp();
            }
            if (trial.getIsSuccess()) {
                 numSuccesses++;
            }
            binomialData.add(numSuccesses / (i+1)); // trial's success rate up until that point
            timeData.add(trial.getTimestamp() - minTimeValue);
        }

        experimentType = 1;
    }

    /**
     * Creates a time plot fragment for an MeasureExperiment.
     *
     * @param experiment the experiment which the class is creating a time plot for.
     */
    public TimePlotFragment(MeasureExperiment experiment) {
        // measurement
        ArrayList<Integer> countTrials = experiment.getTrials();
        float totalCount = 0;
        for (int i=0; i<countTrials.size(); i++) {
            MeasureTrial trial = (MeasureTrial) ObjectContext.getTrialById(experiment.getTrials().get(i));
            if (i == 0) {
                minTimeValue = trial.getTimestamp();
            }
            totalCount += trial.getMeasurement();
            floatData.add(totalCount / (i+1));
            timeData.add(trial.getTimestamp() - minTimeValue);
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
     * Builds the actual fragment with the xml file for a time plot fragment.
     *
     * @param savedInstanceState
     * @return a fragment for the time plot
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.time_plot_fragment, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        tpLineChart = (LineChart) view.findViewById(R.id.time_plot_graph);
        LineData lineData;
        LineDataSet lineDataSet;
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        lineDataSet = new LineDataSet(dataValues(), "X: " + timeInterval + " after First Trial");

        dataSets.add(lineDataSet);

        lineData = new LineData(dataSets);

        tpLineChart.setNoDataText("No Data Yet");
        tpLineChart.setData(lineData);
        tpLineChart.invalidate();

        XAxis xAxis = tpLineChart.getXAxis();

        tpLineChart.getAxisLeft().setAxisMinimum(0); // Y axis starting value
        tpLineChart.getAxisRight().setDrawLabels(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularityEnabled(true);
        tpLineChart.getXAxis().setAxisMinimum(0);
        tpLineChart.getDescription().setEnabled(false);
        tpLineChart.setVisibleXRangeMaximum(10);

        if (numPoints < 5) {
            graphWidth = 5;
        }
        else {
            graphWidth = numPoints;
        }
        tpLineChart.getXAxis().setAxisMaximum(maxTimeNum);

        lineDataSet.setLineWidth(2f);
        lineDataSet.setColor(Color.BLUE);

        return builder
                .setView(view)
                .setTitle("Time Plot")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                }).create();
    }

    /**
     * Creates an ArrayList of entries for the database.
     *
     * @return An ArrayList of entries for the database.
     */
    private ArrayList<Entry> dataValues() {
        ArrayList<Entry> timePlotData = new ArrayList<Entry>();
        List<Float> timeDataReformatted = buildTimeList(timeData);

        switch(experimentType) {
            case 0:
                for (int i=0; i<integerData.size(); i++) {
                    timePlotData.add(new Entry(timeDataReformatted.get(i), integerData.get(i)));
                    numPoints++;
                }
                break;
            case 1:
                for (int i=0; i<binomialData.size(); i++) {
                    timePlotData.add(new Entry(timeDataReformatted.get(i), binomialData.get(i)));
                    numPoints++;
                }
                break;
            case 2:
                for (int i=0; i<floatData.size(); i++) {
                    timePlotData.add(new Entry(timeDataReformatted.get(i), floatData.get(i)));
                    numPoints++;
                }
                break;
        }

        return timePlotData;
    }

    /**
     * Creates a List of lengths of time corresponding to each trial (but now reformatted so they're readable).
     *
     * @param timeList a list of all the amounts of time (in ms) since the first trial.
     *
     * @return a List of lengths of time corresponding to each trial (but now reformatted so they're readable).
     */
    private List<Float> buildTimeList(List<Long> timeList) {
        long determiner;

        if (timeList.size() > 0) {
            determiner = timeList.get(timeList.size()-1);
        } else {
            determiner = 0;
        }
      
        List<Float> timeDataNew = new ArrayList<>();

        for (int i=0; i<timeList.size(); i++) {
            if (determiner < 1000 * 60 * 10) {                          // less than 10 minutes between first and last trial
                float numToAdd = timeList.get(i) / 1000;
                timeDataNew.add(numToAdd);
                timeInterval = "Seconds";
            }
            else if (determiner < 1000 * 60 * 60 * 10) {                // between 10 minutes and 10 hours between first and last trials
                float numToAdd = timeList.get(i) / (60* 1000);
                timeDataNew.add(numToAdd);
                timeInterval = "Minutes";
            }
            else if (determiner < 1000 * 60 * 60 * 24 * 5) {           // between 10 hours and 5 days between first and last trials
                float numToAdd = timeList.get(i) / (60 * 60 * 1000);
                timeDataNew.add(numToAdd);
                timeInterval = "Hours";
            }
            else {                                                      // longer than 5 days between first and last trials
                float numToAdd = timeList.get(i) / (24 * 60 * 60 * 1000);
                timeDataNew.add(numToAdd);
                timeInterval = "Days";
            }
        }
        if (timeList.size() > 0) {
            maxTimeNum = timeDataNew.get(timeDataNew.size()-1);
        } else {
            maxTimeNum = 0;
        }
        return timeDataNew;
    }

    /**
     * Creates a new instance of a TimePlotFragment.
     *
     * @param exp_id the object id of the experiment a time plot is needed for.
     *
     * @return a new instance of a TimePlotFragment.
     */
    static TimePlotFragment newInstance(int exp_id) {
        Bundle args = new Bundle();
        args.putSerializable("exp_id", exp_id);

        Experiment experiment = ObjectContext.getExperimentById(exp_id);

        TimePlotFragment fragment;

        if (experiment instanceof BinomialExperiment) {
            fragment = new TimePlotFragment((BinomialExperiment) experiment);

        } else if (experiment instanceof NonNegativeCountExperiment &&
                !(experiment instanceof IntegerCountExperiment)) {
            fragment = new TimePlotFragment((NonNegativeCountExperiment) experiment);

        } else if(experiment instanceof IntegerCountExperiment) {
            fragment = new TimePlotFragment((IntegerCountExperiment) experiment);

        } else {
            fragment = new TimePlotFragment((MeasureExperiment) experiment);
        }

        fragment.setArguments(args);
        return fragment;
    }

}