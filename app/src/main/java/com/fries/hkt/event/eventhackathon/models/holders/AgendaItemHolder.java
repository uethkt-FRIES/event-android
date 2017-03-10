package com.fries.hkt.event.eventhackathon.models.holders;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fries.hkt.event.eventhackathon.R;
import com.fries.hkt.event.eventhackathon.models.AgendaItem;
import com.fries.hkt.event.eventhackathon.utils.CommonVls;

/**
 * Created by tmq on 11/03/2017.
 */


public class AgendaItemHolder extends RecyclerView.ViewHolder {

    private TextView tvAgendaItem;
    private ImageView ivState;
    private View rootView;
    private ProgressBar pbState;
    private View vState;
    private Context mContext;

    public AgendaItemHolder(View itemView, Context context) {
        super(itemView);
        rootView = itemView;
        mContext = context;

        // Init views
        tvAgendaItem = (TextView) itemView.findViewById(R.id.tv_title_agenda);
        ivState = (ImageView) itemView.findViewById(R.id.iv_agenda_state);
        pbState = (ProgressBar) itemView.findViewById(R.id.pb_state);
        vState = itemView.findViewById(R.id.v_state);

        // Set other
        rootView.setOnClickListener(onClickRootView);
    }

    private View.OnClickListener onClickRootView = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Dialog d = new Dialog(mContext, R.style.AppTheme_OverlapStatusBar);

            d.setContentView(R.layout.dialog_agenda_details_full);
            d.setTitle(tvAgendaItem.getText());
            d.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(d.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;

            d.show();
            d.getWindow().setAttributes(lp);

            ImageView ivClose = (ImageView) d.findViewById(R.id.iv_close);
            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.dismiss();
                }
            });
        }
    };

    public void setAgendaItemText(String info) {
        tvAgendaItem.setText(info);
    }

    public void setState(int state, Context context) {
        int resId = 0;
        switch (state) {
            case AgendaItem.STATE_PASSED:
                resId = R.drawable.ic_agenda_passed;
                tvAgendaItem.setTextColor(CommonVls.getColor(R.color.black_54, context));
                tvAgendaItem.setTypeface(null, Typeface.NORMAL);
//                rootView.setBackgroundColor(CommonVls.getColor(R.color.black_6, context));
//                rootView.setAlpha(0.6f);
                pbState.setVisibility(View.GONE);
                vState.setVisibility(View.INVISIBLE);
                break;
            case AgendaItem.STATE_RESPONDING:
                resId = android.R.color.transparent;
                tvAgendaItem.setTextColor(CommonVls.getColor(R.color.black_87, context));
                tvAgendaItem.setTypeface(null, Typeface.BOLD);
//                rootView.setBackgroundColor(CommonVls.getColor(R.color.white, context));
                pbState.setVisibility(View.VISIBLE);
//                rootView.setAlpha(1f);
                vState.setVisibility(View.VISIBLE);
                break;
            case AgendaItem.STATE_WAITING:
                resId = R.drawable.ic_agenda_waiting;
                tvAgendaItem.setTextColor(CommonVls.getColor(R.color.black_54, context));
                tvAgendaItem.setTypeface(null, Typeface.NORMAL);
//                rootView.setBackgroundColor(CommonVls.getColor(R.color.white, context));
                pbState.setVisibility(View.GONE);
//                rootView.setAlpha(1f);
                vState.setVisibility(View.INVISIBLE);
                break;
        }
        ivState.setImageResource(resId);
    }

    public View getRootView() {
        return rootView;
    }
}
