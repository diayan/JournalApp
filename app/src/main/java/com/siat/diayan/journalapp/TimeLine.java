package com.siat.diayan.journalapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TimeLine extends AppCompatActivity {

    private TextView dateTextView;
    private TextView contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        dateTextView = (TextView) findViewById(R.id.tv_date_added);
        contentTextView = (TextView) findViewById(R.id.tv_content);


    }
}
