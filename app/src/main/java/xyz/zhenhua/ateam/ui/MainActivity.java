package xyz.zhenhua.ateam.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.system.Os;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import es.dmoral.toasty.Toasty;
import xyz.zhenhua.ateam.R;
import xyz.zhenhua.ateam.ui.BottomNavigationViewHelper;
import xyz.zhenhua.ateam.ui.account.AccountFragment;
import xyz.zhenhua.ateam.ui.account.UserDetailFragment;
import xyz.zhenhua.ateam.ui.notification.NotificationFragment;
import xyz.zhenhua.ateam.ui.project.ProjectFragment;
import xyz.zhenhua.ateam.ui.task.TaskFragment;

public class MainActivity extends AppCompatActivity {

    private TaskFragment taskFragment;
    private ProjectFragment projectFragment;
    private NotificationFragment notifiFragment;
    private AccountFragment accountFrament;
    private Toolbar toolbar;
    MaterialDialog.Builder builder;
    MaterialDialog dialog;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction;
            transaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_task:
                    transaction.replace(R.id.content,taskFragment);
                    break;
                case R.id.navigation_project:
                    transaction.replace(R.id.content,projectFragment);
                    break;
                case R.id.navigation_notifications:
                    transaction.replace(R.id.content,notifiFragment);
                    break;
                case R.id.navigation_account:
                        transaction.replace(R.id.content,accountFrament);
                    break;
            }
            transaction.commit();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        //navigation.setBackgroundColor(Color.parseColor("#2F4B81"));
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        taskFragment = new TaskFragment();
        projectFragment = new ProjectFragment();
        notifiFragment = new NotificationFragment();
        accountFrament = new AccountFragment();
        FragmentTransaction transaction;
        transaction = getSupportFragmentManager().beginTransaction();
       // transaction.replace(R.id.content,taskFragment);
        //transaction.commit();
        //toolbar.setNavigationIcon(R.drawable.icon_home);
        toolbar.setTitle("团队协作系统");
        toolbar.setTitleTextColor(Color.WHITE);
        // 副标题
        toolbar.setSubtitle("毕业设计演示程序");
        toolbar.setBackgroundColor(Color.parseColor("#3C3D47"));
        toolbar.setSubtitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.toolbar_menu);
        //侧边栏的按钮

        setActionBar(toolbar);
        builder = new MaterialDialog.Builder(this)
                .title("提示")
                .content("确定要退出吗")
                .positiveText("确定")
                .positiveColor(Color.RED)
                .negativeText("取消")
                .negativeColor(Color.GREEN)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }})
                .onNegative(new MaterialDialog.SingleButtonCallback(){
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                });
        dialog = builder.build();

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_add:
                        break;
                    case R.id.action_share:
                        break;
                    case R.id.action_about:
                        break;
                }
                return true;
            }
        });
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        dialog.show();
    }
}
