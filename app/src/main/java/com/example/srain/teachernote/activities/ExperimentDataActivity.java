package com.example.srain.teachernote.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.srain.teachernote.R;
import com.example.srain.teachernote.entity.Experiment;

import org.litepal.LitePal;

public class ExperimentDataActivity extends AppCompatActivity {

    private int mExperimentId;

    private Experiment mExperiment;

    private Toolbar toolbar;

    private TextView titleText, codeText, startTimeText, deadlineText, describeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment_data);

        Intent intent = getIntent();
        mExperimentId = intent.getIntExtra("experiment_id", -1);

        toolbar = findViewById(R.id.experiment_data_activity_toolbar);

        titleText = findViewById(R.id.experiment_data_title);
        codeText = findViewById(R.id.experiment_data_code);
        startTimeText = findViewById(R.id.experiment_data_start_time_text);
        deadlineText = findViewById(R.id.experiment_data_deadline_text);
        describeText = findViewById(R.id.experiment_data_describe_text);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getExperimentFromDb();
        setDataToTextView();
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
                // 启动新活动
                Intent intent = new Intent(this, EditExperimentDataActivity.class);
                intent.putExtra("experiment_id", mExperimentId);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    private void setDataToTextView() {
        titleText.setText(mExperiment.getTitle());
        codeText.setText(mExperiment.getCode());
        startTimeText.setText(mExperiment.getStartTime());
        deadlineText.setText(mExperiment.getDeadline());
        describeText.setText(mExperiment.getDescribe());
    }

    private void getExperimentFromDb() {
        mExperiment = LitePal.find(Experiment.class, mExperimentId);
    }
}
