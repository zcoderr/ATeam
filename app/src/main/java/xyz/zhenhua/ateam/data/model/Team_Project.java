package xyz.zhenhua.ateam.data.model;

/**
 * Created by zachary on 2017/5/20.
 */

public class Team_Project {
    private long id;
    private long team_id;
    private long project_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTeam_id() {
        return team_id;
    }

    public void setTeam_id(long team_id) {
        this.team_id = team_id;
    }

    public long getProject_id() {
        return project_id;
    }

    public void setProject_id(long project_id) {
        this.project_id = project_id;
    }
}
