package xyz.zhenhua.ateam.data.remote;

import java.util.List;

import retrofit2.Call;

import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import xyz.zhenhua.ateam.data.model.Result;
import xyz.zhenhua.ateam.data.model.Team;
import xyz.zhenhua.ateam.data.model.User;

/**
 * Created by zachary on 2017/5/20.
 */

public interface TeamService {

    @POST("team/create")
    public Call<Result> createTeam(@Header("authorization") String token , @Query("team_name") String team_name);

    @POST("team/getmyteam")
    public  Call<Result<List<Team>>> getMyTeam(@Header("authorization") String token);

    @POST("team/getteamcreater")
    public Call<Result<User>> getTeamCreater(@Header("authorization") String token , @Query("team_id") long team_id);

    @POST("team/getteamusers")
    public Call<Result<List<User>>> getTeamUsers(@Header("authorization") String token , @Query("team_id") long team_id);

    @POST("team/inviteuser")
    public Call<Result> inviteUser(@Header("authorization") String token ,@Query("team_id") long team_id , @Query("email") String email);

    @POST("team/join")
    public  Call<Result> joinTeam(@Header("authorization") String token, @Query("team_id") long team_id);
}
