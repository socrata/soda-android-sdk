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

package com.socrata.android.soql.datatypes;


import com.socrata.android.soql.clauses.BuildCapable;
import com.socrata.android.soql.utils.BuildUtils;

/**
 * A bounding box datatype for doing geo queries.
 */
public class GeoBox implements BuildCapable {

    private double north;

    private double east;

    private double south;

    private double west;

    public GeoBox(double north, double east, double south, double west) {
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
    }

    public double getNorth() {
        return north;
    }

    public double getEast() {
        return east;
    }

    public double getSouth() {
        return south;
    }

    public double getWest() {
        return west;
    }

    @Override
    public String build() {
        return BuildUtils.join(", ", getNorth(), getEast(), getSouth(), getWest());
    }
}
