package com.fries.hkt.event.eventhackathon.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.fries.hkt.event.eventhackathon.R;
import com.fries.hkt.event.eventhackathon.utils.CommonVls;

/**
 * Created by TooNies1810 on 3/11/17.
 */

public class FeedbackDialog extends Dialog {

    private TimelineDialog dlTimeline;

    private Button btnSend;
    private EditText edtFeedback;
    private RatingBar rating;

    public FeedbackDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_feedback);

        btnSend = (Button) findViewById(R.id.btn_send);
        edtFeedback = (EditText) findViewById(R.id.edt_feedback);
        rating = (RatingBar) findViewById(R.id.ratingBar);

//        ImageView iv_reviewer = (ImageView) findViewById(R.id.iv_reviewer);
//        iv_reviewer.bringToFront();
//        iv_reviewer.invalidate();

        LayerDrawable stars = (LayerDrawable) rating.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(CommonVls.getColor(R.color.orange, getContext()), PorterDuff.Mode.SRC_ATOP);
//        stars.getDrawable(1).setColorFilter(getResources().getColor(R.color.starPartiallySelected), PorterDuff.Mode.SRC_ATOP);
//        stars.getDrawable(0).setColorFilter(getResources().getColor(R.color.starNotSelected), PorterDuff.Mode.SRC_ATOP);

        rating.setStepSize(1);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // post to firebase and then close this dialog

                /**
                 * rating.getNumStars();
                 * edtFeedback.getText().toString();
                 */

                //FeedbackDialog.this.dismiss();
            }
        });
    }

    public TimelineDialog getDlFeed() {
        return dlTimeline;
    }

    public void setDlFeed(TimelineDialog dlTimeline) {
        this.dlTimeline = dlTimeline;
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.dlTimeline.show();
    }
}
