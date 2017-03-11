package com.fries.hkt.event.eventhackathon.holders;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fries.hkt.event.eventhackathon.R;
import com.fries.hkt.event.eventhackathon.dialogs.TimelineDialog;
import com.fries.hkt.event.eventhackathon.models.AgendaItem;
import com.fries.hkt.event.eventhackathon.models.ITimeLine;
import com.fries.hkt.event.eventhackathon.utils.CommonVls;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by tmq on 11/03/2017.
 */


public class ITimeLineHolder extends RecyclerView.ViewHolder {
    public static final int STATE_PASSED = 0;
    public static final int STATE_RESPONDING = 1;
    public static final int STATE_WAITING = 2;
    private static final String TAG = ITimeLine.class.getSimpleName();

    private TextView tvName, tvTime, tvDate, tvPlace;
    private ImageView ivState;
    private View rootView;
    private ProgressBar pbState;
    private View vState;
    private Context mContext;

    private ITimeLine timeLine;

    public ITimeLineHolder(View itemView, Context context) {
        super(itemView);
        rootView = itemView;
        mContext = context;

        // Init views
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        tvDate = (TextView) itemView.findViewById(R.id.tv_date);
        tvPlace = (TextView) itemView.findViewById(R.id.tv_place);
        ivState = (ImageView) itemView.findViewById(R.id.iv_state);
        pbState = (ProgressBar) itemView.findViewById(R.id.pb_state);
        vState = itemView.findViewById(R.id.v_state);

        // Set other
        rootView.setOnClickListener(onClickRootView);
    }

    public void setTimeLine(ITimeLine timeLine){
        this.timeLine = timeLine;

        setAgendaItemText();
        setState();
        setDateTime();
        tvPlace.setText(timeLine.getPlace());
    }

    private View.OnClickListener onClickRootView = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            final Dialog d = new Dialog(mContext, R.style.AppTheme_OverlapStatusBar);
            final TimelineDialog d = new TimelineDialog(mContext);

            d.setContentView(R.layout.dialog_agenda_details_full);
            d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            d.setTitle("");
            d.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(d.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;

            d.show();
            d.getWindow().setAttributes(lp);

            View ivClose = d.findViewById(R.id.iv_close);
            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.dismiss();
                }
            });
        }
    };

    public void setAgendaItemText() {
        tvName.setText(timeLine.getName());
    }

    public void setState() {
        long startTime = timeLine.getStart_time();
        long endTime = timeLine.getEnd_time();
        long currentTime = SystemClock.currentThreadTimeMillis();
        int state = 0;
        if (currentTime<startTime) state = STATE_PASSED;
        else {
            if (currentTime>endTime) state = STATE_WAITING;
            else state = STATE_RESPONDING;
        }

        int resId = 0;
        switch (state) {
            case AgendaItem.STATE_PASSED:
                resId = R.drawable.ic_agenda_passed;
                tvName.setTextColor(CommonVls.getColor(R.color.black_54, mContext));
                tvName.setTypeface(null, Typeface.NORMAL);
//                rootView.setBackgroundColor(CommonVls.getColor(R.color.black_6, context));
//                rootView.setAlpha(0.6f);
                pbState.setVisibility(View.GONE);
                vState.setVisibility(View.INVISIBLE);
                break;
            case AgendaItem.STATE_RESPONDING:
                resId = android.R.color.transparent;
                tvName.setTextColor(CommonVls.getColor(R.color.black_87, mContext));
                tvName.setTypeface(null, Typeface.BOLD);
//                rootView.setBackgroundColor(CommonVls.getColor(R.color.white, context));
                pbState.setVisibility(View.VISIBLE);
//                rootView.setAlpha(1f);
                vState.setVisibility(View.VISIBLE);
                break;
            case AgendaItem.STATE_WAITING:
                resId = R.drawable.ic_agenda_waiting;
                tvName.setTextColor(CommonVls.getColor(R.color.black_54, mContext));
                tvName.setTypeface(null, Typeface.NORMAL);
//                rootView.setBackgroundColor(CommonVls.getColor(R.color.white, context));
                pbState.setVisibility(View.GONE);
//                rootView.setAlpha(1f);
                vState.setVisibility(View.INVISIBLE);
                break;
        }
        ivState.setImageResource(resId);
    }

    private void setDateTime(){
//        long milliseconds = Long.parseLong(timeLine.getStart_time());
        long milliseconds = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        int hours = calendar.get(Calendar.HOUR);
        int mins = calendar.get(Calendar.MINUTE);
        String am_pm = (calendar.get(Calendar.AM_PM) == Calendar.AM) ? "AM" : "PM";
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH)+1;
        int year = calendar.get(Calendar.YEAR);

        tvTime.setText(hours + ":" + mins + " " + am_pm);
        tvDate.setText(day + ", " + month + " " + year);
    }

    public View getRootView() {
        return rootView;
    }
}
