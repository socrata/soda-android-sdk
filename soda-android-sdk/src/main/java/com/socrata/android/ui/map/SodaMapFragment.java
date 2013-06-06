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

package com.socrata.android.ui.map;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.socrata.android.client.Callback;
import com.socrata.android.client.Consumer;
import com.socrata.android.client.Response;
import com.socrata.android.client.ResponseError;
import com.socrata.android.soql.Query;
import com.socrata.android.soql.datatypes.GeoBox;

import java.util.List;

/**
 * Abstract reusable class for Soda items that want to simply display Soda query results on a map inside a fragment
 *
 * @param <Data> the type of data being displayed
 */
public abstract class SodaMapFragment<Data> extends SupportMapFragment implements GoogleMap.OnCameraChangeListener {

    /**
     * Indicates this map has received data at some point
     */
    private boolean firstLoad = false;

    /**
     * Receives map query lifecycle events
     */
    public interface MapQueryListener<Data> {

        /**
         * Invoked before a query is sent
         */
        void onQueryStarted();

        /**
         * Invoked after a query results are returned
         */
        void onQueryCompleted();

        /**
         * Invoked when there is a response error
         */
        void onQueryResponseError(Response<List<Data>> response);
    }

    /**
     * The default map query listener
     */
    private MapQueryListener mapQueryListener = new MapQueryListener<Data>() {
        @Override
        public void onQueryStarted() {
        }

        @Override
        public void onQueryCompleted() {
        }

        @Override
        public void onQueryResponseError(Response<List<Data>> response) {
            ResponseError responseError = response.getError();
            Log.e("Socrata", responseError.getMessage(), responseError.getError());
            Toast.makeText(getActivity(), responseError.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    /**
     * The soda Consumer associated with this map queries
     */
    private Consumer consumer;

    /**
     * @see SupportMapFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        consumer = getConsumer();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * @see com.google.android.gms.maps.SupportMapFragment#onResume()
     */
    @Override
    public void onResume() {
        super.onResume();
        getMap().setOnCameraChangeListener(this);
    }

    /**
     * Calls a map reload when the viewport updates in order to allow subclasses reload the query if necessary
     *
     * @see GoogleMap.OnCameraChangeListener#onCameraChange(com.google.android.gms.maps.model.CameraPosition)
     */
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        reload();
    }

    /**
     * Sets the map query listener
     *
     * @param mapQueryListener the map query listener
     */
    public void setMapQueryListener(MapQueryListener mapQueryListener) {
        this.mapQueryListener = mapQueryListener;
    }

    /**
     * @return the consumer associated with this maps requests
     */
    public abstract Consumer getConsumer();

    /**
     * The query associated with this current viewport geo box (ne, sw)
     *
     * @param box the Geo box
     * @return the query
     */
    public abstract Query getQuery(GeoBox box);

    /**
     * Converts a model entity into its marker representation
     *
     * @param data the model
     * @return the marker representation
     */
    public abstract MarkerOptions toMarkerOptions(Data data);

    /**
     * @return whether this map should reload when its viewport camera is updated by movement or zoom
     */
    public boolean reloadOnMapChanged() {
        return true;
    }

    /**
     * Reloads the map data and refreshes the markers
     */
    public void reload() {
        if (!firstLoad || reloadOnMapChanged()) {
            LatLngBounds bounds = getMap().getProjection().getVisibleRegion().latLngBounds;
            GeoBox box = Query.box(bounds.northeast.latitude, bounds.northeast.longitude, bounds.southwest.latitude, bounds.southwest.longitude);

            Log.d("socrata", "Reload: " + box.toString());

            mapQueryListener.onQueryStarted();
            consumer.getObjects(getQuery(box), new Callback<List<Data>>() {
                @Override
                public void onResults(Response<List<Data>> response) {
                    if (response.hasError()) {
                        mapQueryListener.onQueryResponseError(response);
                    } else {
                        loadData(response.getEntity());
                        mapQueryListener.onQueryCompleted();
                    }
                }
            });
        }
        firstLoad = true;
    }

    /**
     * Private helper that requests model entities in the returned results are transformed into their
     * marker representation
     *
     * @param data the returned results
     */
    private void loadData(List<Data> data) {
        for (Data item : data) {
            MarkerOptions marker = toMarkerOptions(item);
            getMap().addMarker(marker);
        }
    }

}
