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

    public TimelineDialog(Context context, ITimeLine timeLine) {
        super(context);
        this.timeLine = timeLine;
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
        askBtn.setOnClickListener(this);
        feedbackBtn.setOnClickListener(this);

        if (timeLine.getIs_online()) {
            ((CircleImageView) findViewById(R.id.iv_state)).setImageResource(R.color.green);
        } else {
            ((CircleImageView) findViewById(R.id.iv_state)).setImageResource(R.color.black_54);
        }

        ((TextView)findViewById(R.id.tv_place)).setText(timeLine.getPlace());

        ((TextView)findViewById(R.id.tv_date_time)).setText(getTime(timeLine.getStart_time()) + " - " + getTime(timeLine.getEnd_time()));

        long time = (timeLine.getEnd_time() - timeLine.getStart_time())/60000;
        Log.i(TAG, time + "");
        ((TextView)findViewById(R.id.tv_space_time)).setText(time + " phút");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ask:
                Log.i("ok", "ask");
                AskQuestionDialog d1 = new AskQuestionDialog(getContext());
                d1.setDlFeed(this);
                d1.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;

                d1.setTitle("");
                WindowManager.LayoutParams lp1 = new WindowManager.LayoutParams();
                lp1.copyFrom(d1.getWindow().getAttributes());
                lp1.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp1.height = WindowManager.LayoutParams.MATCH_PARENT;

                this.hide();
                d1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                d1.getWindow().setAttributes(lp1);

                d1.show();

                break;
            case R.id.btn_feedback:
                Log.i("ok", "feed");

                FeedbackDialog d = new FeedbackDialog(getContext(), R.style.AppTheme_OverlapStatusBar);
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
