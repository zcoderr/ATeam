package xyz.zhenhua.ateam.data.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import xyz.zhenhua.ateam.data.model.Project;
import xyz.zhenhua.ateam.data.model.Result;
import xyz.zhenhua.ateam.data.model.User;

/**
 * Created by zachary on 2017/5/20.
 */

public interface ProjectService {

    @POST("project/adduser")
    public Call<Result> addUser(@Header("authorization") String token, @Query("project_id") long project_id
            , @Query("user_id") long user_id);

    @POST("project/createpersonp")
    public Call<Result> createPersonProject(@Header("authorization") String token, @Query("project_name") String name
            , @Query("project_detail") String detail);

    @POST("project/createteamp")
    public Call<Result> createTeamProject(@Header("authorization") String token, @Query("project_name") String name
            , @Query("project_detail") String detail, @Query("team_id") long team_id);

    @POST("project/delete")
    public Call<Result> deleteTeam(@Header("authorization") String token, @Query("project_id") long project_id);

    @POST("project/getmycreatep")
    public Call<Result<List<Project>>> getMyCreateProjects(@Header("authorization") String token);

    @POST("project/getmyprojects")
    public Call<Result<List<Project>>> getMyProjects(@Header("authorization") String token);

    @POST("project/getprojectcreater")
    public Call<Result<User>> getProjectCreater(@Header("authorization") String token , @Query("project_id") long project_id);

    @POST("project/getprojectusers")
    public Call<Result<List<User>>> getProjectUsers(@Header("authorization") String token , @Query("project_id") long project_id);


    @POST("project/getteamprojects")
    public Call<Result<List<Project>>> getTeamProjects(@Header("authorization") String token , @Query("team_id") long team_id);
}

