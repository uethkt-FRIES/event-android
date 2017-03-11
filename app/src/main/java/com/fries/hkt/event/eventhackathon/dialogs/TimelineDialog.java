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

import com.fries.hkt.event.eventhackathon.R;

/**
 * Created by TooNies1810 on 3/11/17.
 */

public class TimelineDialog extends Dialog implements View.OnClickListener{

    private Button askBtn;
    private Button feedbackBtn;

    public TimelineDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_agenda_details_full);

        askBtn = (Button) findViewById(R.id.btn_ask);
        feedbackBtn = (Button) findViewById(R.id.btn_feedback);
        askBtn.setOnClickListener(this);
        feedbackBtn.setOnClickListener(this);
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
}
