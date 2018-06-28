package com.example.srain.teachernote.entity;

import org.litepal.crud.LitePalSupport;

/**
 * Project: TeacherNote
 * Date: 2018/6/24
 *
 * @author srain
 */
public class Experiment extends LitePalSupport {
    private int id;

    private String startTime;

    private String deadline;

    private String describe;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
