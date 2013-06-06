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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Transform JSON arrays and objects into their model representation
 *
 * @param <T> the type of model
 */
public class JsonAdapter<T> {

    /**
     * The target model class
     */
    private Class<T> target;

    /**
     * SODA fields to Java fields mappings
     */
    private Map<String, Field> fieldMappings;

    /**
     * Data type mapper that converts JSON field values to their java representation
     */
    private DataTypesMapper dataTypesMapper;

    /**
     * Field names to type mappings as returned by the SODA headers or manually composed by the user
     */
    private Map<String, String> fieldsTypesMap = new HashMap<String, String>();

    /**
     * Thread safe local cache of property mappings to avoid continuous iteration over reflection
     */
    private static final ConcurrentHashMap<Class<?>, Map<String, Field>> propertyMappings = new ConcurrentHashMap<Class<?>, Map<String, Field>>();

    /**
     * Constructs a JSON adapter of a target and type mapper
     *
     * @param target          The target model class
     * @param dataTypesMapper Data type mapper that converts JSON field values to their Java representation
     */
    public JsonAdapter(Class<T> target, DataTypesMapper dataTypesMapper) {
        this.target = target;
        this.dataTypesMapper = dataTypesMapper;
        initPropertyMappings();
    }

    /**
     * Private helper to initialize the property mappings
     */
    private void initPropertyMappings() {
        if (!propertyMappings.containsKey(target)) {
            Map<String, Field> mappings = new HashMap<String, Field>();
            for (Field field : target.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(SodaField.class)) {
                    String sodaField = field.getAnnotation(SodaField.class).value();
                    mappings.put(sodaField, field);
                } else {
                    mappings.put(field.getName(), field);
                }
            }
            propertyMappings.putIfAbsent(target, mappings);
        }
        fieldMappings = propertyMappings.get(target);
    }

    /**
     * Unmarshalls a JSON array into a model List<T>
     *
     * @param items the JSON array
     * @return the List<T> model representation
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws JSONException
     */
    public List<T> fromJsonArray(JSONArray items) throws IllegalAccessException, InstantiationException, JSONException {
        List<T> result = new ArrayList<T>(items.length());
        for (int i = 0; i < items.length(); i++) {
            JSONObject jsonObject = items.getJSONObject(i);
            T item = fromJsonObject(jsonObject);
            result.add(item);
        }
        return result;
    }

    /**
     * Unmarshalls a JSON object into a model <T>
     *
     * @param json the JSON object
     * @return the <T> model representation
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws JSONException
     */
    public T fromJsonObject(JSONObject json) throws IllegalAccessException, InstantiationException, JSONException {
        if (!target.isAnnotationPresent(SodaEntity.class)) {
            throw new AssertionError("Target is not annotated with @SodaEntity");
        }
        T model = target.newInstance();
        for (Map.Entry<String, String> fieldTypeEntry : fieldsTypesMap.entrySet()) {
            String field = fieldTypeEntry.getKey();
            String type = fieldTypeEntry.getValue();
            Field targetField = fieldMappings.get(field);
            if (json.has(field) && targetField != null) {
                Object jsonValue = json.opt(field);
                if (jsonValue != null) {
                    Object value = dataTypesMapper.getValue(targetField, type, jsonValue);
                    targetField.set(model, value);
                }
            }
        }
        return model;
    }

    /**
     * Public method to allow field mapping overrides
     *
     * @param field the field
     * @param type  the SODA type mapped to
     */
    public void addFieldMapping(String field, String type) {
        fieldsTypesMap.put(field, type);
    }

}
