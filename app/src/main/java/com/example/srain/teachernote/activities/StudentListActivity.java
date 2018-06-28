package com.example.srain.teachernote.activities;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.srain.teachernote.R;
import com.example.srain.teachernote.adapters.StudentListAdapter;
import com.example.srain.teachernote.entity.ExperimentClassStudentList;
import com.example.srain.teachernote.entity.Student;
import com.example.srain.teachernote.entity.TeachClassStudentList;
import com.example.srain.teachernote.fragments.AddStudentListDialogFragment;
import com.example.srain.teachernote.fragments.SamplePromptDialogFragment;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class StudentListActivity extends AppCompatActivity implements AddStudentListDialogFragment.LoginInputListener, SamplePromptDialogFragment.SampleInputListener {

    private SwipeMenuRecyclerView mSwipeMenuRecyclerView;

    private Toolbar mToolbar;

    private List<Student> mStudentList = new ArrayList<>();

    private StudentListAdapter adapter;

    private int deletePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        mSwipeMenuRecyclerView = findViewById(R.id.swipe_menu_recycler_view);

        mToolbar = findViewById(R.id.student_list_toolbar);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.home);
        }

        // 初始化学生列表
        initList();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mSwipeMenuRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new StudentListAdapter(mStudentList);

        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(StudentListActivity.this);
                deleteItem.setBackgroundColor(Color.RED)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setTextSize(25)
                        .setWidth(300)
                        .setHeight(MATCH_PARENT);
                swipeRightMenu.addMenuItem(deleteItem);
            }
        };

        mSwipeMenuRecyclerView.setSwipeMenuCreator(swipeMenuCreator);

        mSwipeMenuRecyclerView.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                menuBridge.closeMenu();
                deletePosition = menuBridge.getAdapterPosition();
                SamplePromptDialogFragment.setSampleInputListener(StudentListActivity.this);
                showSampleDialog();
            }
        });

        mSwipeMenuRecyclerView.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent showStudentDataIntent = new Intent(StudentListActivity.this, StudentDataActivity.class);
                Student student = mStudentList.get(position);
                showStudentDataIntent.putExtra("student_id", student.getId());
                startActivity(showStudentDataIntent);
            }
        });

        DefaultItemDecoration defaultItemDecoration = new DefaultItemDecoration(Color.GRAY, 100, 2);
        mSwipeMenuRecyclerView.addItemDecoration(defaultItemDecoration);
        mSwipeMenuRecyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initList();
        adapter.notifyDataSetChanged();
    }

    private void showSampleDialog() {
        SamplePromptDialogFragment samplePromptDialogFragment = SamplePromptDialogFragment.samplePromptDialogCreator("是否彻底删除该学生？");
        FragmentManager fragmentManager = getSupportFragmentManager();
        samplePromptDialogFragment.show(fragmentManager, "alertDialog");
    }

    private void initList() {
        mStudentList.clear();
        List<Student> students = LitePal.findAll(Student.class);
        for (Student student:students) {
            mStudentList.add(student);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.adds:
                AddStudentListDialogFragment.setLoginInputListener(this);
                showAddStudentDialog();
                break;
            case android.R.id.home:
                break;
            default:
                break;
        }
        return true;
    }

    private void showAddStudentDialog() {
        AddStudentListDialogFragment addStudentListDialogFragment = AddStudentListDialogFragment.addDialogFragmentCreator();
        FragmentManager fragmentManager = getSupportFragmentManager();
        addStudentListDialogFragment.show(fragmentManager, "addStudentDialog");

    }

    @Override
    public void onLogInputComplete(String addName, String addNumber, String addScore) {
        boolean isExist = false;
        // 判断列表中是否有该学生，或输入的学号已存在
        for (Student item:mStudentList){
            if (item.getStudentNumber().equals(addNumber)) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            Student student = new Student();
            student.setStudentNumber(addNumber);
            student.setName(addName);
            student.save();
            Toast.makeText(this,"已成功添加！", Toast.LENGTH_SHORT).show();
            initList();
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this,"该学生号已存在！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void sampleCellBack() {
        Student student = mStudentList.get(deletePosition);
        int studentId = student.getId();

        // 参照完整性
        LitePal.deleteAll(ExperimentClassStudentList.class, "studentId = ?", studentId + "");
        LitePal.deleteAll(TeachClassStudentList.class, "studentId = ?", studentId + "");
        LitePal.delete(Student.class, studentId);
        initList();
        adapter.notifyDataSetChanged();
        Toast.makeText(StudentListActivity.this, "已成功彻底删除该学生！", Toast.LENGTH_SHORT).show();
        deletePosition = -1;
    }
}
