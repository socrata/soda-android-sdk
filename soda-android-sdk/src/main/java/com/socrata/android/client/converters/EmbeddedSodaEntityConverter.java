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
import com.socrata.android.client.JsonAdapter;
import com.socrata.android.client.SodaField;
import com.socrata.android.client.SodaTypeConversionException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

/**
 * Converter used for embedded SODA objects annotated with SodaEntity
 * @see DataTypeConverter
 */
@SuppressWarnings("unchecked")
public class EmbeddedSodaEntityConverter implements DataTypeConverter<Object> {

    @Override
    public Object getValue(DataTypesMapper dataTypesMapper, Field field, String type, Object value) {
        Class<?> target = field.getType();
        JsonAdapter<?> adapter = new JsonAdapter(target, dataTypesMapper);
        Object result = null;
        if (value != null) {
            try {
                if (JSONArray.class.isAssignableFrom(value.getClass())) {
                    result = adapter.fromJsonArray((JSONArray) value);
                } else if (JSONObject.class.isAssignableFrom(value.getClass())) {
                    //SODA does not provide header type values for embedded datatypes so we create mappings for the default properties
                    for (Field innerField : target.getDeclaredFields()) {
                        String sodaFieldName = innerField.isAnnotationPresent(SodaField.class) ? innerField.getAnnotation(SodaField.class).value() : innerField.getName();
                        adapter.addFieldMapping(sodaFieldName, DataTypesMapper.UNKNOWN_TYPE);
                    }
                    result = adapter.fromJsonObject((JSONObject) value);
                }
            } catch (IllegalAccessException e) {
                throw new SodaTypeConversionException(e);
            } catch (InstantiationException e) {
                throw new SodaTypeConversionException(e);
            } catch (JSONException e) {
                throw new SodaTypeConversionException(e);
            }
        }
        return result;
    }

}
