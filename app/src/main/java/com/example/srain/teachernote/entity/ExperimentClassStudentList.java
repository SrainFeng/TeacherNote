package com.example.srain.teachernote.entity;

import org.litepal.crud.LitePalSupport;

/**
 * Project: TeacherNote
 * Date: 2018/6/26
 *
 * @author srain
 */
public class ExperimentClassStudentList extends LitePalSupport{
    private int id;

    private int classId;

    private int studentId;

    private float score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
