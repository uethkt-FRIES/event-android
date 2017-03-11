package com.fries.hkt.event.eventhackathon.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fries.hkt.event.eventhackathon.R;
import com.fries.hkt.event.eventhackathon.utils.SharedPreferencesMgr;
import com.fries.hkt.event.eventhackathon.utils.TouchImageView;
import com.squareup.picasso.Picasso;


public class MapFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
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
        initViews(rootView);
        return rootView;
    }

    private void initViews(View root){
        TouchImageView ivMap = (TouchImageView) root.findViewById(R.id.iv_map);

        SharedPreferencesMgr sharedPreferencesMgr = new SharedPreferencesMgr(getContext());
        Picasso.with(getContext()).load(sharedPreferencesMgr.getEventMap()).into(ivMap);
    }
}
