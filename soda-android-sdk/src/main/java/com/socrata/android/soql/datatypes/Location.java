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

import com.socrata.android.client.SodaEntity;
import com.socrata.android.client.SodaField;

/**
 * Remote SODA datatype for location with latitude, longitude and an address
 */
@SodaEntity
public class Location {

    @SodaField("needs_recoding")
    private boolean needsRecoding;

    @SodaField("longitude")
    private Double longitude;

    @SodaField("latitude")
    private Double latitude;

    @SodaField("human_address")
    private String humanAddress;

    public boolean isNeedsRecoding() {
        return needsRecoding;
    }

    public void setNeedsRecoding(boolean needsRecoding) {
        this.needsRecoding = needsRecoding;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getHumanAddress() {
        return humanAddress;
    }

    public void setHumanAddress(String humanAddress) {
        this.humanAddress = humanAddress;
    }
}
