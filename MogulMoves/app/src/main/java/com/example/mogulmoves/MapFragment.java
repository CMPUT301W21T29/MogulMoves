package com.example.mogulmoves;

import android.graphics.Camera;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {
    private ArrayList<Double[]> allLocations = new ArrayList<>();

    /**
     * Creates a map display fragment for an IntegerCountExperiment.
     *
     * @param experiment the experiment which the class is creating a time plot for.

    public MapFragment(IntegerCountExperiment experiment) {
        // count
        ArrayList<Integer> countTrials = experiment.getTrials();
        for (int i=0; i<countTrials.size(); i++) {
            IntegerCountTrial trial = (IntegerCountTrial) ObjectContext.getObjectById(experiment.getTrials().get(i));
            allLocations.add(trial.getExperimenterGeo());
        }
    }*/

    /**
     * Creates a map display fragment for an NonNegativeCountExperiment.
     *
     * @param experiment the experiment which the class is creating a time plot for.


    public MapFragment(NonNegativeCountExperiment experiment) {
        // non negative count
        ArrayList<Integer> countTrials = experiment.getTrials();
        for (int i=0; i<countTrials.size(); i++) {
            NonNegativeCountTrial trial = (NonNegativeCountTrial) ObjectContext.getObjectById(experiment.getTrials().get(i));
            allLocations.add(trial.getExperimenterGeo());
        }
    }*/

    /**
     * Creates a map display fragment for an BinomialExperiment.
     *
     * @param experiment the experiment which the class is creating a time plot for.


    public MapFragment(BinomialExperiment experiment) {
        // binomial
        ArrayList<Integer> countTrials = experiment.getTrials();
        for (int i=0; i<countTrials.size(); i++) {
            BinomialTrial trial = (BinomialTrial) ObjectContext.getObjectById(experiment.getTrials().get(i));
            allLocations.add(trial.getExperimenterGeo());
        }
    }*/

    /**
     * Creates a map display fragment for an MeasureExperiment.
     *
     * @param experiment the experiment which the class is creating a time plot for.


    public MapFragment(MeasureExperiment experiment) {
        // measurement
        ArrayList<Integer> countTrials = experiment.getTrials();
        for (int i=0; i<countTrials.size(); i++) {
            MeasureTrial trial = (MeasureTrial) ObjectContext.getObjectById(experiment.getTrials().get(i));
            allLocations.add(trial.getExperimenterGeo());
        }
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment supportMapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);

        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Double[] Edmonton = {53.5461, -113.4938};
                Double[] Vancouver = {49.2827, -123.1207};
                allLocations.add(Edmonton);
                allLocations.add(Vancouver);

                for (int i = 0; i < allLocations.size(); i++) {
                    Double latitude1 = allLocations.get(i)[0];
                    Double longitude1 = allLocations.get(i)[1];
                    LatLng thisPoint = new LatLng(latitude1, longitude1);
                    googleMap.addMarker(new MarkerOptions()
                            .position(thisPoint)
                            .title(latitude1 + " : " + longitude1));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(thisPoint));
                }
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                        googleMap.addMarker(markerOptions);
                    }
                });
            }
        });


        return view;
    }
}