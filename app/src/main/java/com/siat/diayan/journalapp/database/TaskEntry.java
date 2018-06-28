package com.siat.diayan.journalapp.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Time;
import java.util.Date;

@Entity(tableName = "task")
public class TaskEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String taskDescription;
    private Date updateDate;
    private Date updateTime;

    @Ignore
    public TaskEntry(String taskDescription, Date updateDate, Date updateTime) {
        this.taskDescription = taskDescription;
        this.updateDate = updateDate;
        this.updateTime = updateTime;
    }


    public TaskEntry(int id, String taskDescription, Date updateDate, Date updateTime) {
        this.id = id;
        this.taskDescription = taskDescription;
        this.updateDate = updateDate;
        this.updateTime = updateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
