package com.example.srain.teachernote.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.srain.teachernote.R;
import com.example.srain.teachernote.database.ExperimentClass;

import org.litepal.LitePal;

public class ExperimentClassActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private Button studentButton;

    private RecyclerView mExperimentRecyclerView;

    private int classId;

    private ExperimentClass mExperimentClass;

    private TextView classNameText, classCodeText;

    private TextView timeText, locationText, teacherText, creditText, describeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment_class);
        toolbar = findViewById(R.id.experiment_class_activity_toolbar);
        setSupportActionBar(toolbar);

        final Intent intent = getIntent();
        String classCode = intent.getStringExtra("class_code");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }

        classNameText = findViewById(R.id.experiment_class_name);
        classCodeText = findViewById(R.id.experiment_class_code);
        timeText = findViewById(R.id.experiment_time_text);
        locationText = findViewById(R.id.experiment_location_text);
        teacherText = findViewById(R.id.experiment_teacher_text);
        creditText = findViewById(R.id.experiment_credit_text);
        describeText = findViewById(R.id.experiment_describe_text);
        studentButton = findViewById(R.id.experiment_student_button);
        mExperimentRecyclerView = findViewById(R.id.experiment_list);

        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 搞事情
                Intent listIntent = new Intent(ExperimentClassActivity.this, ExperimentStudentListActivity.class);
                listIntent.putExtra("class_id", classId);
                startActivity(listIntent);
            }
        });

        ExperimentClass experimentClass = LitePal
                .where("classCode = ?", classCode)
                .findFirst(ExperimentClass.class);

        if (experimentClass != null) {
            mExperimentClass = experimentClass;
            classId = experimentClass.getId();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_data_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.edit:
                Intent intent = new Intent(ExperimentClassActivity.this, EditExperimentClassActivity.class);
                intent.putExtra("class_id", classId);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    private void refreshData(){
        mExperimentClass = LitePal.find(ExperimentClass.class, classId);
        setDataToTextView();
    }

    private void setDataToTextView(){
        classNameText.setText(mExperimentClass.getName());
        classCodeText.setText(mExperimentClass.getClassCode());
        timeText.setText(mExperimentClass.getTime());
        locationText.setText(mExperimentClass.getLocation());
        teacherText.setText(mExperimentClass.getTeacherName());
        creditText.setText(mExperimentClass.getCredit() + "");
        describeText.setText(mExperimentClass.getDescribe());
    }
}
