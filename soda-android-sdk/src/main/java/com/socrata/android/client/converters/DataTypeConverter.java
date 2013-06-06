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

import com.socrata.android.client.DataTypesMapper;

import java.lang.reflect.Field;

/**
 * Converts a remote json property to the appropriate type
 */
public interface DataTypeConverter<T> {

    /**
     * Invoked by the DataTypesMapper delagating conversion to its registered converters
     * @param dataTypesMapper the mapper requesting this conversion
     * @param field the java.lang.reflect.Field where the resulting value will be set
     * @param type the SODA type
     * @param value the raw value as interpreted by the JSON deserialization
     * @return the value that will be set on the field
     */
    T getValue(DataTypesMapper dataTypesMapper, Field field, String type, Object value);

}
