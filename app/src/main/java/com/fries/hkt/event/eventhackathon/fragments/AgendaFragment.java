package com.fries.hkt.event.eventhackathon.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fries.hkt.event.eventhackathon.R;
import com.fries.hkt.event.eventhackathon.adapters.TimeLineAdapter;

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
        TimeLineAdapter timeLineAdapter = new TimeLineAdapter(getContext());
        rvAgenda.setAdapter(timeLineAdapter);

        rootView.postDelayed(new Runnable() {
            @Override
            public void run() {
//                ll.scrollToPositionWithOffset(10, 300);
                ll.smoothScrollToPosition(rvAgenda, new RecyclerView.State(), 10);
            }
        }, 3000);
    }


//    // ---------------------------------------------------------------------------------------------
//    public class MyCustomLayoutManager extends LinearLayoutManager {
//        private static final float MILLISECONDS_PER_INCH = 50f;
//        private Context mContext;
//
//        public MyCustomLayoutManager(Context context) {
//            super(context);
//            mContext = context;
//        }
//
//        @Override
//        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, final int position) {
//
//            LinearSmoothScroller smoothScroller = new LinearSmoothScroller(mContext) {
//                        //This controls the direction in which smoothScroll looks
//                        //for your view
//                        @Override
//                        public PointF computeScrollVectorForPosition(int targetPosition) {
////                            return MyCustomLayoutManager.this.computeScrollVectorForPosition(targetPosition);
//                            int yDelta = calculateCurrentDistanceToPosition(targetPosition);
//                            return new PointF(0, yDelta);
//                        }
//
//                        //This returns the milliseconds it takes to
//                        //scroll one pixel.
//                        @Override
//                        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
//                            return MILLISECONDS_PER_INCH / displayMetrics.densityDpi;
//                        }
//
//                private int calculateCurrentDistanceToPosition(int targetPosition) {
//                    int targetScrollY = targetPosition * itemHeight;
//                    return targetScrollY - currentScrollY;
//                }
//                    };
//
//            smoothScroller.setTargetPosition(position);
//            startSmoothScroll(smoothScroller);
//        }
//    }
}
