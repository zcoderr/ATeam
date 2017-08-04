package xyz.zhenhua.ateam.data.remote;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import xyz.zhenhua.ateam.data.model.Result;
import xyz.zhenhua.ateam.data.model.TokenModel;

/**
 * Created by zachary on 2017/5/20.
 */

public interface TokenService {
    @POST("tokens")
    public Call<Result<TokenModel>> login(@Query("email") String email , @Query("password") String password);

    //登出
    @DELETE("tokens")
    public Call<Result> logout(@Header("authorization") String token);
}
