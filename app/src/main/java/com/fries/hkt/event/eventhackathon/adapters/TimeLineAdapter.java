package com.fries.hkt.event.eventhackathon.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fries.hkt.event.eventhackathon.R;
import com.fries.hkt.event.eventhackathon.holders.ITimeLineHolder;
import com.fries.hkt.event.eventhackathon.models.ITimeLine;
import com.fries.hkt.event.eventhackathon.utils.SharedPreferencesMgr;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by tmq on 11/03/2017.
 */


public class TimeLineAdapter extends RecyclerView.Adapter<ITimeLineHolder> {

    private static final String TAG = TimeLineAdapter.class.getSimpleName();
    private ArrayList<ITimeLine> listAgendaItem;
    private Context mContext;

    public TimeLineAdapter(Context context) {
        mContext = context;

        listAgendaItem = new ArrayList<>();

        initDataFromFirebase();
    }

    private void initDataFromFirebase() {
        SharedPreferencesMgr preferencesMgr = new SharedPreferencesMgr(mContext);
        String id = preferencesMgr.getEventId();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("/events/" + id + "/timelines");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "data=" + dataSnapshot.toString());
                ArrayList<ITimeLine> arrTemp = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.i(TAG, child.toString());
                    ITimeLine timeLine = child.getValue(ITimeLine.class);
                    if (timeLine.getEnabled()) arrTemp.add(timeLine);
                }
                listAgendaItem = arrTemp;
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public ITimeLineHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_timeline, parent, false);

        return new ITimeLineHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(ITimeLineHolder holder, int position) {
        ITimeLine iTimeLine = listAgendaItem.get(position);

        holder.setTimeLine(iTimeLine);
    }

    @Override
    public int getItemCount() {
        return listAgendaItem.size();
    }
}
