package com.example.srain.teachernote;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Project: TeacherNote
 * Date: 2018/5/9
 *
 * @author srain
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Experiment> mExperimentList;

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

    public ListAdapter(List<Experiment> List) {
        mExperimentList = List;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.experiment_list_item, parent, false);

        ListAdapter.ViewHolder holder = new ListAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        Experiment experiment = mExperimentList.get(position);
        Log.d("Experiment:", experiment.getExperimentId() + experiment.getExperimentName());

        holder.experimentName.setText(experiment.getExperimentName());
        holder.experimentId.setText(experiment.getExperimentId());
    }

    @Override
    public int getItemCount() {
        return mExperimentList.size();
    }
}
