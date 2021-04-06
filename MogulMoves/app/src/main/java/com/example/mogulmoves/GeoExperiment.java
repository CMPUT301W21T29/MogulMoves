package com.example.mogulmoves;
import android.location.Location;
import java.io.IOException;
import java.util.ArrayList;

public interface GeoExperiment {
    public boolean getLocationRequired();
    public void setLocationRequired(boolean locationRequired, int user) throws IOException;
    public String GeoExperimentWarning(int user);
    public ArrayList<Location> getAllLocations() throws IOException;
}
