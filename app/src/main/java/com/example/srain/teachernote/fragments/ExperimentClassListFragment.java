package com.example.srain.teachernote.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.srain.teachernote.R;
import com.example.srain.teachernote.adapters.ExperimentClassAdapter;
import com.example.srain.teachernote.entity.Experiment;
import com.example.srain.teachernote.entity.ExperimentClass;
import com.example.srain.teachernote.entity.ExperimentClassStudentList;
import com.example.srain.teachernote.entity.ExperimentList;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: TeacherNote
 * Date: 2018/5/9
 *
 * @author srain
 */
public class ExperimentClassListFragment extends Fragment implements AddClassDialogFragment.LoginInputListener, DeleteItemDialogFragment.LoginInputListener{

    private List<ExperimentClass> mExperimentClassList = new ArrayList<>();

    private Toolbar toolbar;

    ExperimentClassAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.experiment_class_list,container,false);
        toolbar = view.findViewById(R.id.experiment_class_toolbar);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        initList();
        RecyclerView experimentRecyclerView = view.findViewById(R.id.experiment_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        experimentRecyclerView.setLayoutManager(gridLayoutManager);
        adapter = new ExperimentClassAdapter(mExperimentClassList);
        experimentRecyclerView.setAdapter(adapter);

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.home);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
        adapter.notifyDataSetChanged();
    }

    private void initList() {
        mExperimentClassList.clear();
        // 不要直接将从数据库中取出的值的列表传给 mExperimentClassList ，要深复制
        List<ExperimentClass> experimentClasses = LitePal.findAll(ExperimentClass.class);
        mExperimentClassList.addAll(experimentClasses);
        
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.class_toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                break;
            case R.id.adds:
                AddClassDialogFragment.setLoginInputListener(this);
                showDialog();
                break;
            case R.id.delete:
                // 搞事情
                DeleteItemDialogFragment.setListener(this);
                showDeleteDialog();
                break;
            default:
                break;
        }
        return true;
    }

    private void showDialog(){
        AddClassDialogFragment addClassDialogFragment = AddClassDialogFragment.addDialogFragmentCreator();
        addClassDialogFragment.show(getFragmentManager(), "addExperimentClassDialog");
    }

    private void showDeleteDialog(){
        String[] items = new String[mExperimentClassList.size()];
        boolean[] itemStatus = new boolean[mExperimentClassList.size()];
        for(int i = 0; i < mExperimentClassList.size(); i++) {
            items[i] = mExperimentClassList.get(i).getName();
            itemStatus[i] = false;
        }

        DeleteItemDialogFragment deleteItemDialogFragment = DeleteItemDialogFragment.DeleteItemDialogCreator(items, itemStatus);
        FragmentManager fragmentManager = getFragmentManager();
        deleteItemDialogFragment.show(fragmentManager, "deleteExperiment");
    }

    @Override
    public void onLogInputComplete(String addClassName, String addClassCode) {
        Toast.makeText(getActivity(), "name: " + addClassName + " code:" + addClassCode, Toast.LENGTH_SHORT).show();
        ExperimentClass experimentClass = new ExperimentClass();
        experimentClass.setName(addClassName);
        experimentClass.setClassCode(addClassCode);
        experimentClass.save();
        initList();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLogInputComplete(boolean[] itemStatus) {
        for (int i = 0; i < itemStatus.length; i++) {
            if (itemStatus[i]) {
                ExperimentClass experimentClass = mExperimentClassList.get(i);
                int eClassId = experimentClass.getId();

                // 删除该实验班的实验
                List<ExperimentList> experimentLists = LitePal.where("ClassId = ?", eClassId + "").find(ExperimentList.class);
                if(!experimentLists.isEmpty()) {
                    for(ExperimentList item:experimentLists) {
                        LitePal.delete(Experiment.class, item.getExperimentId());
                    }
                }
                // 删除该实验班的实验列表
                LitePal.deleteAll(ExperimentList.class, "ClassId = ?", eClassId + "");
                // 删除该实验班的学生列表
                LitePal.deleteAll(ExperimentClassStudentList.class, "classId = ?", eClassId + "");
                LitePal.delete(ExperimentClass.class, eClassId);
            }
        }
        initList();
        adapter.notifyDataSetChanged();
    }
}
