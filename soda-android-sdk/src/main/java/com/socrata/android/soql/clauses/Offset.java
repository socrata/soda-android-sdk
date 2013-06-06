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
 * An offset x clause to offset the start record for the returning results for pagination purposes
 */
public class Offset implements BuildCapable {

    /**
     * The record offset
     */
    private Integer offset;

    /**
     * @param offset
     */
    public Offset(Integer offset) {
        this.offset = offset;
    }

    /**
     * @return The record offset
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * @see com.socrata.android.soql.clauses.BuildCapable#build()
     */
    @Override
    public String build() {
        return offset == null ? "" : String.format("offset %d", getOffset());
    }
}
