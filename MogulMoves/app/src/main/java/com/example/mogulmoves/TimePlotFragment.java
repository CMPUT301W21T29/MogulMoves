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


    // implement time storage whenever i figure out how exactly to store time variables

    /**
     * Creates a time plot fragment for an IntegerCountExperiment.
     *
     * @param experiment the experiment which the class is creating a time plot for.
     */

    public TimePlotFragment(IntegerCountExperiment experiment) {
        // count
        ArrayList<Integer> countTrials = experiment.getTrials();
        for (int i=0; i<countTrials.size(); i++) {
            IntegerCountTrial trial = (IntegerCountTrial) ObjectContext.getObjectById(experiment.getTrials().get(i));
            integerData.add(trial.getCount());
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
        for (int i=0; i<countTrials.size(); i++) {
            NonNegativeCountTrial trial = (NonNegativeCountTrial) ObjectContext.getObjectById(experiment.getTrials().get(i));
            integerData.add(trial.getCount());
        }
        experimentType = 0;
    }

    /**
     * Creates a time plot fragment for an BinomialExperiment.
     *
     * @param experiment the experiment which the class is creating a time plot for.
     */

    public TimePlotFragment(BinomialExperiment experiment) {
        // binomial
        binomialData.add(experiment.getSuccessRate());
        binomialData.add((float) 1.0 - experiment.getSuccessRate());

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


        lineDataSet = new LineDataSet(dataValues(), "Data");

        dataSets.add(lineDataSet);

        lineData = new LineData(dataSets);

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

        if (numPoints < 5) {
            graphWidth = 5;
        }
        else {
            graphWidth = numPoints;
        }
        tpLineChart.getXAxis().setAxisMaximum(graphWidth);

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

    private ArrayList<Entry> dataValues() {
        ArrayList<Entry> timePlotData = new ArrayList<Entry>();

        switch(experimentType) {
            case 0:
                for (int i=0; i<integerData.size(); i++) {
                    timePlotData.add(new Entry(i+1, integerData.get(i)));
                    numPoints++;
                }
                break;
            case 1:
                for (int i=0; i<binomialData.size(); i++) {
                    timePlotData.add(new Entry(i+1, binomialData.get(i)));
                    numPoints++;
                }
                break;
            case 2:
                for (int i=0; i<floatData.size(); i++) {
                    timePlotData.add(new Entry(i+1, floatData.get(i)));
                    numPoints++;
                }
                break;
        }

        return timePlotData;
    }

    static TimePlotFragment newInstance(int exp_id) {
        Bundle args = new Bundle();
        args.putSerializable("exp_id", exp_id);

        Experiment experiment = (Experiment) ObjectContext.getObjectById(exp_id);

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