package com.fries.hkt.event.eventhackathon.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fries.hkt.event.eventhackathon.R;
import com.fries.hkt.event.eventhackathon.models.Notification;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationDialog extends DialogFragment {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.content)
    TextView content;

    public NotificationDialog() {
        // Required empty public constructor
    }

    Notification notification;

    public static NotificationDialog newInstance(Notification notification) {
        NotificationDialog fragment = new NotificationDialog();
        fragment.notification = notification;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification_dialog, container, false);
        ButterKnife.bind(this, view);
        String titleString = String.format("<b>%s</b>", notification.getTitle());
        Spanned html = Html.fromHtml(titleString);
        title.setText(html);
        content.setText(notification.getContent());
        return view;
    }

}
