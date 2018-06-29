package com.example.srain.teachernote.adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.srain.teachernote.R;
import com.example.srain.teachernote.entity.ExperimentClassStudentList;
import com.example.srain.teachernote.entity.Student;

import org.litepal.LitePal;

import java.util.List;

/**
 * Project: TeacherNote
 * Date: 2018/6/26
 *
 * @author srain
 */
public class ExperimentStudentListAdapter extends StudentListAdapter{

    private int classId;


    public ExperimentStudentListAdapter(List<Student> studentList, int classId) {
        super(studentList);
        this.classId = classId;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = mStudentList.get(position);
        holder.studentName.setText(student.getName());
        holder.studentId.setText(student.getStudentNumber());
        float score = LitePal.where("classId = ? and studentId = ?", classId + "", student.getId() + "")
                .findFirst(ExperimentClassStudentList.class)
                .getScore();
        holder.studentScore.setText(score + "");
    }

}
