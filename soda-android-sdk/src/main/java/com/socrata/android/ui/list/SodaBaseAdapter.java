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

package com.socrata.android.ui.list;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.socrata.android.client.Callback;
import com.socrata.android.client.Consumer;
import com.socrata.android.client.Response;
import com.socrata.android.client.ResponseError;
import com.socrata.android.soql.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Base adapter for Soda Enabled List with built in querying capabilities
 *
 * @param <Holder> The type of view subject to the view holder pattern for reusable cells
 * @param <Data>   The type of model being rendered in this adapter and list
 */
public class SodaBaseAdapter<Holder extends BindableView<Data>, Data> extends BaseAdapter {

    /**
     * A listener interface to receive lifecycle callbacks
     */
    public interface SodaAdapterListener<Data> {

        /**
         * Invoked when the query starts
         */
        void onQueryStarted();

        /**
         * Invoked when the query completes
         */
        void onQueryCompleted();

        /**
         * Invoked when a query starts moving the offset for pagination
         */
        void onPaginatedQueryStarted();

        /**
         * Invoked when a query stops moving the offset for pagination
         */
        void onPaginatedQueryCompleted();

        /**
         * Invoked when there is a response error
         */
        void onQueryResponseError(Response<List<Data>> response);
    }

    /**
     * A list of model entities being rendered in this adapter
     */
    private List<Data> data = new ArrayList<Data>();

    /**
     * layout id injected from the holder annotation
     */
    private int layoutId;

    /**
     * @see LayoutInflater
     */
    private LayoutInflater inflater;

    /**
     * The type of holder that will be dynamically instantiated
     */
    private Class<Holder> holderType;

    /**
     * Consumer to the Soda API backend
     */
    private Consumer consumer;

    /**
     * The Soda Adapter Lifecycle listener
     */
    private SodaAdapterListener sodaAdapterListener;

    /**
     * Query that produced the tables results
     */
    private Query query;

    /**
     * Constructs a SodaBaseAdapter from an existing Context and holder type
     * The holder type will be pragmatically instantiated and inspected for SodaHolder annotations
     *
     * @param context    the context bound to this adapter
     * @param holderType the holder type
     */
    public SodaBaseAdapter(Context context, Class<Holder> holderType) {
        inflater = LayoutInflater.from(context);
        this.holderType = holderType;
        if (!holderType.isAnnotationPresent(SodaHolder.class)) {
            throw new IllegalArgumentException(String.format("%s is required in al Holder Types", SodaHolder.class.getName()));
        }
        SodaHolder holderLayoutAnnotation = holderType.getAnnotation(SodaHolder.class);
        int value = holderLayoutAnnotation.value();
        if (value == Integer.MIN_VALUE) {
            String tag = holderLayoutAnnotation.layout();
            this.layoutId = context.getResources().getIdentifier(tag, "layout", context.getPackageName());
        } else {
            this.layoutId = value;
        }
    }

    /**
     * sets the soda adapter listener that will receive callback notifications on this adapter's lifecycle
     *
     * @param sodaAdapterListener the soda adapter listener
     */
    public void setSodaAdapterListener(SodaAdapterListener sodaAdapterListener) {
        this.sodaAdapterListener = sodaAdapterListener;
    }

    /**
     * @return the consumer bound to this adapter used to communicate with the soda api
     */
    public Consumer getConsumer() {
        return consumer;
    }

    /**
     * sets the consumer bound to this adapter used to communicate with the soda api
     *
     * @param consumer the consumer
     */
    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }

    /**
     * Sets this adapter query use to filter server side results
     *
     * @param query the query
     */
    public void setQuery(Query query) {
        if (sodaAdapterListener != null) {
            sodaAdapterListener.onQueryStarted();
        }
        query.setOffset(0);
        this.query = query;
        consumer.getObjects(query, new Callback<List<Data>>() {
            @Override
            public void onResults(Response<List<Data>> response) {
                if (response.hasError()) {
                    sodaAdapterListener.onQueryResponseError(response);
                } else {
                    data.clear();
                    data.addAll(response.getEntity());
                    notifyDataSetChanged();
                    if (sodaAdapterListener != null) {
                        sodaAdapterListener.onQueryCompleted();
                    }
                }
            }
        });
    }

    /**
     * Invoked upon pagination requests moving the query offset forward in order to progressively
     * load more results from the Soda API
     */
    public void obtainMoreData() {
        if (sodaAdapterListener != null) {
            sodaAdapterListener.onPaginatedQueryStarted();
        }
        query.setOffset(getCount());
        consumer.getObjects(query, new Callback<List<Data>>() {
            @Override
            public void onResults(Response<List<Data>> response) {
                data.addAll(response.getEntity());
                notifyDataSetChanged();
                if (sodaAdapterListener != null) {
                    sodaAdapterListener.onPaginatedQueryCompleted();
                }
            }
        });
    }

    /**
     * @see android.widget.BaseAdapter#getCount()
     */
    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    /**
     * @see android.widget.BaseAdapter#getItem(int)
     */
    @Override
    public Data getItem(int i) {
        return data.get(i);
    }

    /**
     * @see android.widget.BaseAdapter#getItemId(int)
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * @see android.widget.BaseAdapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    @SuppressWarnings("unchecked")
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(layoutId, parent, false);
            try {
                holder = holderType.newInstance();
                holder.createViewHolder(convertView);
                if (convertView == null) {
                    throw new AssertionError("convertView was null after inflating attempt");
                }
                convertView.setTag(holder);
            } catch (InstantiationException e) {
                throw new IllegalArgumentException("Exception instantianting view", e);
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException("IllegalAccessException", e);
            }

        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.bindView(getItem(position), position, convertView, parent);
        return convertView;
    }
}
