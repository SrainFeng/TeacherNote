package com.example.srain.teachernote;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Project: TeacherNote
 * Date: 2018/5/9
 *
 * @author srain
 */
public class ExperimentListFragment extends Fragment {
    private List<Experiment> mExperimentList = new ArrayList<>();
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.experiment_list,container,false);
        toolbar = view.findViewById(R.id.experiment_toolbar);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        initExperimentList();
        RecyclerView experimentRecyclerView = view.findViewById(R.id.experiment_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        experimentRecyclerView.setLayoutManager(gridLayoutManager);
        ListAdapter adapter = new ListAdapter(mExperimentList);
        experimentRecyclerView.setAdapter(adapter);

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        return view;
    }

    private void initExperimentList() {
        for (int i = 1; i <= 50; i++) {
            Experiment experiment = new Experiment();
            experiment.setExperimentId("No." + i);
            experiment.setExperimentName("Experiment" + i);
            mExperimentList.add(experiment);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                break;
            case R.id.adds:
                break;
            default:
                break;
        }
        return true;
    }
}
