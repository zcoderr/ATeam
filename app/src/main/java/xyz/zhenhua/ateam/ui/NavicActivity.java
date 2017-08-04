package xyz.zhenhua.ateam.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import xyz.zhenhua.ateam.R;
import xyz.zhenhua.ateam.ui.account.AccountFragment;
import xyz.zhenhua.ateam.ui.account.LoginActivity;
import xyz.zhenhua.ateam.ui.account.RegisterActivity;

public class NavicActivity extends Activity {
    Button btn_to_login_view;
    Button btn_to_register_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navic);
        btn_to_login_view = (Button)findViewById(R.id.btn_to_lgoinview);
        btn_to_register_view = (Button)findViewById(R.id.btn_to_register_view);
        btn_to_login_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NavicActivity.this, LoginActivity.class));
            }
        });

        btn_to_register_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NavicActivity.this, RegisterActivity.class));
            }
        });
    }
}
