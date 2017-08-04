package xyz.zhenhua.ateam.data.model;

/**
 * Created by zachary on 2017/5/20.
 */

public class Team_Users {
    private long id;
    private long user_id;
    private long team_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getTeam_id() {
        return team_id;
    }

    public void setTeam_id(long team_id) {
        this.team_id = team_id;
    }
}
