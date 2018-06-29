package com.example.srain.teachernote.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.srain.teachernote.MyApplication;
import com.example.srain.teachernote.R;
import com.example.srain.teachernote.activities.ExperimentDataActivity;
import com.example.srain.teachernote.entity.Experiment;


import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Project: TeacherNote
 * Date: 2018/6/28
 *
 * @author srain
 */
public class ExperimentAdapter extends RecyclerView.Adapter<ExperimentAdapter.ViewHolder> {

    private List<Experiment> mExperimentList;

    public ExperimentAdapter(List<Experiment> experimentList) {
        mExperimentList = experimentList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView experimentTitle;
        TextView experimentCode;

        public ViewHolder(View view) {
            super(view);
            experimentTitle = view.findViewById(R.id.experiment_name);
            experimentCode = view.findViewById(R.id.experiment_id);
            this.view = view;

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.experiment_class_list_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 搞事情
                int position = holder.getAdapterPosition();
                Experiment experiment = mExperimentList.get(position);
                Intent intent = new Intent(MyApplication.getContext(), ExperimentDataActivity.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                int eId = experiment.getId();
                intent.putExtra("experiment_id", eId);
                MyApplication.getContext().startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.experimentCode.setText(mExperimentList.get(position).getCode());
        holder.experimentTitle.setText(mExperimentList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mExperimentList.size();
    }
}
