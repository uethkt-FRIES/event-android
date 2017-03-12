package com.fries.hkt.event.eventhackathon.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.fries.hkt.event.eventhackathon.R;
import com.fries.hkt.event.eventhackathon.utils.CommonVls;
import com.fries.hkt.event.eventhackathon.utils.SharedPreferencesMgr;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

/**
 * Created by TooNies1810 on 3/11/17.
 */

public class AskQuestionDialog extends Dialog {

    private TimelineDialog dlTimeline;

    private Button btnSend;
    private EditText edtFeedback;
    private RatingBar rating;
    private int mIndexOfAgenda;
    private DatabaseReference mDatabase;

    SharedPreferencesMgr sharedPreferencesMgr;

    public AskQuestionDialog(Context context) {
        super(context);
    }

    public AskQuestionDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_ask);
        sharedPreferencesMgr = new SharedPreferencesMgr(getContext());



        btnSend = (Button) findViewById(R.id.btn_send);
        edtFeedback = (EditText) findViewById(R.id.edt_feedback);

//        ImageView iv_reviewer = (ImageView) findViewById(R.id.iv_reviewer);
//        iv_reviewer.bringToFront();
//        iv_reviewer.invalidate();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(edtFeedback.getText())){
                    String urlDB = "/events/" + sharedPreferencesMgr.getEventId() + "/timelines/" + mIndexOfAgenda + "/feedback/";
                    // post to firebase and then close this dialog
                    String key = mDatabase.child(urlDB).push().getKey();
                    HashMap<String, Object> feedBack = new HashMap<String, Object>();
                    feedBack.put("content", edtFeedback.getText().toString());
                    feedBack.put("email", sharedPreferencesMgr.getUserInfo().getEmail());
                    feedBack.put("star", rating.getNumStars());

                    HashMap<String, Object> objectUpdate = new HashMap<String, Object>();
                    objectUpdate.put(urlDB + key, feedBack);

                    mDatabase.updateChildren(objectUpdate, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                           // FeedbackDialog.this.dismiss();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Vui lòng điền cảm nhận của bạn", Toast.LENGTH_SHORT).show();
                }
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