package com.example.mogulmoves;

import android.location.Location;

import com.google.type.LatLng;

import java.io.IOException;

public interface GeoTrial {
    //public void addExperimenterGeo();
    public LatLng getExperimenterGeo() throws IOException;
    //public boolean setGeoRequired();
}
