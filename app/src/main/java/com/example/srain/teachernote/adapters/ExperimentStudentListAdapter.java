package com.example.srain.teachernote.adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.srain.teachernote.R;
import com.example.srain.teachernote.database.ExperimentClassStudentList;
import com.example.srain.teachernote.database.Student;

import org.litepal.LitePal;

import java.util.List;

/**
 * Project: TeacherNote
 * Date: 2018/6/26
 *
 * @author srain
 */
public class ExperimentStudentListAdapter extends RecyclerView.Adapter<ExperimentStudentListAdapter.ViewHolder>{

    private List<Student> mStudentList;

    private int classId;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView studentName;
        TextView studentId;
        TextView studentScore;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            studentName = view.findViewById(R.id.student_name);
            studentId = view.findViewById(R.id.student_number);
            studentScore = view.findViewById(R.id.student_score);
        }
    }

    public ExperimentStudentListAdapter(List<Student> studentList, int classId) {
        mStudentList = studentList;
        this.classId = classId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_list_item, parent, false);

        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = mStudentList.get(position);
        holder.studentName.setText(student.getName());
        holder.studentId.setText(student.getStudentNumber());
        float scroe = LitePal.where("classId = ? and studentId = ?", classId + "", student.getId() + "")
                .findFirst(ExperimentClassStudentList.class)
                .getScore();
        holder.studentScore.setText(scroe + "");
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }
}
