package xyz.zhenhua.ateam.data.remote;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import xyz.zhenhua.ateam.data.model.Result;

/**
 * Created by zachary on 2017/5/20.
 */

public interface UserService {

    //注册
    @POST("user")
    public Call<Result> register(@Query("email") String email , @Query("username") String username
            , @Query("password") String password , @Query("authCode") String authcode);

    //修改密码
    @POST("modifypassword")
    public Call<Result> modifyPass(@Query("email") String email , @Query("newPassword") String newPass
    , @Query("authCode") String authCode);
}
