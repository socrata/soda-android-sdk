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
 * Remote SODA datatype for a URL
 */
@SodaEntity
public class Url {

    @SodaField("description")
    private String description;

    @SodaField("url")
    private String url;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
