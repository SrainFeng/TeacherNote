package com.example.srain.teachernote.Activities;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.srain.teachernote.R;
import com.example.srain.teachernote.database.Student;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class StudentListActivity extends AppCompatActivity {

    SwipeMenuRecyclerView studentSwipeRecyclerView;

    Toolbar studentListToolbar;

    private List<Student> studentList = new ArrayList<>();

    StudentListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        studentSwipeRecyclerView = findViewById(R.id.swipe_menu_recycler_view);

        studentListToolbar = findViewById(R.id.student_list_toolbar);
        setSupportActionBar(studentListToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }

        initList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        studentSwipeRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new StudentListAdapter(studentList);

        SwipeMenuCreator mSwipeMenuCreator= new SwipeMenuCreator() {
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

        studentSwipeRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);

        studentSwipeRecyclerView.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                menuBridge.closeMenu();
            }
        });
        DefaultItemDecoration defaultItemDecoration = new DefaultItemDecoration(Color.GRAY, 100, 2);
        studentSwipeRecyclerView.addItemDecoration(defaultItemDecoration);
        studentSwipeRecyclerView.setAdapter(adapter);
    }

    private void initList() {
        for (int i = 1; i <= 50; i++) {
            Student experiment = new Student();
            experiment.setName("Experiment" + i);
            experiment.setStudentNumber("No." + i);
            studentList.add(experiment);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
                break;
        }
        return true;
    }
}
