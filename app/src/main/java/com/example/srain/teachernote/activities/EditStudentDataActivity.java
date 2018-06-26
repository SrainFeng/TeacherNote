package com.example.srain.teachernote.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.srain.teachernote.R;
import com.example.srain.teachernote.database.Student;

import org.litepal.LitePal;

public class EditStudentDataActivity extends AppCompatActivity {

    private int mStudentId;

    private Student mStudent;

    private EditText editStudentName, editStudentNumber, editGrade, editMajor;

    private Toolbar editStudentToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_data);

        editStudentName = findViewById(R.id.edit_student_name);
        editStudentNumber = findViewById(R.id.edit_student_number);
        editGrade = findViewById(R.id.edit_student_grade_text);
        editMajor = findViewById(R.id.edit_student_major_text);
        editStudentToolbar = findViewById(R.id.edit_student_data_toolbar);

        Intent intent = getIntent();
        mStudentId = intent.getIntExtra("student_id", -1);
        mStudent = LitePal.find(Student.class, mStudentId);

        setSupportActionBar(editStudentToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }

        setDefaultText();

    }

    private void setDefaultText() {
        editMajor.setText(mStudent.getMajor());
        editGrade.setText(mStudent.getGrade() + "");
        editStudentName.setText(mStudent.getName());
        editStudentNumber.setText(mStudent.getStudentNumber());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_data_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.complete:
                mStudent.setName(editStudentName.getText().toString());
                mStudent.setStudentNumber(editStudentNumber.getText().toString());
                mStudent.setGrade(Integer.parseInt(editGrade.getText().toString()));
                mStudent.setMajor(editMajor.getText().toString());
                mStudent.update(mStudentId);
                Toast.makeText(this, "信息保存成功！", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                finish();
            default:
                break;
        }
        return true;
    }
}
