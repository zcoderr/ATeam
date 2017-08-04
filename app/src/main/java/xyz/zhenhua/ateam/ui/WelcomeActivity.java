package xyz.zhenhua.ateam.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.zhenhua.ateam.R;
import xyz.zhenhua.ateam.data.local.SPUtil;
import xyz.zhenhua.ateam.data.model.Result;
import xyz.zhenhua.ateam.data.model.TokenModel;
import xyz.zhenhua.ateam.data.model.UserAuths;
import xyz.zhenhua.ateam.data.remote.TokenService;

public class WelcomeActivity extends Activity {
    SPUtil spUtil;
    Retrofit retrofit;
    TokenService tokenService;
    Gson gson;
    public Handler newhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            checkLogin();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        newhandler.sendEmptyMessageDelayed(0,1000);


        spUtil = new SPUtil(this);
        gson = new GsonBuilder()
                //配置你的Gson
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
        retrofit = new Retrofit.Builder().baseUrl("http://123.206.45.220:8080/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        tokenService = retrofit.create(TokenService.class);
    }
    public void checkLogin(){
        if(spUtil.getToken().equals("")){
            startActivity(new Intent(WelcomeActivity.this,NavicActivity.class));
            finish();
        }else {
            //reLogin();
            startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
            finish();
        }
    }
    public void reLogin(){
        // TODO: 重新登录时token失效
        UserAuths userAuths = spUtil.getCurrentUserAuth();
        Log.d("reLogin","email:"+userAuths.getEmail()+",pass:"+userAuths.getPassword());
        Call<Result<TokenModel>> call = tokenService.login(userAuths.getEmail(),userAuths.getPassword());
        call.enqueue(new Callback<Result<TokenModel>>() {
            @Override
            public void onResponse(Call<Result<TokenModel>> call, Response<Result<TokenModel>> response) {
                try{
                    if(response.code()==200){
                        if(response.body().getCode()==100){
                            Toasty.success(WelcomeActivity.this,"欢迎回来", Toast.LENGTH_SHORT).show();
                            TokenModel tokenModel = response.body().getContent();
                            Log.d("reLogin_token",tokenModel.getAuthentication());
                            spUtil.setToken(tokenModel.getAuthentication());
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Result<TokenModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
