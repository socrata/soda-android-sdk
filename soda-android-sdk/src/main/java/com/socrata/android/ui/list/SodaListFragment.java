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


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.socrata.android.client.Consumer;
import com.socrata.android.client.Response;
import com.socrata.android.client.ResponseError;
import com.socrata.android.soql.Query;
import com.socrata.soda.android.R;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Abstract reusable class for Soda list that want to simply display Soda query results on a list
 * view inside a fragment
 *
 * @param <Holder> the view holder type used to render cells
 * @param <Data>   the type of data being displayed
 */
public abstract class SodaListFragment<Holder extends BindableView<Data>, Data> extends Fragment {

    /**
     * The adapter used to transform model entities into the list views
     */
    private SodaBaseAdapter<Holder, Data> adapter;

    /**
     * The underlying list view
     */
    private ListView listView;

    /**
     * The progress bar associated with the async calls to the Soda API
     */
    private ProgressBar progressbar;

    /**
     * A view providing a 'more' button or text to load more results in a paginated fashion
     */
    private MoreFooterView moreFooterView;

    /**
     * @see Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    @SuppressWarnings("unchecked")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.soda_list_default, null);
        if (view == null) {
            throw new AssertionError("R.layout.soda_list_default view is null after inflating");
        }
        listView = (ListView) view.findViewById(android.R.id.list);
        progressbar = (ProgressBar) view.findViewById(R.id.progressbar);
        ParameterizedType typedClass = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] types = typedClass.getActualTypeArguments();
        adapter = new SodaBaseAdapter<Holder, Data>(getActivity(), (Class<Holder>) types[0]);
        adapter.setConsumer(getConsumer());
        adapter.setSodaAdapterListener(new SodaBaseAdapter.SodaAdapterListener<Data>() {
            @Override
            public void onQueryStarted() {
                progressbar.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
            }

            @Override
            public void onQueryCompleted() {
                progressbar.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPaginatedQueryStarted() {
                moreFooterView.showLoading();
            }

            @Override
            public void onPaginatedQueryCompleted() {
                moreFooterView.showText();
            }

            @Override
            public void onQueryResponseError(Response<List<Data>> response) {
                ResponseError responseError = response.getError();
                Log.e("Socrata", responseError.getMessage(), responseError.getError());
                Toast.makeText(getActivity(), responseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        moreFooterView = new MoreFooterView(getActivity());
        listView.addFooterView(moreFooterView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (view.equals(moreFooterView)) {
                    adapter.obtainMoreData();
                } else {
                    SodaListFragment.this.onItemClick(adapterView, view, i, l);
                }
            }
        });
        adapter.setQuery(getQuery());
        return view;
    }

    /**
     * @return the consumer associated with this list activity
     */
    public abstract Consumer getConsumer();

    /**
     * @return the query used to return results from the Soda API
     */
    public abstract Query getQuery();

    /**
     * To be implmented by subclasses that want to provide extra functionality on clicked items
     *
     * @param adapterView the adapter view
     * @param view        the view
     * @param position    the position in the list
     * @param id          the item id
     */
    public abstract void onItemClick(AdapterView<?> adapterView, View view, int position, long id);

}
