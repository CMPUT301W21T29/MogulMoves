package com.example.mogulmoves;

import android.location.Location;

import com.google.type.LatLng;

import java.io.IOException;
import java.util.ArrayList;

public interface GeoTrial {
    //public void addExperimenterGeo();
    public Double[] getExperimenterGeo() throws IOException;
    //public boolean setGeoRequired();
}
