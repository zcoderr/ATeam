package xyz.zhenhua.ateam.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Objects;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.zhenhua.ateam.data.remote.TeamService;
import xyz.zhenhua.ateam.data.remote.TokenService;

/**
 * Created by zachary on 2017/5/22.
 */

public class RetrofitConfig {

    private static Retrofit retrofit;
    private static Gson gson;

    public static Object builder(Class<?> clazz){
        gson = new GsonBuilder()
                //配置你的Gson
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
        retrofit = new Retrofit.Builder().baseUrl("http://192.168.199.197:8080/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
         return retrofit.create(clazz);
    }
}
