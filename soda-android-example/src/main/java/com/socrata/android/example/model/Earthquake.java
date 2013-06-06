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

package com.socrata.android.example.model;

import com.socrata.android.client.SodaEntity;
import com.socrata.android.client.SodaField;
import com.socrata.android.soql.datatypes.Location;

import java.util.Date;

/**
 * Model entity representing a remote record on the Soda sample datasets
 * http://dev.socrata.com/consumers/getting-started
 *
 * All Soda entities are annotated with @SodaEntity for automatic serialization of json responses
 * into domain model objects
 */
@SodaEntity
public class Earthquake {

    @SodaField("region")
    private String region;

    @SodaField("source")
    private String source;

    @SodaField("location")
    private Location location;

    @SodaField("magnitude")
    private Double magnitude;

    /**
     * @SodaField allow local properties to be mapped to remote json responses where the field
     * name do not match
     */
    @SodaField("number_of_stations")
    private Integer numberOfStations;

    @SodaField("datetime")
    private Date dateTime;

    @SodaField("earthquake_id")
    private String earthquakeId;

    @SodaField("depth")
    private Double depth;

    @SodaField("version")
    private String version;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Double magnitude) {
        this.magnitude = magnitude;
    }

    public Integer getNumberOfStations() {
        return numberOfStations;
    }

    public void setNumberOfStations(Integer numberOfStations) {
        this.numberOfStations = numberOfStations;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getEarthquakeId() {
        return earthquakeId;
    }

    public void setEarthquakeId(String earthquakeId) {
        this.earthquakeId = earthquakeId;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
