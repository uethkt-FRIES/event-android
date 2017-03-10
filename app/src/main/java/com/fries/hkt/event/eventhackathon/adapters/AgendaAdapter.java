package com.fries.hkt.event.eventhackathon.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fries.hkt.event.eventhackathon.R;
import com.fries.hkt.event.eventhackathon.models.AgendaItem;
import com.fries.hkt.event.eventhackathon.holders.AgendaItemHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by tmq on 11/03/2017.
 */


public class AgendaAdapter extends RecyclerView.Adapter<AgendaItemHolder> {

    private static final String TAG = AgendaAdapter.class.getSimpleName();
    private ArrayList<AgendaItem> listAgendaItem;
    private Context mContext;

    public AgendaAdapter(Context context) {
        mContext = context;

        listAgendaItem = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            AgendaItem item = new AgendaItem("Item " + i);
            if (i < 10) item.setState(AgendaItem.STATE_PASSED);
            else if (i == 10) item.setState(AgendaItem.STATE_RESPONDING);
            else item.setState(AgendaItem.STATE_WAITING);

            listAgendaItem.add(item);
        }

        initDataFromFirebase();
    }

    private void initDataFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("agenda-test");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<AgendaItem> arrTemp = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Log.i(TAG, child.toString());
                    arrTemp.add(new AgendaItem(child.toString()));
                }
//                listAgendaItem = arrTemp;
//                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public AgendaItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_agenda, parent, false);

        return new AgendaItemHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(AgendaItemHolder holder, int position) {
        AgendaItem agendaItem = listAgendaItem.get(position);

        holder.setAgendaItemText(agendaItem.info);
        holder.setState(agendaItem.state, mContext);
    }

    @Override
    public int getItemCount() {
        return listAgendaItem.size();
    }
}
