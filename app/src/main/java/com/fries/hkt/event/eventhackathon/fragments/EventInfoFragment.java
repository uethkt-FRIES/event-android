package com.fries.hkt.event.eventhackathon.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.fries.hkt.event.eventhackathon.R;
import com.fries.hkt.event.eventhackathon.utils.SharedPreferencesMgr;


/**
 * A placeholder fragment containing a simple view.
 */

public class EventInfoFragment extends Fragment {


    private static final String TAG = EventInfoFragment.class.getSimpleName();

    public EventInfoFragment() {
    }

    public static EventInfoFragment newInstance() {
        return new EventInfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_info, container, false);
        initViews(rootView);
        return rootView;
    }

    private void initViews(View rootView) {
        SharedPreferencesMgr preferencesMgr = new SharedPreferencesMgr(getContext());
        WebView wvInfo = (WebView) rootView.findViewById(R.id.wv_event_info);
        Log.i(TAG, preferencesMgr.getOverview());
        wvInfo.getSettings().setJavaScriptEnabled(true);
        String html = "<!doctype html><html><head><base href=\"/\"><meta charset=\"utf-8\"></head><body>" + preferencesMgr.getOverview() + "</body></html>";

        WebSettings settings = wvInfo.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        wvInfo.loadData(html, "text/html; charset=utf-8", "utf-8");


    }
}
