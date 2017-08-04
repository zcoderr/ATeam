package xyz.zhenhua.ateam.data.model;

import java.sql.Timestamp;

/**
 * Created by zachary on 2017/5/20.
 */

public class Task {
    private long task_id;
    private String task_name;
    private long creater_id;
    private String task_status;
    private String task_detail;
    private String start_time;
    private String end_time;

    public long getTask_id() {
        return task_id;
    }

    public void setTask_id(long task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public long getCreater_id() {
        return creater_id;
    }

    public void setCreater_id(long create_id) {
        this.creater_id = create_id;
    }

    public String getTask_status() {
        return task_status;
    }

    public void setTask_status(String task_status) {
        this.task_status = task_status;
    }

    public String getTask_detail() {
        return task_detail;
    }

    public void setTask_detail(String task_detail) {
        this.task_detail = task_detail;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
}
