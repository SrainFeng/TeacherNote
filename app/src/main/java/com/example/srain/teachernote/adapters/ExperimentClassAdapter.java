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
import com.example.srain.teachernote.activities.ExperimentClassActivity;
import com.example.srain.teachernote.entity.ExperimentClass;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Project: TeacherNote
 * Date: 2018/5/9
 *
 * @author srain
 */
public class ExperimentClassAdapter extends RecyclerView.Adapter<ExperimentClassAdapter.ViewHolder> {

    private List<ExperimentClass> mExperimentClassList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView experimentName;
        TextView experimentId;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            experimentName = view.findViewById(R.id.experiment_name);
            experimentId = view.findViewById(R.id.experiment_id);
        }
    }

    public ExperimentClassAdapter(List<ExperimentClass> List) {
        mExperimentClassList = List;
    }

    @NonNull
    @Override
    public ExperimentClassAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.experiment_class_list_item, parent, false);

        final ViewHolder holder = new ExperimentClassAdapter.ViewHolder(view);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 搞事情
                int position = holder.getAdapterPosition();
                ExperimentClass experimentClass = mExperimentClassList.get(position);
                Intent intent = new Intent(MyApplication.getContext(), ExperimentClassActivity.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                String classCode = experimentClass.getClassCode();
                intent.putExtra("class_code", classCode);
                MyApplication.getContext().startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExperimentClassAdapter.ViewHolder holder, int position) {
        ExperimentClass experimentClass = mExperimentClassList.get(position);

        holder.experimentName.setText(experimentClass.getName());
        holder.experimentId.setText(experimentClass.getClassCode());
    }

    @Override
    public int getItemCount() {
        return mExperimentClassList.size();
    }
}
