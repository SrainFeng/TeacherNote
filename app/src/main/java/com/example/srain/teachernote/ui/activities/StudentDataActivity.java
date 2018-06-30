package com.example.srain.teachernote.ui.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.srain.teachernote.R;
import com.example.srain.teachernote.ui.adapters.ExperimentClassAdapter;
import com.example.srain.teachernote.ui.adapters.TeachClassAdapter;
import com.example.srain.teachernote.entity.ExperimentClass;
import com.example.srain.teachernote.entity.ExperimentClassStudentList;
import com.example.srain.teachernote.entity.Student;
import com.example.srain.teachernote.entity.TeachClass;
import com.example.srain.teachernote.entity.TeachClassStudentList;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class StudentDataActivity extends AppCompatActivity {

    private int mStudentId;

    private Student mStudent;

    private Toolbar studentDataToolbar;

    private TextView gradeText, majorText, nameText, numberText;

    private RecyclerView teachClassRecyclerView, experimentClassRecyclerView;

    private TeachClassAdapter teachClassAdapter;

    private ExperimentClassAdapter experimentClassAdapter;

    private List<TeachClass> mTeachClasses = new ArrayList<>();

    private List<ExperimentClass> mExperimentClasses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_data);

        Intent intent = getIntent();
        mStudentId = intent.getIntExtra("student_id", -1);

        teachClassRecyclerView = findViewById(R.id.student_teach_class_list);
        experimentClassRecyclerView = findViewById(R.id.student_experiment_class_list);
        nameText = findViewById(R.id.student_data_name);
        numberText = findViewById(R.id.student_data_number);
        majorText = findViewById(R.id.major_text);
        gradeText = findViewById(R.id.grade_text);

        // Toolbar设置
        studentDataToolbar = findViewById(R.id.student_data_toolbar);
        setSupportActionBar(studentDataToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }

        initTeachClassList();
        initExperimentList();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        teachClassRecyclerView.setLayoutManager(linearLayoutManager);
        teachClassAdapter = new TeachClassAdapter(mTeachClasses);
        teachClassRecyclerView.setAdapter(teachClassAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        experimentClassRecyclerView.setLayoutManager(gridLayoutManager);
        experimentClassAdapter = new ExperimentClassAdapter(mExperimentClasses);
        experimentClassRecyclerView.setAdapter(experimentClassAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getStudentFromDb();
        setDataToTextView();
    }

    private void initTeachClassList(){
        List<TeachClassStudentList> teachClassStudentListList = LitePal
                .where("studentId = ?", mStudentId + "")
                .find(TeachClassStudentList.class);
        if (!teachClassStudentListList.isEmpty()) {
            for (TeachClassStudentList item:teachClassStudentListList) {
                mTeachClasses.add(LitePal.find(TeachClass.class, item.getClassId()));
            }
        }
    }

    private void initExperimentList() {
        List<ExperimentClassStudentList> experimentClassStudentLists = LitePal
                .where("studentId = ?", mStudentId + "")
                .find(ExperimentClassStudentList.class);
        if (!experimentClassStudentLists.isEmpty()) {
            for (ExperimentClassStudentList item:experimentClassStudentLists) {
                mExperimentClasses.add(LitePal.find(ExperimentClass.class, item.getClassId()));
            }
        }
    }

    private void getStudentFromDb() {
        mStudent = LitePal.find(Student.class, mStudentId);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_data_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                Intent intent = new Intent(StudentDataActivity.this, EditStudentDataActivity.class);
                intent.putExtra("student_id", mStudentId);
                startActivity(intent);
                break;
            case android.R.id.home:
                finish();
            default:
                break;
        }
        return true;
    }

    private void setDataToTextView(){
        nameText.setText(mStudent.getName());
        numberText.setText(mStudent.getStudentNumber());
        majorText.setText(mStudent.getMajor());
        gradeText.setText(mStudent.getGrade() + "");
    }
}
