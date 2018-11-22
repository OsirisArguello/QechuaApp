package com.tdp2.quechuaapp.utils.CustomNotifications;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.tdp2.quechuaapp.R;

public class NotificationActivity extends AppCompatActivity {

    String notificationBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        notificationBody = getIntent().getStringExtra("notificationBody");

        final TextView notificationTextView = findViewById(R.id.notificationLabel);
        notificationTextView.setText(notificationBody);
    }

}
