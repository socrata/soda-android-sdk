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

import org.json.JSONObject;

import java.util.Map;

/**
 * A response error on Soda response
 */
@SodaEntity
public class ResponseError {

    /**
     * The error code
     */
    private String code;

    /**
     * The error message
     */
    private String message;

    /**
     * Additional user info associated with the error
     */
    private JSONObject data;

    /**
     * The throwable
     */
    private Throwable error;

    /**
     * Default constructor with no info
     */
    public ResponseError() {
    }

    /**
     * Constructs an error response
     *
     * @param code    The error code
     * @param message The error message
     * @param data    Additional user info associated with the error
     */
    public ResponseError(String code, String message, JSONObject data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * @return The error code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return The error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return Additional user info associated with the error
     */
    public JSONObject getData() {
        return data;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
