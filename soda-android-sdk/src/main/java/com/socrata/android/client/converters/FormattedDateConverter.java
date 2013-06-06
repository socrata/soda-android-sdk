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

package com.socrata.android.client.converters;

import android.util.Log;
import com.socrata.android.client.DataTypesMapper;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @see DataTypeConverter
 */
public class FormattedDateConverter implements DataTypeConverter<Date> {

    private String format;

    public FormattedDateConverter(String format) {
        this.format = format;
    }

    @Override
    public Date getValue(DataTypesMapper dataTypesMapper, Field field, String type, Object value) {
        try {
            return new SimpleDateFormat(format).parse(String.valueOf(value));
        } catch (ParseException e) {
            Log.w("Socrata FormattedDateConverter",String.format("Socrata FormattedDateConverter: unparseable%s", value));
        }
        return null;
    }
}
