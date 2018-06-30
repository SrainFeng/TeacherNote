package com.example.srain.teachernote.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.srain.teachernote.R;
import com.example.srain.teachernote.ui.adapters.TeachStudentListAdapter;
import com.example.srain.teachernote.entity.Student;
import com.example.srain.teachernote.entity.TeachClassStudentList;
import com.example.srain.teachernote.ui.fragments.AddStudentListDialogFragment;
import com.example.srain.teachernote.ui.fragments.ReviseScoreDialogFragment;
import com.example.srain.teachernote.ui.fragments.SamplePromptDialogFragment;
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

public class TeachStudentListActivity extends AppCompatActivity implements AddStudentListDialogFragment.LoginInputListener, SamplePromptDialogFragment.SampleInputListener, ReviseScoreDialogFragment.LoginInputListener{

    private SwipeMenuRecyclerView studentSwipeRecyclerView;

    private int classId;

    private Toolbar studentListToolbar;

    private List<Student> studentList = new ArrayList<>();

    private List<Integer> studentIdList = new ArrayList<>();

    private List<TeachClassStudentList> studentLists;

    private TeachStudentListAdapter adapter;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach_student_list);

        final Intent intent = getIntent();
        classId = intent.getIntExtra("class_id", -1);

        studentSwipeRecyclerView = findViewById(R.id.swipe_menu_recycler_view);

        studentListToolbar = findViewById(R.id.student_list_toolbar);
        setSupportActionBar(studentListToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }

        // 查找出该课程的所有学生的 id
        findStudentId();
        initList();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        studentSwipeRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new TeachStudentListAdapter(studentList, classId);

        SwipeMenuCreator mSwipeMenuCreator= new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(TeachStudentListActivity.this);
                deleteItem.setBackgroundColor(Color.RED)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setTextSize(25)
                        .setWidth(300)
                        .setHeight(MATCH_PARENT);
                swipeRightMenu.addMenuItem(deleteItem);

                SwipeMenuItem reviseItem = new SwipeMenuItem(TeachStudentListActivity.this);
                reviseItem.setBackgroundColor(Color.BLUE)
                        .setText("修改")
                        .setTextColor(Color.WHITE)
                        .setTextSize(25)
                        .setWidth(300)
                        .setHeight(MATCH_PARENT);
                swipeRightMenu.addMenuItem(reviseItem);

            }
        };

        studentSwipeRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);

        // 侧滑菜单中点击事件
        studentSwipeRecyclerView.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                menuBridge.closeMenu();
                int menuPosition = menuBridge.getPosition();
                position = menuBridge.getAdapterPosition();
                switch (menuPosition) {
                    case 0:
                        SamplePromptDialogFragment.setSampleInputListener(TeachStudentListActivity.this);
                        showSampleDialog();
                        break;
                    case 1:
                        ReviseScoreDialogFragment.setListener(TeachStudentListActivity.this);
                        showScoreDialog();
                        break;
                    default:
                        break;
                }
            }
        });

        // 列表项点击事件
        studentSwipeRecyclerView.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent showStudentDataIntent = new Intent(TeachStudentListActivity.this, StudentDataActivity.class);
                Student student = studentList.get(position);
                showStudentDataIntent.putExtra("student_id", student.getId());
                startActivity(showStudentDataIntent);
            }
        });

        DefaultItemDecoration defaultItemDecoration = new DefaultItemDecoration(Color.GRAY, 100, 2);
        studentSwipeRecyclerView.addItemDecoration(defaultItemDecoration);
        studentSwipeRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initList();
        adapter.notifyDataSetChanged();
    }

    private void findStudentId(){
        studentLists = LitePal.where("classId = ?", classId + "").find(TeachClassStudentList.class);
        if (!studentLists.isEmpty()) {
            for (TeachClassStudentList teachClassStudentList:studentLists) {
                studentIdList.add(teachClassStudentList.getStudentId());

                Log.d("ListActivity",  "classId: " + classId + "studentId: " + teachClassStudentList.getStudentId());
            }
        }
    }

    /**
     * 初始化列表该科包含的学生列表
     */
    private void initList() {
        studentList.clear();
        for (int id:studentIdList) {
            Student student = LitePal.find(Student.class, id);
            studentList.add(student);
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
                finish();
            default:
                break;
        }
        return true;
    }

    private void showAddStudentDialog(){
        AddStudentListDialogFragment addStudentListDialogFragment = AddStudentListDialogFragment.addDialogFragmentCreator();
        FragmentManager fragmentManager = getSupportFragmentManager();
        addStudentListDialogFragment.show(fragmentManager, "addStudentDialog");
    }

    private void showSampleDialog(){
        SamplePromptDialogFragment samplePromptDialogFragment = SamplePromptDialogFragment.samplePromptDialogCreator("是否从这门课中删除该学生？");
        FragmentManager fragmentManager = getSupportFragmentManager();
        samplePromptDialogFragment.show(fragmentManager, "alertDialog");

    }

    private void showScoreDialog(){
        ReviseScoreDialogFragment reviseScoreDialogFragment = ReviseScoreDialogFragment.FragmentCreator();
        FragmentManager fragmentManager = getSupportFragmentManager();
        reviseScoreDialogFragment.show(fragmentManager, "ScoreDialog");
    }

    @Override
    public void onLogInputComplete(String addName, String addNumber, String addScore) {
        TeachClassStudentList teachClassStudentList;
        boolean isExist = false;
        // 判断列表中是否有该学生，或输入的学号已存在
        for (Student item:studentList){
            if (item.getStudentNumber().equals(addNumber)) {
                isExist = true;
                Log.d("ListActivity", isExist + "");
                break;
            }
        }
        if (!isExist){
            // 不存在，则添加
            teachClassStudentList = new TeachClassStudentList();
            teachClassStudentList.setClassId(classId);
            teachClassStudentList.setScore(Float.parseFloat(addScore));

            List<Student> students = LitePal.where("studentNumber = ?", addNumber).find(Student.class);
            // 判断数据库中是否已经存在该学生，通过学号判断，学号相同即认为是同一个人
            if (students.isEmpty()) {
                // 不存在，添加该学生到数据库
                Student student = new Student();
                student.setStudentNumber(addNumber);
                student.setName(addName);
                student.save();
                teachClassStudentList.setStudentId(student.getId());
                studentIdList.add(student.getId());
                Toast.makeText(this,"该学生无记录，已成功添加！", Toast.LENGTH_SHORT).show();
            } else {
                // 存在，直接取出
                Student student = students.get(0);
                teachClassStudentList.setStudentId(student.getId());
                studentIdList.add(student.getId());
            }
            teachClassStudentList.save();

            initList();
            adapter.notifyDataSetChanged();
            Log.d("ListActivity", "name:"+addName+" num:"+addNumber);
        } else {
            Toast.makeText(this,"该学生号已存在！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void sampleCellBack() {
        Student student= studentList.get(position);
        int studentId = student.getId();
        int listId;
        for (int j = 0; j < studentIdList.size(); j++){
            if (studentIdList.get(j) == studentId) {
                studentIdList.remove(j);
            }
        }
        for (int i = 0; i < studentLists.size(); i++) {
            if (studentLists.get(i).getStudentId() == studentId){
                listId = studentLists.get(i).getId();
                studentLists.remove(i);
                LitePal.delete(TeachClassStudentList.class, listId);
            }
        }
        initList();
        adapter.notifyDataSetChanged();
        Toast.makeText(TeachStudentListActivity.this, "已成功删除！", Toast.LENGTH_SHORT).show();
        position = -1;
    }

    @Override
    public void onLogInputComplete(String newScore) {
        List<TeachClassStudentList> teachClassStudentLists = LitePal
                .where("classId = ? and studentId = ?", classId + "", studentList.get(position).getId() + "")
                .find(TeachClassStudentList.class);
        if (teachClassStudentLists.size() == 1){
            TeachClassStudentList teachClassStudentList = teachClassStudentLists.get(0);
            teachClassStudentList.setScore(Float.parseFloat(newScore));
            teachClassStudentList.update(teachClassStudentList.getId());
            initList();
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "出了点问题", Toast.LENGTH_SHORT).show();
        }
        position = -1;
    }
}
