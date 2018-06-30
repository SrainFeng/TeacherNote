package com.example.srain.teachernote.ui.adapters;

import android.support.annotation.NonNull;

import com.example.srain.teachernote.entity.Student;
import com.example.srain.teachernote.entity.TeachClassStudentList;

import org.litepal.LitePal;

import java.util.List;

/**
 * Project: TeacherNote
 * Date: 2018/6/25
 *
 * @author srain
 */
public class TeachStudentListAdapter extends StudentListAdapter{

    private int classId;

    public TeachStudentListAdapter(List<Student> studentList, int classId) {
        super(studentList);
        this.classId = classId;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = mStudentList.get(position);
        holder.studentName.setText(student.getName());
        holder.studentId.setText(student.getStudentNumber());
        float score = LitePal.where("classId = ? and studentId = ?", classId + "", student.getId() + "")
                .findFirst(TeachClassStudentList.class)
                .getScore();
        holder.studentScore.setText(score + "");
    }

}
