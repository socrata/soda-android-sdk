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

import android.view.View;
import android.view.ViewGroup;

/**
 * Interface to be implemented by views subject to the adapter view holder pattern
 * @param <Result> the generic model type
 */
public interface BindableView<Result> {

    /**
     * Gives the implementer a chance to create all subviews and elements that this view will
     * be composed of
     * @param convertView the convert view
     */
    void createViewHolder(View convertView);

    /**
     * Binds the current model to the view
     * @param item the model
     * @param position the current position in the list
     * @param convertView the convert view
     * @param parent the parent view
     */
    void bindView(Result item, int position, View convertView, ViewGroup parent);

}