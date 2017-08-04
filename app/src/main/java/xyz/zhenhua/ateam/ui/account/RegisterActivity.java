package xyz.zhenhua.ateam.ui.account;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.zhenhua.ateam.R;
import xyz.zhenhua.ateam.config.RetrofitConfig;
import xyz.zhenhua.ateam.data.model.Result;
import xyz.zhenhua.ateam.data.remote.MailService;
import xyz.zhenhua.ateam.data.remote.UserService;

public class RegisterActivity extends Activity {
    private  boolean runningThree = false;
    private EditText et_email;
    private EditText et_pass;
    private EditText et_name;
    private EditText et_authcode;
    private Button btn_getCode;
    private Button btn_commit;
    private UserService userService;
    private MailService mailService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

    }

    public void initView(){
        et_email = (EditText)findViewById(R.id.et_register_mail);
        et_pass = (EditText)findViewById(R.id.et_register_pass);
        et_authcode = (EditText)findViewById(R.id.et_authcode);
        et_name = (EditText)findViewById(R.id.et_register_name);
        btn_getCode = (Button)findViewById(R.id.btn_getcode);
        btn_commit = (Button)findViewById(R.id.btn_register);
        userService = (UserService) RetrofitConfig.builder(UserService.class);
        mailService = (MailService) RetrofitConfig.builder(MailService.class);
        btn_getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (runningThree) {
                    //不操作
                } else {
                    Call<Result> call = mailService.getMailCode(et_email.getText().toString());
                    call.enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            if(response.code() == 200){
                                if(response.body().getCode() == 100){
                                    Toasty.success(RegisterActivity.this,"获取成功，请查收", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {

                        }
                    });

                    downTimer.start();
                }
            }
        });

        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Result> call = userService.register(et_email.getText().toString(),et_name.getText().toString(),et_pass.getText().toString(),et_authcode.getText().toString());
                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        if(response.code() == 200){
                            if(response.body().getCode() == 100){
                                Toasty.success(RegisterActivity.this,"注册成功", Toast.LENGTH_SHORT).show();
                            }else {
                                Toasty.error(RegisterActivity.this,response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {

                    }
                });
            }
        });

    }

    private CountDownTimer downTimer = new CountDownTimer(60 * 1000, 1000) {
        @Override
        public void onTick(long l) {
            runningThree = true;
            btn_getCode.setText((l / 1000) + "秒");
        }

        @Override
        public void onFinish() {
            runningThree = false;
            btn_getCode.setText("重新获取");
        }
    };


}
