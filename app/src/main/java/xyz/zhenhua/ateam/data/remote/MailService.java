package xyz.zhenhua.ateam.data.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import xyz.zhenhua.ateam.data.model.Result;

/**
 * Created by zachary on 2017/5/20.
 */

public interface MailService {

    @GET("mail/verificationcode")
    public Call<Result> getMailCode(@Query("email") String email);

}
