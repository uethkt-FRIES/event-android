package com.fries.hkt.event.eventhackathon.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.fries.hkt.event.eventhackathon.R;
import com.fries.hkt.event.eventhackathon.utils.CommonVls;

/**
 * Created by TooNies1810 on 3/11/17.
 */

public class AskQuestionDialog extends Dialog {

    private TimelineDialog dlTimeline;

    private Button btnSend;
    private EditText edtFeedback;
    private RatingBar rating;

    public AskQuestionDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_ask);

        btnSend = (Button) findViewById(R.id.btn_send);
        edtFeedback = (EditText) findViewById(R.id.edt_feedback);

//        ImageView iv_reviewer = (ImageView) findViewById(R.id.iv_reviewer);
//        iv_reviewer.bringToFront();
//        iv_reviewer.invalidate();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // post to firebase and then close this dialog

                /**
                 * edtFeedback.getText().toString();
                 */

                //AskQuestionDialog.this.dismiss();
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