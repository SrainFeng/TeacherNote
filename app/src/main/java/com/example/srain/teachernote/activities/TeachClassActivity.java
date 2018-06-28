package com.example.srain.teachernote.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.srain.teachernote.R;
import com.example.srain.teachernote.entity.TeachClass;

import org.litepal.LitePal;

import java.util.List;

/**
 * 展示教学班的 Activity
 */
public class TeachClassActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private Button studentButton;

    private int classId;

    private TextView classNameText, classCodeText;
    private TextView timeText, locationText, teacherText, creditText, describeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach_class);
        toolbar = findViewById(R.id.teacher_class_activity_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }

        classNameText = findViewById(R.id.teach_class_name);
        classCodeText = findViewById(R.id.teach_class_code);
        timeText = findViewById(R.id.time_text);
        locationText = findViewById(R.id.location_text);
        teacherText = findViewById(R.id.teacher_text);
        creditText = findViewById(R.id.credit_text);
        describeText = findViewById(R.id.describe_text);
        studentButton = findViewById(R.id.student_button);

        studentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeachClassActivity.this, TeachStudentListActivity.class);
                intent.putExtra("class_id", classId);
                startActivity(intent);
            }
        });

        // 读取 intent 中数据
        Intent intent = getIntent();
        String classCode = intent.getStringExtra("class_code");
        // 获取数据库中的数据

        List<TeachClass> teachClasses = LitePal.where("classCode = ?", classCode).find(TeachClass.class);

        if (!teachClasses.isEmpty()) {
            TeachClass teachClass = teachClasses.get(0);
            classId = teachClass.getId();
            Log.d("TeachClassActivity", teachClass.getName());
            // setDataToTextView(teachClass);
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
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.edit:
                Intent intent = new Intent(TeachClassActivity.this, EditTeachClassActivity.class);
                intent.putExtra("class_id", classId);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    private void refreshData(){
        TeachClass teachClass = LitePal.find(TeachClass.class, classId);
        Log.d("TeachClassActivity", "refresh");
        setDataToTextView(teachClass);
    }

    private void setDataToTextView(TeachClass teachClass){
        classNameText.setText(teachClass.getName());
        classCodeText.setText(teachClass.getClassCode());
        timeText.setText(teachClass.getTime());
        locationText.setText(teachClass.getLocation());
        teacherText.setText(teachClass.getTeacherName());
        creditText.setText(teachClass.getCredit() + "");
        describeText.setText(teachClass.getDescribe());
    }
}
