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
import com.example.srain.teachernote.entity.TeachClass;

import org.litepal.LitePal;

/**
 * 编辑更改教学班信息
 */
public class EditTeachClassActivity extends AppCompatActivity {
    Toolbar toolbar;

    EditText classNameText, classCodeText;
    EditText timeText, locationText, teacherText, creditText, describeText;

    TeachClass mTeachClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_teach_class);

        toolbar = findViewById(R.id.edit_teacher_class_activity_toolbar);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }

        classNameText = findViewById(R.id.edit_teach_class_name);
        classCodeText = findViewById(R.id.edit_teach_class_code);
        timeText = findViewById(R.id.edit_time_text);
        locationText = findViewById(R.id.edit_location_text);
        teacherText = findViewById(R.id.edit_teacher_text);
        creditText = findViewById(R.id.edit_credit_text);
        describeText = findViewById(R.id.edit_describe_text);

        setDefaultText();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_data_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.complete:
                mTeachClass.setName(classNameText.getText().toString());
                mTeachClass.setClassCode(classCodeText.getText().toString());
                mTeachClass.setTime(timeText.getText().toString());
                mTeachClass.setLocation(locationText.getText().toString());
                mTeachClass.setTeacherName(teacherText.getText().toString());
                float credit = Float.parseFloat(creditText.getText().toString());
                mTeachClass.setCredit(credit);
                mTeachClass.setDescribe(describeText.getText().toString());
                mTeachClass.update(mTeachClass.getId());

                Toast.makeText(this, "信息保存成功！", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    private void setDefaultText() {
        Intent intent = getIntent();
        int ClassId = intent.getIntExtra("class_id", -1);
        mTeachClass = LitePal.find(TeachClass.class, ClassId);
        classNameText.setText(mTeachClass.getName());
        classCodeText.setText(mTeachClass.getClassCode());
        timeText.setText(mTeachClass.getTime());
        locationText.setText(mTeachClass.getLocation());
        teacherText.setText(mTeachClass.getTeacherName());
        creditText.setText(mTeachClass.getCredit() + "");
        describeText.setText(mTeachClass.getDescribe());
    }
}
