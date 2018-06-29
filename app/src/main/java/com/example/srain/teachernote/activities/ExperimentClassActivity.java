package com.example.srain.teachernote.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.srain.teachernote.R;
import com.example.srain.teachernote.adapters.ExperimentAdapter;
import com.example.srain.teachernote.entity.Experiment;
import com.example.srain.teachernote.entity.ExperimentClass;
import com.example.srain.teachernote.entity.ExperimentList;
import com.example.srain.teachernote.fragments.AddClassDialogFragment;
import com.example.srain.teachernote.fragments.DeleteItemDialogFragment;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class ExperimentClassActivity extends AppCompatActivity implements AddClassDialogFragment.LoginInputListener, DeleteItemDialogFragment.LoginInputListener{

    private Toolbar toolbar;

    private Button studentButton;

    private RecyclerView mExperimentRecyclerView;

    private ExperimentAdapter adapter;

    private int classId;

    private ExperimentClass mExperimentClass;

    private TextView classNameText, classCodeText;

    private TextView timeText, locationText, teacherText, creditText, describeText;

    private List<Experiment> mExperimentList = new ArrayList<>();

    private List<Integer> mExperimentIdList = new ArrayList<>();

    private List<ExperimentList> mExperimentListList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment_class);

        toolbar = findViewById(R.id.experiment_class_activity_toolbar);
        setSupportActionBar(toolbar);

        final Intent intent = getIntent();
        String classCode = intent.getStringExtra("class_code");


        ExperimentClass experimentClass = LitePal
                .where("classCode = ?", classCode)
                .findFirst(ExperimentClass.class);

        if (experimentClass != null) {
            mExperimentClass = experimentClass;
            classId = experimentClass.getId();
            Log.d("EC", classId + "");
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }

        findExperimentId();
        initExperimentList();
        mExperimentRecyclerView = findViewById(R.id.experiment_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mExperimentRecyclerView.setLayoutManager(gridLayoutManager);
        adapter = new ExperimentAdapter(mExperimentList);
        mExperimentRecyclerView.setAdapter(adapter);


        classNameText = findViewById(R.id.experiment_class_name);
        classCodeText = findViewById(R.id.experiment_class_code);
        timeText = findViewById(R.id.experiment_time_text);
        locationText = findViewById(R.id.experiment_location_text);
        teacherText = findViewById(R.id.experiment_teacher_text);
        creditText = findViewById(R.id.experiment_credit_text);
        describeText = findViewById(R.id.experiment_describe_text);
        studentButton = findViewById(R.id.experiment_student_button);
        mExperimentRecyclerView = findViewById(R.id.experiment_recycler_view);

        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listIntent = new Intent(ExperimentClassActivity.this, ExperimentStudentListActivity.class);
                listIntent.putExtra("class_id", classId);
                startActivity(listIntent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
        initExperimentList();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.experiment_class_show_data_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.experiment_class_edit:
                Intent intent = new Intent(ExperimentClassActivity.this, EditExperimentClassActivity.class);
                intent.putExtra("class_id", classId);
                startActivity(intent);
                break;
            case R.id.experiment_class_add:
                AddClassDialogFragment.setLoginInputListener(this);
                showDialog();
                break;
            case R.id.experiment_class_delete:
                // 搞事情
                DeleteItemDialogFragment.setListener(this);
                showDeleteDialog();
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

    private void findExperimentId() {
        mExperimentIdList.clear();
        mExperimentListList = LitePal.where("classId = ?", classId + "").find(ExperimentList.class);
        if(!mExperimentListList.isEmpty()) {
            for (ExperimentList experimentList:mExperimentListList) {
                mExperimentIdList.add(experimentList.getExperimentId());
            }
        }
    }

    private void initExperimentList() {
        mExperimentList.clear();
        for (int id:mExperimentIdList) {
            mExperimentList.add(LitePal.find(Experiment.class,id));
        }
    }

    private void showDialog(){
        AddClassDialogFragment addClassDialogFragment = AddClassDialogFragment.addDialogFragmentCreator();
        addClassDialogFragment.show(getSupportFragmentManager(), "addExperimentClassDialog");
    }

    private void showDeleteDialog() {
        String[] items = new String[mExperimentList.size()];
        boolean[] itemStatus = new boolean[mExperimentList.size()];
        for(int i = 0; i < mExperimentList.size(); i++) {
            items[i] = mExperimentList.get(i).getTitle();
            itemStatus[i] = false;
        }

        DeleteItemDialogFragment deleteItemDialogFragment = DeleteItemDialogFragment.DeleteItemDialogCreator(items, itemStatus);
        FragmentManager fragmentManager = getSupportFragmentManager();
        deleteItemDialogFragment.show(fragmentManager, "deleteExperiment");
    }

    @Override
    public void onLogInputComplete(String addClassName, String addClassCode) {
        ExperimentList experimentList;
        boolean isExist = false;
        for (Experiment item:mExperimentList) {
            if (item.getCode().equals(addClassCode)) {
                isExist = true;
                break;
            }
        }

        if(!isExist){
            List<Experiment> experiments = LitePal.where("code = ?", addClassCode).find(Experiment.class);
            if (experiments.isEmpty()) {
                Experiment experiment = new Experiment();
                experiment.setCode(addClassCode);
                experiment.setTitle(addClassName);
                experiment.save();

                int eId = experiment.getId();
                experimentList = new ExperimentList();
                experimentList.setClassId(classId);
                experimentList.setExperimentId(eId);
                experimentList.save();

                mExperimentIdList.add(eId);
                initExperimentList();
                adapter.notifyDataSetChanged();
                Toast.makeText(this,"成功添加实验", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"该实验编号已被使用", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this,"该实验编号已存在", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onLogInputComplete(boolean[] itemStatus) {
        for (int i = 0; i < itemStatus.length; i++) {
            if (itemStatus[i]) {
                Experiment experiment = mExperimentList.get(i);
                int eId = experiment.getId();

                LitePal.deleteAll(ExperimentList.class, "experimentId = ?", eId + "");
                LitePal.delete(Experiment.class, eId);
                findExperimentId();
                initExperimentList();
                adapter.notifyDataSetChanged();
            }
        }
        Toast.makeText(this, "已删除实验", Toast.LENGTH_SHORT).show();
    }
}
