package xyz.zhenhua.ateam.data.model;

/**
 * Created by zachary on 2017/5/20.
 */

public class Taskgroup {
    private long taskgroup_id;
    private String taskgroup_name;
    private long creater_id;

    public long getTaskgroup_id() {
        return taskgroup_id;
    }

    public void setTaskgroup_id(long taskgroup_id) {
        this.taskgroup_id = taskgroup_id;
    }

    public String getTaskgroup_name() {
        return taskgroup_name;
    }

    public void setTaskgroup_name(String taskgroup_name) {
        this.taskgroup_name = taskgroup_name;
    }

    public long getCreater_id() {
        return creater_id;
    }

    public void setCreater_id(long creater_id) {
        this.creater_id = creater_id;
    }
}
