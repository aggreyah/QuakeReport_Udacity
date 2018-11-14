package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {
    private String myUrl;
    public EarthquakeLoader(Context context, String url) {
        super(context);
        // TODO: Finish implementing this constructor
        myUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Earthquake> loadInBackground() {
        // TODO: Implement this method
        if(myUrl == null){
            return null;
        }
        final ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes(myUrl);
        return earthquakes;
    }
}