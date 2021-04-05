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

    public MapFragment(Experiment experiment) {
        ArrayList<Integer> trials = experiment.getTrials();
        for (int i=0; i<trials.size(); i++) {
            I
        }
    }

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
                LatLng Edmonton = new LatLng(53.5461, -113.4938);
                googleMap.addMarker(new MarkerOptions()
                        .position(Edmonton)
                        .title("try display marker"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(Edmonton));
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