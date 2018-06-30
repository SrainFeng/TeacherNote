package com.example.srain.teachernote.ui.activities;

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
import com.example.srain.teachernote.entity.Experiment;

import org.litepal.LitePal;

public class EditExperimentDataActivity extends AppCompatActivity {

    private int mExperimentId;

    private Experiment mExperiment;

    private EditText editTitle, editCode, editStartTime, editDeadline, editDescribe;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_experiment_data);

        Intent intent = getIntent();
        mExperimentId = intent.getIntExtra("experiment_id", -1);
        mExperiment = LitePal.find(Experiment.class, mExperimentId);

        bindViews();

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }

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
            case R.id.complete:
                mExperiment.setTitle(editTitle.getText().toString());
                mExperiment.setCode(editCode.getText().toString());
                mExperiment.setStartTime(editStartTime.getText().toString());
                mExperiment.setDeadline(editDeadline.getText().toString());
                mExperiment.setDescribe(editDescribe.getText().toString());
                mExperiment.update(mExperimentId);
                Toast.makeText(this, "信息保存成功！", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                finish();
            default:
                break;
        }
        return true;
    }

    private void setDefaultText() {
        editTitle.setText(mExperiment.getTitle());
        editCode.setText(mExperiment.getCode());
        editStartTime.setText(mExperiment.getStartTime());
        editDeadline.setText(mExperiment.getDeadline());
        editDescribe.setText(mExperiment.getDescribe());
    }

    private void bindViews() {
        toolbar = findViewById(R.id.edit_experiment_data_activity_toolbar);
        editTitle = findViewById(R.id.edit_experiment_data_title);
        editCode = findViewById(R.id.edit_experiment_data_code);
        editStartTime = findViewById(R.id.edit_experiment_data_start_time_text);
        editDeadline = findViewById(R.id.edit_experiment_data_deadline_text);
        editDescribe = findViewById(R.id.edit_experiment_data_describe_text);
    }
}
