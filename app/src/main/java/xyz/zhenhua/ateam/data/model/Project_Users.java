package xyz.zhenhua.ateam.data.model;

/**
 * Created by zachary on 2017/5/20.
 */

public class Project_Users {
    private long id;
    private long project_id;
    private long user_id;

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

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}
