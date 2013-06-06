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

package com.socrata.android.example.views;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.socrata.android.example.R;
import com.socrata.android.example.model.Earthquake;
import com.socrata.android.ui.list.BindableView;
import com.socrata.android.ui.list.SodaHolder;

/**
 * @SodaHolder auto implements the view holder pattern for reusable cell instances in list views
 */
@SodaHolder(layout = "earthquake_item")
public class EarthquakeView implements BindableView<Earthquake> {

    private TextView title;

    private TextView magnitude;

    private TextView depth;

    @Override
    public void createViewHolder(View convertView) {
        title = (TextView) convertView.findViewById(R.id.earthquake_item_title);
        magnitude = (TextView) convertView.findViewById(R.id.earthquake_item_magnitude);
        depth = (TextView) convertView.findViewById(R.id.earthquake_item_depth);
    }

    @Override
    public void bindView(Earthquake item, int position, View convertView, ViewGroup parent) {
        title.setText(item.getRegion());
        magnitude.setText(convertView.getContext().getString(R.string.magnitude, item.getMagnitude()));
        depth.setText(convertView.getContext().getString(R.string.depth, item.getDepth()));
    }

}
