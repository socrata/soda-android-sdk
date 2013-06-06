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

import com.socrata.android.client.converters.*;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Datatype mappers that map incoming json values based on their SODA type or default target primitive type
 */
public class DataTypesMapper {

    /**
     * A type placeholder for unknown types on target fields
     */
    public static final String UNKNOWN_TYPE = "__unknown_type__";

    /**
     * Default converter for embedded types that declare @SodaEntity
     */
    private static final DataTypeConverter embeddedEntityConverter = new EmbeddedSodaEntityConverter();

    /**
     * Default conversion map from remote SODA types to model bean fields
     */
    private static final Map<String, DataTypeConverter> converters = new HashMap<String, DataTypeConverter>() {{
        put("percent", new IntegerConverter());
        put("date", new DateTimeStampConverter());
        put("percent", new IntegerConverter());
        put("text", new StringTypeConverter());
        put("dataset_link", new StringTypeConverter());
        put("html", new StringTypeConverter());
        put("money", new DoubleConverter());
        put("calendar_date", new FormattedDateConverter("yyyy-MM-dd'T'HH:mm:ss"));
        put("phone", embeddedEntityConverter);
        put("location", embeddedEntityConverter);
        put("stars", new IntegerConverter());
        put("photo", new StringTypeConverter());
        put("url", embeddedEntityConverter);
        put("document", embeddedEntityConverter);
        put("drop_down_list", new StringTypeConverter());
        put("flag", new StringTypeConverter());
    }};

    /**
     * Default conversion map for unknown remote SODA values to model bean fields
     */
    private static final Map<Class<?>, DataTypeConverter> defaultJavaConverters = new HashMap<Class<?>, DataTypeConverter>() {{
        put(Double.class, new DoubleConverter());
        put(Integer.class, new IntegerConverter());
        put(Boolean.class, new BooleanConverter());
        put(Long.class, new LongConverter());
        put(Date.class, new DateTimeStampConverter());
        put(String.class, new StringTypeConverter());
    }};

    /**
     * Public method to register user converters
     *
     * @param type              the type to register for
     * @param dataTypeConverter the type converter
     */
    public void setConverter(String type, DataTypeConverter dataTypeConverter) {
        converters.put(type, dataTypeConverter);
    }

    /**
     * Public method to register Java types converters
     *
     * @param type              the type to register for
     * @param dataTypeConverter the type converter
     */
    public void setJavaConverter(Class<?> type, DataTypeConverter dataTypeConverter) {
        defaultJavaConverters.put(type, dataTypeConverter);
    }

    /**
     * Resolves a remote incoming value into its Java counterpart
     *
     * @param targetField the field where the value is about to be written to
     * @param type        the remote SODA type
     * @param rawValue    the raw value as interpreted by the JSON serialization
     * @return the value to be set in the field
     */
    public Object getValue(Field targetField, String type, Object rawValue) {
        Object result = rawValue;
        DataTypeConverter converter = converters.get(type);
        if (converter != null) {
            result = converter.getValue(this, targetField, type, rawValue);
        } else {
            Class<?> fieldType = targetField.getType();
            if (fieldType.isAnnotationPresent(SodaEntity.class)) {
                result = embeddedEntityConverter.getValue(this, targetField, type, rawValue);
            } else {
                if (defaultJavaConverters.containsKey(fieldType)) {
                    result = defaultJavaConverters.get(fieldType).getValue(this, targetField, type, rawValue);
                }
            }
        }
        return result;
    }
}
