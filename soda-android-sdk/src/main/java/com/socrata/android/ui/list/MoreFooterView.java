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

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.socrata.soda.android.R;

/**
 * Footer view that displays pagination controls
 */
public class MoreFooterView extends LinearLayout {

    /**
     * Loading progress bar
     */
    private ProgressBar progressBar;

    /**
     * 'More' text in the footer
     */
    private TextView text;

    /**
     * @see LinearLayout#LinearLayout(android.content.Context)
     */
    public MoreFooterView(Context context) {
        super(context);
        init();
    }

    /**
     * @see LinearLayout#LinearLayout(android.content.Context, android.util.AttributeSet)
     */
    public MoreFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * @see LinearLayout#LinearLayout(android.content.Context, android.util.AttributeSet, int)
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public MoreFooterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * Private helper to initialize this view
     */
    private void init() {
        setGravity(Gravity.CENTER);
        text = new TextView(getContext());
        text.setPadding(20, 20, 20, 20);
        text.setText(R.string.more);
        addView(text);
        progressBar = new ProgressBar(getContext());
        addView(progressBar);
        showText();
    }

    /**
     * enables the text and hides the progress indicator
     */
    public void showText() {
        text.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    /**
     * enables the progress indicator and hides the text
     */
    public void showLoading() {
        text.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

}
