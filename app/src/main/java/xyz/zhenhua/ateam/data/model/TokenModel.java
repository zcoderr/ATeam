package xyz.zhenhua.ateam.data.model;

/**
 * Created by zachary on 2017/5/18.
 */

public class TokenModel {
    private long userId;
    private String token;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAuthentication(){
        return userId+"_"+token;
    }
}
