package xyz.zhenhua.ateam.ui.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import xyz.zhenhua.ateam.config.RetrofitConfig;
import xyz.zhenhua.ateam.data.local.SPUtil;
import xyz.zhenhua.ateam.data.model.Result;
import xyz.zhenhua.ateam.data.model.TokenModel;
import xyz.zhenhua.ateam.data.remote.TokenService;
import xyz.zhenhua.ateam.ui.MainActivity;

public class LoginActivity extends Activity {
    Button btn_login;
    TextView tv_to_register;
    EditText et_email;
    EditText et_password;
    Retrofit retrofit;
    Gson gson;
    SPUtil spUtil;
    TokenService tokenService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = (Button)findViewById(R.id.btn_login);
        tv_to_register = (TextView) findViewById(R.id.btn_to_register);
        et_email = (EditText)findViewById(R.id.et_login_email);
        et_password = (EditText)findViewById(R.id.et_login_pwd);
        spUtil = new SPUtil(this);
        gson = new GsonBuilder()
                //配置你的Gson
                .setDateFormat("yyyy-MM-dd hh:mm:ss")
                .create();
        retrofit = new Retrofit.Builder().baseUrl("http://123.206.45.220:8080/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        tokenService = (TokenService) RetrofitConfig.builder(TokenService.class);
        tv_to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login(){
        spUtil.setCurrentUserAuth(et_email.getText().toString(),et_password.getText().toString());
        Call<Result<TokenModel>> call = tokenService.login(et_email.getText().toString(),et_password.getText().toString());
        call.enqueue(new Callback<Result<TokenModel>>() {
            @Override
            public void onResponse(Call<Result<TokenModel>> call, Response<Result<TokenModel>> response) {
                if(response.code() == 200){

                    if(response.body().getCode() == 100){
                        Toasty.success(LoginActivity.this, "登录成功!", Toast.LENGTH_SHORT, true).show();
                        spUtil.setToken(response.body().getContent().getAuthentication());
                        Log.d("login",response.body().getContent().getAuthentication());
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }else if(response.body().getCode() == -1001){

                    }
                }else{
                    Toasty.error(LoginActivity.this,"请求错误:"+response.code()).show();
                }

            }

            @Override
            public void onFailure(Call<Result<TokenModel>> call, Throwable t) {
                Toasty.error(LoginActivity.this,"用户名或密码错误").show();
                Log.d("登录",t.getMessage());
            }
        });

    }
}
