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

package com.socrata.android.example.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.socrata.android.example.R;
import com.socrata.android.example.fragments.MapExampleFragment;

/**
 * Simple helper to load a map fragment
 */
public class MapExampleActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_frame);
        FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
        MapExampleFragment fragment = new MapExampleFragment();
        t.replace(R.id.container_frame_layout, fragment);
        t.commit();
    }
}
