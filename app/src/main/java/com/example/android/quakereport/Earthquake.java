package com.example.android.quakereport;

public class Earthquake {
    //magnitude of the earthquake on the ritcher scale
    private double mMagnitude;

    //magnitude of the earthquake on the ritcher scale
    private String mQuakeDetailsUrl;

    /**Name of place where earthquake occured*/
    private String mPlaceEarthquakeOcurred;

    /**Date earthquake occurred*/
    private long mDateOfOccurrence;

    /**Create a new Earthquake object.
     * @param magnitude is the magnitude of the earthquake.
     * @param  placeOccurred is the name of place the quake occurred.
     * @param dateOfOccurrence is time in milliseconds (unix time) when the earthquake occurred.
     */
    public Earthquake(double magnitude, String placeOccurred, long dateOfOccurrence, String quakeUrl){
        mMagnitude = magnitude;
        mPlaceEarthquakeOcurred = placeOccurred;
        mDateOfOccurrence = dateOfOccurrence;
        mQuakeDetailsUrl = quakeUrl;
    }

    /**Get magnitude of the earthquake*/
    public double getMagnitude(){
        return mMagnitude;
    }

    /**Get name of place earthquake occurred*/
    public String getPlaceEarthquakeOccurred(){return mPlaceEarthquakeOcurred;}

    /**Get date quake occurred*/
    public long getDateOfOccurrence(){return mDateOfOccurrence;}

    /**Get date quake details url*/
    public String getQuakeUrl(){return mQuakeDetailsUrl;}
}
