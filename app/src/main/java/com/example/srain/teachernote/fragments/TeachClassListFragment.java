package com.example.srain.teachernote.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.srain.teachernote.R;
import com.example.srain.teachernote.adapters.TeachClassAdapter;
import com.example.srain.teachernote.entity.TeachClass;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: TeacherNote
 * Date: 2018/5/2
 *
 * @author srain
 */
public class TeachClassListFragment extends Fragment implements AddClassDialogFragment.LoginInputListener {

    private List<TeachClass> mClassList = new ArrayList<>();

    private Toolbar toolbar;

    TeachClassAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.teach_class_list,container,false);
        toolbar = view.findViewById(R.id.teach_class_toolbar);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        initList();
        RecyclerView teachClassRecyclerView = view.findViewById(R.id.teach_class_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        teachClassRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new TeachClassAdapter(mClassList);
        teachClassRecyclerView.setAdapter(adapter);

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
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
        mClassList.clear();
        List<TeachClass> teachClasses = LitePal.findAll(TeachClass.class);
        for (TeachClass teachClass:teachClasses) {
            mClassList.add(teachClass);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("studentFragment", "you click the setting");
        switch (item.getItemId()) {
            case android.R.id.home:
                break;
            case R.id.adds:
                AddClassDialogFragment.setLoginInputListener(this);
                showDialog();
                break;
            default:
                break;
        }
        return true;
    }

    private void showDialog(){
        AddClassDialogFragment addClassDialogFragment = AddClassDialogFragment.addDialogFragmentCreator();
        addClassDialogFragment.show(getFragmentManager(), "addTeachClassDialog");
    }

    @Override
    public void onLogInputComplete(String addClassName, String addClassCode) {
        Toast.makeText(getActivity(), "name: " + addClassName + " code:" + addClassCode, Toast.LENGTH_SHORT).show();
        TeachClass teachClass = new TeachClass();
        teachClass.setName(addClassName);
        teachClass.setClassCode(addClassCode);
        teachClass.save();
        initList();
        adapter.notifyDataSetChanged();
    }
}
