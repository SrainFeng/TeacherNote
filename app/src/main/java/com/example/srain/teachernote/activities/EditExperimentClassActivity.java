package com.example.srain.teachernote.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.srain.teachernote.R;
import com.example.srain.teachernote.entity.ExperimentClass;

import org.litepal.LitePal;

public class EditExperimentClassActivity extends AppCompatActivity {

    Toolbar toolbar;

    EditText classNameText, classCodeText;
    EditText timeText, locationText, teacherText, creditText, describeText;

    ExperimentClass mExperimentClass;

    int mClassId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_experiment_class);

        toolbar = findViewById(R.id.edit_experiment_class_activity_toolbar);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }

        Intent intent = getIntent();
        mClassId = intent.getIntExtra("class_id", -1);

        classNameText = findViewById(R.id.edit_experiment_class_name);
        classCodeText = findViewById(R.id.edit_experiment_class_code);
        timeText = findViewById(R.id.edit_experiment_time_text);
        locationText = findViewById(R.id.edit_experiment_location_text);
        teacherText = findViewById(R.id.edit_experiment_teacher_text);
        creditText = findViewById(R.id.edit_experiment_credit_text);
        describeText = findViewById(R.id.edit_experiment_describe_text);

        setDefaultText();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_data_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.complete:
                mExperimentClass.setName(classNameText.getText().toString());
                mExperimentClass.setClassCode(classCodeText.getText().toString());
                mExperimentClass.setTime(timeText.getText().toString());
                mExperimentClass.setLocation(locationText.getText().toString());
                mExperimentClass.setTeacherName(teacherText.getText().toString());
                float credit = Float.parseFloat(creditText.getText().toString());
                mExperimentClass.setCredit(credit);
                mExperimentClass.setDescribe(describeText.getText().toString());
                mExperimentClass.update(mClassId);

                Toast.makeText(this, "信息保存成功！", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    private void setDefaultText() {
        mExperimentClass = LitePal.find(ExperimentClass.class, mClassId);
        classNameText.setText(mExperimentClass.getName());
        classCodeText.setText(mExperimentClass.getClassCode());
        timeText.setText(mExperimentClass.getTime());
        locationText.setText(mExperimentClass.getLocation());
        teacherText.setText(mExperimentClass.getTeacherName());
        creditText.setText(mExperimentClass.getCredit() + "");
        describeText.setText(mExperimentClass.getDescribe());
    }


}
