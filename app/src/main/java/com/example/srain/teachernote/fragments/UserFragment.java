package com.example.srain.teachernote.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.srain.teachernote.MyApplication;
import com.example.srain.teachernote.R;
import com.example.srain.teachernote.activities.StudentListActivity;

/**
 * Project: TeacherNote
 * Date: 2018/6/27
 *
 * @author srain
 */
public class UserFragment extends Fragment implements View.OnClickListener{

    private Toolbar toolbar;

    private LinearLayout studentList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.user_fragment_layout, container, false);
        toolbar = view.findViewById(R.id.user_fragment_toolbar);

        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        studentList = view.findViewById(R.id.student_list_l);
        studentList.setOnClickListener(this);

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.home);
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                break;
            case R.id.adds:
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.student_list_l:
                Intent intent = new Intent(MyApplication.getContext(), StudentListActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
