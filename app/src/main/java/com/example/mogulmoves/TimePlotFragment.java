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

public class TimePlotFragment extends DialogFragment {
    private OnFragmentInteractionListener listener;
    // private List<Time> timeData = new ArrayList<>();
    private List<Float> floatData = new ArrayList<>();
    private List<Integer> integerData = new ArrayList<>();
    private List<int[]> binomialData = new ArrayList<>();
    private int experimentType;
    private ArrayList<Entry> timePlotData = new ArrayList<>();
    private ArrayList<Entry> timePlotData1 = new ArrayList<>();
    private LineChart tpLineChart;


    // implement time storage whenever i figure out how exactly to store time variables

    public TimePlotFragment(IntegerCountExperiment experiment) {
        // count
        ArrayList<Integer> countTrials = experiment.getTrials();
        for (int i=0; i<countTrials.size(); i++) {
            IntegerCountTrial trial = (IntegerCountTrial) ObjectContext.getObjectById(experiment.getTrials().get(i));
            integerData.add(trial.getCount());
        }
        experimentType = 0;
    }

    public TimePlotFragment(NonNegativeCountExperiment experiment) {
        // non negative count
        ArrayList<Integer> countTrials = experiment.getTrials();
        for (int i=0; i<countTrials.size(); i++) {
            NonNegativeCountTrial trial = (NonNegativeCountTrial) ObjectContext.getObjectById(experiment.getTrials().get(i));
            integerData.add(trial.getCount());
        }
        experimentType = 0;
    }

    public TimePlotFragment(BinomialExperiment experiment) {
        // binomial
        ArrayList<Integer> countTrials = experiment.getTrials();
        for (int i=0; i<countTrials.size(); i++) {
            BinomialTrial trial = (BinomialTrial) ObjectContext.getObjectById(experiment.getTrials().get(i));
            int[] trialPair = {trial.getSuccesses(), trial.getFailures()};
            binomialData.add(trialPair);
        }

        experimentType = 1;
    }

    public TimePlotFragment(MeasureExperiment experiment) {
        // measurement
        ArrayList<Integer> countTrials = experiment.getTrials();
        for (int i=0; i<countTrials.size(); i++) {
            MeasureTrial trial = (MeasureTrial) ObjectContext.getObjectById(experiment.getTrials().get(i));
            floatData.add(trial.getMeasurement());
        }
        experimentType = 2;
    }

    public interface OnFragmentInteractionListener {
        void onOkPressed(Experiment newExperiment);
    }

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

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.time_plot_fragment, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        tpLineChart = view.findViewById(R.id.time_plot_graph);
        LineData lineData;
        LineDataSet lineDataSet, lineDataSet1;

        switch(experimentType) {
            case 0:
                for (int i=0; i<integerData.size(); i++) {
                    timePlotData.add(new BarEntry(i, integerData.get(i)));
                }
                break;
            case 1:
                for (int i=0; i<binomialData.size(); i++) {
                    timePlotData.add(new BarEntry(i, binomialData.get(i)[0]));
                    timePlotData1.add(new BarEntry(i, binomialData.get(i)[1]));
                }
                break;
            case 2:
                for (int i=0; i<floatData.size(); i++) {
                    timePlotData.add(new BarEntry(i, floatData.get(i)));
                }
                break;
        }

        if (experimentType == 1) {
            lineDataSet = new LineDataSet(timePlotData, "Successes");
            lineDataSet1 = new LineDataSet(timePlotData, "Failures"); //

            lineData = new LineData(lineDataSet, lineDataSet1);

        }
        else {
            lineDataSet = new LineDataSet(timePlotData, "Data");

            lineData = new LineData(lineDataSet);
        }

        tpLineChart.setData(lineData);
        tpLineChart.invalidate();

        return builder
                .setView(view)
                .setTitle("Time Plot")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                }).create();
    }

}