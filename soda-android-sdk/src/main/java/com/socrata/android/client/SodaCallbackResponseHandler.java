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

import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.Header;
import org.apache.http.client.HttpResponseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * An Async http client response handler implementation of {@link JsonHttpResponseHandler}
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class SodaCallbackResponseHandler<T> extends JsonHttpResponseHandler {

    /**
     * SODA Fields Header
     */
    private static final String FIELDS_HEADER = "X-SODA2-Fields";

    /**
     * SODA Types Header
     */
    private static final String TYPES_HEADER = "X-SODA2-Types";

    /**
     * Callback where unmarshalled responses will get serialized
     */
    private Callback<T> callback;

    /**
     * Response object containing status, headers, JSON and entity information for a resulting request
     */
    private Response<T> response;

    /**
     * A JSON adapter that transforms JSON into its Java model representation
     */
    private JsonAdapter jsonAdapter;

    /**
     * Constructs a response handler for a given request
     * @param target the target model class or collection
     * @param callback Callback where unmarshalled responses will get serialized
     * @param dataTypesMapper a data type mapper that transform remote SODA values to model properties
     */
    public SodaCallbackResponseHandler(Class<?> target, Callback<T> callback, DataTypesMapper dataTypesMapper) {
        this.callback = callback;
        this.jsonAdapter = new JsonAdapter(target, dataTypesMapper);
        this.response = new Response<T>();
    }

    /**
     * Invoked upon request success
     * @param status the status code
     * @param headers the response headers
     * @param jsonObject the resulting JSON object
     */
    @Override
    public void onSuccess(int status, Header[] headers, JSONObject jsonObject) {
        super.onSuccess(status, jsonObject);
        parseHeaders(status, headers);
        response.setJson(jsonObject);
        if (jsonObject != null) {
            T entity;
            try {
                entity = (T) jsonAdapter.fromJsonObject(jsonObject);
            } catch (IllegalAccessException e) {
                throw new SodaTypeConversionException(e);
            } catch (InstantiationException e) {
                throw new SodaTypeConversionException(e);
            } catch (JSONException e) {
                throw new SodaTypeConversionException(e);
            }
            response.setEntity(entity);
        }
        callback.onResults(response);
    }

    /**
     * Invoked upon request success
     * @param status the status code
     * @param headers the response headers
     * @param jsonArray the resulting JSON array
     */
    @Override
    public void onSuccess(int status, Header[] headers, JSONArray jsonArray) {
        super.onSuccess(status, jsonArray);
        parseHeaders(status, headers);
        response.setJson(jsonArray);
        if (jsonArray != null) {
            T entity;
            try {
                entity = (T) jsonAdapter.fromJsonArray(jsonArray);
            } catch (IllegalAccessException e) {
                throw new SodaTypeConversionException(e);
            } catch (InstantiationException e) {
                throw new SodaTypeConversionException(e);
            } catch (JSONException e) {
                throw new SodaTypeConversionException(e);
            }
            response.setEntity(entity);
        }
        callback.onResults(response);
    }

    /**
     * Invoked upon request failure
     * @param throwable the failure reason
     * @param jsonObject the resulting JSON object
     */
    @Override
    public void onFailure(Throwable throwable, JSONObject jsonObject) {
        super.onFailure(throwable, jsonObject);
        if (HttpResponseException.class.isAssignableFrom(throwable.getClass())) {
            HttpResponseException exception = (HttpResponseException) throwable;
            parseHeaders(exception.getStatusCode(), null);
            ResponseError responseError = new ResponseError(
                    jsonObject.optString("code"),
                    jsonObject.optString("message"),
                    jsonObject.optJSONObject("data")
            );
            responseError.setError(throwable);
            response.setError(responseError);
            response.setJson(jsonObject);
        }
        callback.onResults(response);
    }

    /**
     * Invoked upon request failure
     * @param throwable the failure reason
     * @param jsonArray the resulting JSON array
     */
    @Override
    public void onFailure(Throwable throwable, JSONArray jsonArray) {
        super.onFailure(throwable, jsonArray);
        if (HttpResponseException.class.isAssignableFrom(throwable.getClass())) {
            HttpResponseException exception = (HttpResponseException) throwable;
            parseHeaders(exception.getStatusCode(), null);
            ResponseError responseError = new ResponseError();
            responseError.setError(throwable);
            response.setError(responseError);
            response.setJson(jsonArray);
        }
        callback.onResults(response);
    }


    /**
     * Private helper that registers field mappings based on the fields and types header values
     * @param status the request status code
     * @param hds the headers
     */
    private void parseHeaders(int status, Header[] hds) {
        response.setStatus(status);
        if (hds != null) {
            Map<String, String> headers = new HashMap<String, String>();
            for (Header header : hds) {
                headers.put(header.getName(), header.getValue());
            }
            response.setHeaders(headers);
            String fieldsJson = headers.get(FIELDS_HEADER);
            String typesJson = headers.get(TYPES_HEADER);
            if (fieldsJson != null && typesJson != null) {
                try {
                    JSONArray fields = new JSONArray(fieldsJson);
                    JSONArray types = new JSONArray(typesJson);
                    for (int i = 0; i < fields.length(); i++) {
                        String field = fields.optString(i);
                        String type = types.optString(i);
                        jsonAdapter.addFieldMapping(field, type);
                    }
                } catch (JSONException e) {
                    throw new SodaTypeConversionException(e);
                }
            }
        }
    }


}