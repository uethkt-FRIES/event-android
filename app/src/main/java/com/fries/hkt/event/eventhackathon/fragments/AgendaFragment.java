package com.fries.hkt.event.eventhackathon.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fries.hkt.event.eventhackathon.R;
import com.fries.hkt.event.eventhackathon.adapters.AgendaAdapter;

/**
 * Created by tmq on 11/03/2017.
 */

public class AgendaFragment extends Fragment {

    private View rootView;

    public AgendaFragment() {
    }

    public static AgendaFragment newInstance() {
        return new AgendaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_agenda, container, false);
        initViews();
        return rootView;
    }

    private void initViews() {
        final RecyclerView rvAgenda = (RecyclerView) rootView.findViewById(R.id.rv_agenda);
        final LinearLayoutManager ll = new LinearLayoutManager(getContext());
        rvAgenda.setLayoutManager(ll);
        AgendaAdapter agendaAdapter = new AgendaAdapter(getContext());
        rvAgenda.setAdapter(agendaAdapter);

        rootView.postDelayed(new Runnable() {
            @Override
            public void run() {
//                ll.scrollToPositionWithOffset(10, 300);
                ll.smoothScrollToPosition(rvAgenda, new RecyclerView.State(), 10);
            }
        }, 3000);
    }
}
