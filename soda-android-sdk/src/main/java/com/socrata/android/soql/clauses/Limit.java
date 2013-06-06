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

package com.socrata.android.soql.clauses;

/**
 * A limit x clause that limits the results returned by the API
 */
public class Limit implements BuildCapable {

    /**
     * The max number of results per page
     */
    private Integer limit;

    /**
     * Constructs a limit clause
     *
     * @param limit The max number of results per page
     */
    public Limit(Integer limit) {
        this.limit = limit;
    }

    /**
     * @return The max number of results per page
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * @see com.socrata.android.soql.clauses.BuildCapable#build()
     */
    @Override
    public String build() {
        return getLimit() == null ? "" : String.format("limit %d", getLimit());
    }
}
