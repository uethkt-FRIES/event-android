package com.fries.hkt.event.eventhackathon.fragments;


import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fries.hkt.event.eventhackathon.R;
import com.fries.hkt.event.eventhackathon.utils.SharedPreferencesMgr;
import com.fries.hkt.event.eventhackathon.utils.TouchImageView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MapFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    private TouchImageView ivMap;

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

    private void initViews(View root) {
        ivMap = (TouchImageView) root.findViewById(R.id.iv_map);

        SharedPreferencesMgr preferencesMgr = new SharedPreferencesMgr(getContext());
        String map = preferencesMgr.getEventMap();
        if (map == null) return;
        Picasso.with(getContext()).load(preferencesMgr.getEventMap()).into(ivMap);
    }
}
