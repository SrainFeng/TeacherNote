package com.example.srain.teachernote.database;

/**
 * Project: TeacherNote
 * Date: 2018/6/24
 *
 * @author srain
 */
public class ExperimentClass {
    private int id;

    private String classCode;

    private String describe;

    private String time;

    private String location;

    private String teacherName;

    private float credit;

    private int experimentListId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public int getExperimentListId() {
        return experimentListId;
    }

    public void setExperimentListId(int experimentListId) {
        this.experimentListId = experimentListId;
    }
}
