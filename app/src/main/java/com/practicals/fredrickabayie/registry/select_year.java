package com.practicals.fredrickabayie.registry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class select_year extends AppCompatActivity implements View.OnClickListener {

    private Button cancel_btn, ok_btn;
    private EditText studentGPA, studentId;
    private RadioGroup select_course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_year);

        select_course = (RadioGroup) findViewById(R.id.select_course);

        studentGPA = (EditText) findViewById(R.id.studentGPA);
        studentId = (EditText) findViewById(R.id.studentId);

        cancel_btn = (Button) findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(this);

        ok_btn = (Button) findViewById(R.id.ok_btn);
        ok_btn.setOnClickListener(this);
    }


    public void onClick(View v) {
        if(v == cancel_btn) {
            setResult(RESULT_CANCELED , null);
            finish();
        }

        if(v == ok_btn) {
//            int selected_course = select_course.getCheckedRadioButtonId();
            String course = ((RadioButton) findViewById(select_course.getCheckedRadioButtonId())).getText().toString();
            Intent data = new Intent();
            data.putExtra("studentId", studentId.getText().toString());
            data.putExtra("studentMajor", course);
            data.putExtra("studentGPA", studentGPA.getText().toString());
            setResult(RESULT_OK, data);
            finish();

        }
    }

}
