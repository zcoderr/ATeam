package xyz.zhenhua.ateam.data.model;

/**
 * Created by zachary on 2017/5/20.
 */

public class Project_Taskgroups {
    private long id;
    private long project_id;
    private long taskgroup_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProject_id() {
        return project_id;
    }

    public void setProject_id(long project_id) {
        this.project_id = project_id;
    }

    public long getTaskgroup_id() {
        return taskgroup_id;
    }

    public void setTaskgroup_id(long taskgroup_id) {
        this.taskgroup_id = taskgroup_id;
    }
}
