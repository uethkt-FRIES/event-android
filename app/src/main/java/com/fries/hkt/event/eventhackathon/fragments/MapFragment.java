package com.fries.hkt.event.eventhackathon.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fries.hkt.event.eventhackathon.R;


public class MapFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public MapFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        initViews();
        return rootView;
    }

    private void initViews(){
        // Todo
    }
}
