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

import java.util.Map;

/**
 * Response object returned by calls to the Consumer
 *
 * @param <T> the type of the objects returned in the response
 */
public class Response<T> {

    private int status;

    private Map<String, String> headers;

    private T entity;

    private ResponseError error;

    private Object json;

    protected Response() {
    }

    public int getStatus() {
        return status;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public T getEntity() {
        return entity;
    }

    public ResponseError getError() {
        return error;
    }

    public Object getJson() {
        return json;
    }

    public boolean hasError() {
        return getError() != null;
    }

    public boolean hasEntity() {
        return getEntity() != null;
    }

    protected void setStatus(int status) {
        this.status = status;
    }

    protected void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    protected void setEntity(T entity) {
        this.entity = entity;
    }

    protected void setError(ResponseError error) {
        this.error = error;
    }

    protected void setJson(Object json) {
        this.json = json;
    }
}
