package com.fries.hkt.event.eventhackathon.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.fries.hkt.event.eventhackathon.R;
import com.fries.hkt.event.eventhackathon.utils.CommonVls;
import com.fries.hkt.event.eventhackathon.utils.SharedPreferencesMgr;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by TooNies1810 on 3/11/17.
 */

public class FeedbackDialog extends Dialog {

    private TimelineDialog dlTimeline;

    private DatabaseReference mDatabase;

    private int mIndexOfAgenda;

    private SharedPreferencesMgr sharedPreferencesMgr;

    private Button btnSend;
    private EditText edtFeedback;
    private RatingBar rating;

    public FeedbackDialog(Context context) {
        super(context);
    }

    public FeedbackDialog(@NonNull Context context, @StyleRes int themeResId, int mIndexOfAgenda) {
        super(context, themeResId);
        this.mIndexOfAgenda = mIndexOfAgenda;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_feedback);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        btnSend = (Button) findViewById(R.id.btn_send);
        edtFeedback = (EditText) findViewById(R.id.edt_feedback);
        rating = (RatingBar) findViewById(R.id.ratingBar);
        sharedPreferencesMgr = new SharedPreferencesMgr(getContext());

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
                            FeedbackDialog.this.dismiss();
                            Toast.makeText(getContext(), "Đánh giá xong", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Vui lòng điền cảm nhận của bạn", Toast.LENGTH_SHORT).show();
                }


                /**
                 * rating.getNumStars();
                 * edtFeedback.getText().toString();
                 */


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
