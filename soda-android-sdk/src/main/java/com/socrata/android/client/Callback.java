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

package com.socrata.android.client;

/**
 * Request callback invoked when the server returns results after unmarshalling to model objects
 * @param <T> the type of result e.g. List<EarthQuake>
 */
public interface Callback<T> {

    void onResults(Response<T> response);

}
