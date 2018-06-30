package com.example.srain.teachernote.ui.adapters;


import android.support.annotation.NonNull;

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
