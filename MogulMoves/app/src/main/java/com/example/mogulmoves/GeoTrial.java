package com.example.mogulmoves;

import android.location.Location;
import java.io.IOException;

public interface GeoTrial {
    public void addExperimenterGeo();
    public Location getExperimenterGeo() throws IOException;
}
