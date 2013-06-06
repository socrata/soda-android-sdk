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

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.socrata.android.soql.Query;

/**
 * Main interface to fetch result from the SODA API
 */
public class Consumer {

    /**
     * The domain this consumer is fetching data from
     */
    private String domain;

    /**
     * The SODA API Token for request authentication
     */
    private String token;

    /**
     * The token header
     */
    private static final String TOKEN_HEADER = "X-App-Token";

    /**
     * Internal Async http client
     */
    private AsyncHttpClient client = new AsyncHttpClient();

    /**
     * Default overridable DataTypesMapper
     */
    private DataTypesMapper dataTypesMapper = new DataTypesMapper();

    /**
     * Performs an async get request
     *
     * @param url             relative url
     * @param params          params
     * @param responseHandler handler
     * @param <T>             the type of results expected back after unmarshalling the response
     */
    private <T> void get(String url, RequestParams params, SodaCallbackResponseHandler<T> responseHandler) {
        Log.d("socrata", String.format("Consumer : %s params: %s", url, params));
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    /**
     * Converts a relative url into an absolute url
     *
     * @param relativeUrl the relative url
     * @return the absolute url
     */
    private String getAbsoluteUrl(String relativeUrl) {
        return "https://" + domain + "/resource" + relativeUrl;
    }

    /**
     * Constructs an unauthenticated consumer object
     *
     * @param domain the domain this consumer object will be fetching results from
     */
    public Consumer(String domain) {
        this.domain = domain;
    }

    /**
     * Constructs an authenticated consumer object
     *
     * @param domain the domain this consumer object will be fetching results from
     * @param token  the authentication token
     */
    public Consumer(String domain, String token) {
        this.domain = domain;
        this.token = token;
        client.addHeader(TOKEN_HEADER, token);
    }

    /**
     * @return the domain this consumer object will be fetching results from
     */
    public String getDomain() {
        return domain;
    }

    /**
     * @return the authentication token
     */
    public String getToken() {
        return token;
    }

    /**
     * @return the data type mapper associated to this consumer where mappings will be looked up when unmarshalling the
     *         response to model objects
     */
    public DataTypesMapper getDataTypesMapper() {
        return dataTypesMapper;
    }

    /**
     * @param dataTypesMapper the data type mapper associated to this consumer where mappings will be looked up when
     *                        unmarshalling the response to model objects
     */
    public void setDataTypesMapper(DataTypesMapper dataTypesMapper) {
        this.dataTypesMapper = dataTypesMapper;
    }

    /**
     * Asynchronously fetches a single object from a remote dataset optionally mapping it to an object
     *
     * @param dataset  the remote dataset
     * @param id       the object id
     * @param mapping  an optional mapping to unmarshall json to instances of the mapping class
     * @param callback a callback implementation where results will be delivered
     * @param <T>      the type of result e.g. EarthQuake
     */
    public <T> void getObject(String dataset, String id, Class<T> mapping, Callback<T> callback) {
        SodaCallbackResponseHandler<T> handler = new SodaCallbackResponseHandler<T>(mapping, callback, dataTypesMapper);
        get(String.format("/%s/%s", dataset, id), null, handler);
    }


    /**
     * Asynchronously fetches all remote dataset objects optionally mapping them to a resulting array where each element
     * corresponds to the mapping result
     *
     * @param dataset  the remote dataset
     * @param mapping  an optional mapping to unmarshall json to instances of the mapping class
     * @param callback a callback implementation where results will be delivered
     * @param <T>      the type of result e.g. List<EarthQuake>
     */
    public <T> void getObjects(String dataset, Class<?> mapping, Callback<T> callback) {
        SodaCallbackResponseHandler<T> handler = new SodaCallbackResponseHandler<T>(mapping, callback, dataTypesMapper);
        get(String.format("/%s", dataset), null, handler);
    }


    /**
     * Asynchronously fetches all remote dataset objects matching a SOQL query expressed as a String optionally
     * mapping the results to a list where each element corresponds to the mapping result parameter
     *
     * @param dataset  the remote dataset
     * @param query    a free form SODA query as a string
     * @param mapping  an optional mapping to unmarshall json to instances of the mapping class
     * @param callback a callback implementation where results will be delivered
     * @param <T>      the type of result e.g. List<EarthQuake>
     */
    public <T> void getObjects(String dataset, String query, Class<?> mapping, Callback<T> callback) {
        SodaCallbackResponseHandler<T> handler = new SodaCallbackResponseHandler<T>(mapping, callback, dataTypesMapper);
        RequestParams params = new RequestParams();
        params.put("$query", query);
        get(String.format("/%s", dataset), params, handler);
    }


    /**
     * Asynchronously fetches all remote dataset objects matching a SOQL query expressed as a SODAQuery
     * mapping the results to a list where each element corresponds to the mapping result parameter
     *
     * @param query    a typed query containing mapping and dataset information
     * @param callback a callback implementation where results will be delivered
     * @param <T>      the type of result e.g. List<EarthQuake>
     */
    public <T> void getObjects(Query query, Callback<T> callback) {
        getObjects(query.getDataset(), query.build(), query.getMapping(), callback);
    }

    /**
     * Asynchronously fetches all remote dataset objects matching a full text query expressed as a String
     * mapping the results to a list where each element corresponds to the mapping result parameter
     *
     * @param dataset  the remote dataset
     * @param keywords the keywords to search for
     * @param mapping  an optional mapping to unmarshall json to instances of the mapping class
     * @param callback a callback implementation where results will be delivered
     * @param <T>      the type of result e.g. List<EarthQuake>
     */
    public <T> void searchObjects(String dataset, String keywords, Class<?> mapping, Callback<T> callback) {
        SodaCallbackResponseHandler<T> handler = new SodaCallbackResponseHandler<T>(mapping, callback, dataTypesMapper);
        RequestParams params = new RequestParams();
        params.put("$s", keywords);
        get(String.format("/%s", dataset), params, handler);
    }

}
