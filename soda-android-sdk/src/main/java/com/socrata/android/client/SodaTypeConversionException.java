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
 * An exception raised while unmarshalling JSON to model beans
 */
public class SodaTypeConversionException extends RuntimeException {

    public SodaTypeConversionException() {
        super();
    }

    public SodaTypeConversionException(String detailMessage) {
        super(detailMessage);
    }

    public SodaTypeConversionException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public SodaTypeConversionException(Throwable throwable) {
        super(throwable);
    }
}
