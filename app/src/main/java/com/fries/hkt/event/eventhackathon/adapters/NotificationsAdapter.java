package com.fries.hkt.event.eventhackathon.adapters;


import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fries.hkt.event.eventhackathon.R;
import com.fries.hkt.event.eventhackathon.fragments.NotificationDialog;
import com.fries.hkt.event.eventhackathon.models.ITimeLine;
import com.fries.hkt.event.eventhackathon.models.Notification;
import com.fries.hkt.event.eventhackathon.utils.SharedPreferencesMgr;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hungtran on 3/12/17.
 */

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationItem> {

    private AppCompatActivity mContext;
    private ArrayList<Notification> datas = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public NotificationsAdapter(AppCompatActivity mContext) {
        this.mContext = mContext;
        layoutInflater = LayoutInflater.from(mContext);
    }

    public void initDataFromFirebase() {
        SharedPreferencesMgr preferencesMgr = new SharedPreferencesMgr(mContext);
        String id = preferencesMgr.getEventId();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("/events/" + id + "/notifications");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Notification noti = child.getValue(Notification.class);
                    datas.add(noti);
                }
                Collections.reverse(datas);
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public NotificationItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_noti, parent, false);
        return new NotificationItem(view);
    }

    @Override
    public void onBindViewHolder(NotificationItem holder, int position) {
        holder.fillData(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class NotificationItem  extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time)
        TextView txtTime;

        @BindView(R.id.tv_date)
        TextView txtDate;

        @BindView(R.id.v_top_line)
        View topView;

        @BindView(R.id.v_bottom_line)
        View bottomView;

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.content)
        TextView content;

        public NotificationItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NotificationDialog notificationDialog = NotificationDialog.newInstance(datas.get(getAdapterPosition()));
                    notificationDialog.show(mContext.getSupportFragmentManager(), "notificationdialog");
                }
            });
        }

        public void fillData(Notification notification){
            if (getAdapterPosition() == 0){
                topView.setVisibility(View.INVISIBLE);
            }
            if (getAdapterPosition() == getItemCount() - 1){
                bottomView.setVisibility(View.INVISIBLE);
            }

            if (getItemCount() == 1){
                topView.setVisibility(View.INVISIBLE);
                bottomView.setVisibility(View.INVISIBLE);
            }

            title.setText(notification.getTitle());
            content.setText(notification.getContent());

            long milliseconds = notification.getCreated_at();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliseconds);
            int hours = calendar.get(Calendar.HOUR);
            int mins = calendar.get(Calendar.MINUTE);
            String am_pm = (calendar.get(Calendar.AM_PM) == Calendar.AM) ? "AM" : "PM";
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);

            txtTime.setText(hours + ":" + mins + " " + am_pm);
            txtDate.setText(day + ", " + month + " " + year);
        }

    }
}
