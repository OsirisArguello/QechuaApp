package com.tdp2.quechuaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tdp2.quechuaapp.student.CourseSignUpActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        attachEvents();

    }

    private void attachEvents() {

        Button studentSignUpButton = findViewById(R.id.student_signup_button);

        studentSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent courseSignUpActivity = new Intent(getApplicationContext(), CourseSignUpActivity.class);
                startActivity(courseSignUpActivity);
            }
        });
    }
}
