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
import com.fries.hkt.event.eventhackathon.adapters.TimeLineAdapter;
import com.fries.hkt.event.eventhackathon.dialogs.TimelineDialog;
import com.fries.hkt.event.eventhackathon.models.AgendaItem;
import com.fries.hkt.event.eventhackathon.models.ITimeLine;
import com.fries.hkt.event.eventhackathon.utils.CommonVls;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by tmq on 11/03/2017.
 */


public class ITimeLineHolder extends RecyclerView.ViewHolder {
    public static final int STATE_PASSED = 0;
    public static final int STATE_RESPONDING = 1;
    public static final int STATE_WAITING = 2;
    private static final String TAG = ITimeLine.class.getSimpleName();

    private TextView tvName, tvTime, tvDate, tvPlace, tvSubDescription;
    private ImageView ivState, ivGift, ivQA, ivTea;
    private View rootView;
    private ProgressBar pbState;
    private View vState;
    private Context mContext;
    private View lineTop;
    private View lineBottom;
    private TimeLineAdapter timeLineAdapter;

    private ITimeLine timeLine;

    public ITimeLineHolder(View itemView, Context context, TimeLineAdapter timeLineAdapter) {
        super(itemView);
        rootView = itemView;
        mContext = context;
        this.timeLineAdapter = timeLineAdapter;

        // Init views
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        tvDate = (TextView) itemView.findViewById(R.id.tv_date);
        tvPlace = (TextView) itemView.findViewById(R.id.tv_place);
        tvSubDescription = (TextView) itemView.findViewById(R.id.tv_sub_description);
        ivState = (ImageView) itemView.findViewById(R.id.iv_state);
        pbState = (ProgressBar) itemView.findViewById(R.id.pb_state);
        vState = itemView.findViewById(R.id.v_state);
        ivGift = (ImageView) itemView.findViewById(R.id.iv_gift);
        ivQA = (ImageView) itemView.findViewById(R.id.iv_qa);
        ivTea = (ImageView) itemView.findViewById(R.id.iv_tea);
        lineBottom = itemView.findViewById(R.id.v_bottom_line);
        lineTop = itemView.findViewById(R.id.v_top_line);

        // Set other
        rootView.setOnClickListener(onClickRootView);
    }

    public void setTimeLine(ITimeLine timeLine) {
        this.timeLine = timeLine;

        if (!timeLine.getEnabled()) {
            rootView.setVisibility(View.GONE);
            return;
        }
        setAgendaItemText();
        setState();
        setDateTime();
        tvPlace.setText(timeLine.getPlace());
        tvSubDescription.setText(timeLine.getDescription());
        setRelated();
        showLine();
    }

    private View.OnClickListener onClickRootView = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            final Dialog d = new Dialog(mContext, R.style.AppTheme_OverlapStatusBar);
            final TimelineDialog d = new TimelineDialog(mContext, timeLine, getAdapterPosition());

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
        int resId = 0;
        Log.i(TAG, "is_online: " + timeLine.getIs_online());
        if (timeLine.getIs_online()) {
            resId = android.R.color.transparent;
            tvName.setTextColor(CommonVls.getColor(R.color.black_87, mContext));
            tvName.setTypeface(null, Typeface.BOLD);
            pbState.setVisibility(View.VISIBLE);
            vState.setVisibility(View.VISIBLE);

            long now= System.currentTimeMillis();
            long end_time = timeLine.getEnd_time();

            if (now > end_time){
                resId = R.drawable.ic_agenda_passed;
                tvName.setTextColor(CommonVls.getColor(R.color.black_54, mContext));
                tvName.setTypeface(null, Typeface.NORMAL);
                pbState.setVisibility(View.GONE);
                vState.setVisibility(View.INVISIBLE);
            }
        } else {
            resId = R.drawable.ic_agenda_waiting;
            tvName.setTextColor(CommonVls.getColor(R.color.black_54, mContext));
            tvName.setTypeface(null, Typeface.NORMAL);
            pbState.setVisibility(View.GONE);
            vState.setVisibility(View.INVISIBLE);
        }
        ivState.setImageResource(resId);
    }

    private void setDateTime() {
        long milliseconds = timeLine.getStart_time();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        int hours = calendar.get(Calendar.HOUR);
        int mins = calendar.get(Calendar.MINUTE);
        String am_pm = (calendar.get(Calendar.AM_PM) == Calendar.AM) ? "AM" : "PM";
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        tvTime.setText(hours + ":" + mins + " " + am_pm);
        tvDate.setText(day + ", " + month + " " + year);
    }

    private void setRelated() {
        String related = timeLine.getRelated();
        ivGift.setVisibility(View.GONE);
        ivTea.setVisibility(View.GONE);
        ivQA.setVisibility(View.GONE);
        if (related == null) return;

        Log.i(TAG, related);
        String[] arr = related.split(";");
        for (String item : arr) {
            if (item.equalsIgnoreCase("gift")) ivGift.setVisibility(View.VISIBLE);
            else if (item.equalsIgnoreCase("tea")) ivTea.setVisibility(View.VISIBLE);
            else if (item.equalsIgnoreCase("qa")) ivQA.setVisibility(View.VISIBLE);
        }
    }

    private void showLine(){
        if(getAdapterPosition() == 0){
            lineTop.setVisibility(View.INVISIBLE);

        }

        if(getAdapterPosition() == timeLineAdapter.getItemCount() - 1){
            lineBottom.setVisibility(View.INVISIBLE);
        }
    }

    public View getRootView() {
        return rootView;
    }
}
