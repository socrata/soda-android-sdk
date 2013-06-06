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
 * Implemented by query clauses and exception that know how to build themselves transforming their
 * internal state into a query expression.
 */
public interface BuildCapable {

    /**
     * Builds this objects transforming its state into a query expression
     *
     * @return the string value as a query expression
     */
    String build();

}
