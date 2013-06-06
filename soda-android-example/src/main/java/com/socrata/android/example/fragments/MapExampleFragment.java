/*
 * SODA Android SDK - Socrata, Inc
 *
 * Copyright (C) 2013 Socrata, Inc
 * All rights reserved.
 *
 * Developed for Socrata, Inc by:
 * 47 Degrees, LLC
 * http://47deg.com
 * hello@47deg.com
 */

package com.socrata.android.example.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.socrata.android.client.Consumer;
import com.socrata.android.example.R;
import com.socrata.android.example.model.Earthquake;
import com.socrata.android.soql.Query;
import com.socrata.android.soql.clauses.OrderDirection;
import com.socrata.android.soql.datatypes.GeoBox;
import com.socrata.android.ui.map.SodaMapFragment;

import static com.socrata.android.soql.clauses.Expression.order;

/**
 * A simple map fragment displaying earthquakes results from the Soda API.
 * Showcases the use of the SodaMapFragment component
 */
public class MapExampleFragment extends SodaMapFragment<Earthquake> {

    private Consumer consumer;

    /**
     * @see SodaMapFragment#onCreateView
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        consumer = new Consumer(getString(R.string.soda_domain));
        //new Consumer(getString(R.string.soda_domain), getString(R.string.soda_token));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Consumer getConsumer() {
        return consumer;
    }

    @Override
    public boolean reloadOnMapChanged() {
        return false;
    }

    @Override
    public Query getQuery(GeoBox geoBox) {
        Query query = new Query("earthquakes", Earthquake.class);
        //query.addWhere(withinBox("location", geoBox));
        query.setLimit(100);
        query.addOrder(order("magnitude", OrderDirection.DESC));
        return query;
    }

    @Override
    public MarkerOptions toMarkerOptions(Earthquake earthquake) {
        LatLng latLng = new LatLng(earthquake.getLocation().getLatitude(), earthquake.getLocation().getLongitude());
        return new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pushpin_default))
                .position(latLng)
                .title(earthquake.getRegion())
                .snippet(getString(R.string.magnitude, earthquake.getMagnitude()));
    }
}
