package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_item, parent, false);
        }

        Earthquake currentQuake = getItem(position);

        TextView magnitudeTextView =  convertView.findViewById(R.id.magnitude);
        DecimalFormat magnitudeFormatter = new DecimalFormat("0.0");
        String magnitudeText = magnitudeFormatter.format(currentQuake.getMagnitude());
        magnitudeTextView.setText(magnitudeText);

        TextView locationOffsetTextView = convertView.findViewById(R.id.location_offset);
        TextView primaryLocationTextView = convertView.findViewById(R.id.primary_location);
        String[] locationStringCombined = currentQuake.getPlaceEarthquakeOccurred().split("(?<=of)");
        String locationOffsetString;
        String primaryLocationString;

        if (locationStringCombined.length == 2){
            locationOffsetString = locationStringCombined[0];
            primaryLocationString = locationStringCombined[1];
        }else{
            locationOffsetString = "Near the";
            primaryLocationString = locationStringCombined[0];
        }

        locationOffsetTextView.setText(locationOffsetString);
        primaryLocationTextView.setText(primaryLocationString);

        Date dateObject = new Date(currentQuake.getDateOfOccurrence());

        TextView dateOfOccurrenceTextView = convertView.findViewById(R.id.date_of_occurrence);
        String formattedDate = formatDate(dateObject);
        dateOfOccurrenceTextView.setText(formattedDate);

        TextView timeOfOccurrenceTextView = convertView.findViewById(R.id.time_of_occurrence);
        String formattedTime = formatTime(dateObject);
        timeOfOccurrenceTextView.setText(formattedTime);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentQuake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);


        return convertView;
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    private String formatTime(Date aTimeObject) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("h:mm a");
        String timeToDisplay = dateFormatter.format(aTimeObject);
        return timeToDisplay;
    }

    private String formatDate(Date aDateObject) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, YYYY");
        String dateToDisplay = dateFormatter.format(aDateObject);
        return dateToDisplay;
    }
}