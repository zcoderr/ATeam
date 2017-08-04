package xyz.zhenhua.ateam.data.model;

/**
 * Created by zachary on 2017/5/20.
 */

public class Taskgroup_Tasks {
    private long id;
    private long task_id;
    private long taskgroup_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTask_id() {
        return task_id;
    }

    public void setTask_id(long task_id) {
        this.task_id = task_id;
    }

    public long getTaskgroup_id() {
        return taskgroup_id;
    }

    public void setTaskgroup_id(long taskgroup_id) {
        this.taskgroup_id = taskgroup_id;
    }
}
