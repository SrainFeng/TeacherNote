package com.example.srain.teachernote.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.example.srain.teachernote.ui.adapters.TeachClassAdapter;
import com.example.srain.teachernote.entity.TeachClass;
import com.example.srain.teachernote.entity.TeachClassStudentList;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: TeacherNote
 * Date: 2018/5/2
 *
 * @author srain
 */
public class TeachClassListFragment extends Fragment implements AddClassDialogFragment.LoginInputListener , DeleteItemDialogFragment.LoginInputListener{

    private List<TeachClass> mClassList = new ArrayList<>();

    TeachClassAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.teach_class_list,container,false);

        initList();
        RecyclerView teachClassRecyclerView = view.findViewById(R.id.teach_class_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        teachClassRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new TeachClassAdapter(mClassList);
        teachClassRecyclerView.setAdapter(adapter);

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
        mClassList.addAll(teachClasses);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.class_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
            case R.id.delete:
                // 搞事情
                DeleteItemDialogFragment.setListener(this);
                showDeleteDialog();
            default:
                break;
        }
        return true;
    }

    private void showDialog(){
        AddClassDialogFragment addClassDialogFragment = AddClassDialogFragment.addDialogFragmentCreator();
        addClassDialogFragment.show(getFragmentManager(), "addTeachClassDialog");
    }

    private void showDeleteDialog(){
        String[] items = new String[mClassList.size()];
        boolean[] itemStatus = new boolean[mClassList.size()];
        for(int i = 0; i < mClassList.size(); i++) {
            items[i] = mClassList.get(i).getName();
            itemStatus[i] = false;
        }
        DeleteItemDialogFragment deleteItemDialogFragment = DeleteItemDialogFragment.DeleteItemDialogCreator(items, itemStatus);
        FragmentManager fragmentManager = getFragmentManager();
        deleteItemDialogFragment.show(fragmentManager, "deleteExperiment");
    }

    @Override
    public void onLogInputComplete(String addClassName, String addClassCode) {
        Toast.makeText(getActivity(), "name: " + addClassName + " code:" + addClassCode, Toast.LENGTH_SHORT).show();
        boolean isExist = false;
        for (TeachClass item:mClassList) {
            if (item.getClassCode().equals(addClassCode)){
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            TeachClass teachClass = new TeachClass();
            teachClass.setName(addClassName);
            teachClass.setClassCode(addClassCode);
            teachClass.save();
            initList();
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getActivity(), "添加失败，课程编码已存在！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLogInputComplete(boolean[] itemStatus) {
        for (int i = 0; i < itemStatus.length; i++) {
            if (itemStatus[i]) {
                TeachClass teachClass = mClassList.get(i);
                int tClassId = teachClass.getId();
                LitePal.deleteAll(TeachClassStudentList.class, "classId = ?", tClassId + "");
                LitePal.delete(TeachClass.class, tClassId);
            }
        }
        initList();
        adapter.notifyDataSetChanged();
    }
}
