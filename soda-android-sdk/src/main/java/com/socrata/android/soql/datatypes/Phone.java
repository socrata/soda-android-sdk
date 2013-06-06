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
 * Remote SODA datatype for a phone number
 */
@SodaEntity
public class Phone {

    @SodaField("phone_number")
    private String number;

    @SodaField("phone_type")
    private String type;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
