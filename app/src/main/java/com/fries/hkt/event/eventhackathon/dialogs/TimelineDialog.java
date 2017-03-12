package com.fries.hkt.event.eventhackathon.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fries.hkt.event.eventhackathon.R;
import com.fries.hkt.event.eventhackathon.models.ITimeLine;
import com.fries.hkt.event.eventhackathon.utils.CommonVls;
import com.fries.hkt.event.eventhackathon.utils.MyTime;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by TooNies1810 on 3/11/17.
 */

public class TimelineDialog extends Dialog implements View.OnClickListener {

    private static final String TAG = TimelineDialog.class.getSimpleName();
    private Button askBtn;
    private Button feedbackBtn;
    private ITimeLine timeLine;
    private int index;


    public TimelineDialog(Context context, ITimeLine timeLine, int index) {
        super(context);
        this.timeLine = timeLine;
        this.index = index;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_agenda_details_full);

        initViews();
    }

    private void initViews() {
        askBtn = (Button) findViewById(R.id.btn_ask);
        feedbackBtn = (Button) findViewById(R.id.btn_feedback);
        feedbackBtn.setOnClickListener(this);

        ((TextView) findViewById(R.id.tv_name)).setText(timeLine.getName());


        ((TextView) findViewById(R.id.tv_place)).setText(timeLine.getPlace());

        ((TextView) findViewById(R.id.tv_description)).setText(timeLine.getDescription());


        if (MyTime.changeDay(timeLine.getStart_time(), timeLine.getEnd_time())) {
            ((TextView) findViewById(R.id.tv_date_time)).setText(MyTime.timeToDayHour(timeLine.getStart_time()) + " - " + MyTime.timeToDayHour(timeLine.getEnd_time()));
        } else {
            ((TextView) findViewById(R.id.tv_date_time)).setText(getTime(timeLine.getStart_time()) + " - " + getTime(timeLine.getEnd_time()));
        }

        long time = timeLine.getEnd_time() - timeLine.getStart_time();
        ((TextView) findViewById(R.id.tv_space_time)).setText("Khoảng: " +MyTime.longtime(time) + " phút");

        askBtn.setOnClickListener(this);

        if (timeLine.getIs_online()) {
            ((CircleImageView) findViewById(R.id.iv_state)).setImageResource(R.color.green);
            askBtn.setEnabled(true);
            askBtn.setAlpha(1f);
            feedbackBtn.setEnabled(true);
            feedbackBtn.setAlpha(1f);

            String related = timeLine.getRelated();
            if (related != null && !related.isEmpty() && related.contains("QA")) {
//                askBtn.setEnabled(true);
//
//                askBtn.setAlpha(1f);
            } else {
                askBtn.setEnabled(false);
                askBtn.setAlpha(0.7f);
            }
        } else {
            ((CircleImageView) findViewById(R.id.iv_state)).setImageResource(R.color.black_54);
            askBtn.setEnabled(false);
            feedbackBtn.setEnabled(false);
            askBtn.setAlpha(0.7f);
            feedbackBtn.setAlpha(0.7f);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ask:
                AskQuestionDialog d1 = new AskQuestionDialog(getContext(), R.style.Theme_AppCompat_Light_Dialog_Alert, index);
                d1.setDlFeed(this);
                d1.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;

                d1.setTitle("");
//                WindowManager.LayoutParams lp1 = new WindowManager.LayoutParams();
//                lp1.copyFrom(d1.getWindow().getAttributes());
//                lp1.width = WindowManager.LayoutParams.MATCH_PARENT;
//                lp1.height = WindowManager.LayoutParams.MATCH_PARENT;

                this.hide();
                d1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                d1.getWindow().setAttributes(lp1);

                d1.show();

                break;
            case R.id.btn_feedback:

                FeedbackDialog d = new FeedbackDialog(getContext(), R.style.Theme_AppCompat_Light_Dialog_Alert, index);
                d.setDlFeed(this);
                d.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                d.setTitle("");

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(d.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;

                this.hide();
                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                d.getWindow().setAttributes(lp);

                d.show();
                break;
            default:
                break;
        }
    }

    private String getTime(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);

        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int mins = calendar.get(Calendar.MINUTE);

        return hours + ":" + mins;
    }
}
