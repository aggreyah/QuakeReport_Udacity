/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class EarthquakeActivity extends
        AppCompatActivity implements LoaderCallbacks <ArrayList<Earthquake>> {
    private EarthquakeAdapter earthQAdapter;
    private static final int EARTHQUAKE_LOADER_ID = 1;
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    private TextView emptyTextView;
    private ProgressBar progressView;
    private ListView earthquakeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        earthQAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());
        progressView = findViewById(R.id.loading_indicator);
        earthquakeListView = findViewById(R.id.list);
        earthquakeListView.setAdapter(earthQAdapter);


        if (isConnected){
            LoaderManager myLoaderManager = getLoaderManager();
            myLoaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Earthquake currentEarthquake = earthQAdapter.getItem(position);
                    Uri earthquakeUrl = Uri.parse(currentEarthquake.getQuakeUrl());
                    Intent openUrlIntent = new Intent(Intent.ACTION_VIEW, earthquakeUrl);
                    startActivity(openUrlIntent);
                }
            });
        }else{
            emptyTextView = findViewById(R.id.list_empty);
            emptyTextView.setText(R.string.no_internet);
            earthquakeListView.setEmptyView(emptyTextView);
            progressView.setVisibility(View.GONE);

        }
    }

    @NonNull
    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> earthquakes) {
        progressView.setVisibility(View.GONE);
//        emptyTextView.setText(R.string.empty_state);
        earthQAdapter.clear();
        if (earthquakes != null && !earthquakes.isEmpty()) {
            earthQAdapter.addAll(earthquakes);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquake>> loader) {
        earthQAdapter.clear();
    }
}
