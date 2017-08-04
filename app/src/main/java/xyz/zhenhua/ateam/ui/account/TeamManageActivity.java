package xyz.zhenhua.ateam.ui.account;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.zhenhua.ateam.R;
import xyz.zhenhua.ateam.config.RetrofitConfig;
import xyz.zhenhua.ateam.data.local.SPUtil;
import xyz.zhenhua.ateam.data.model.Project;
import xyz.zhenhua.ateam.data.model.Result;
import xyz.zhenhua.ateam.data.model.Team;
import xyz.zhenhua.ateam.data.model.User;
import xyz.zhenhua.ateam.data.remote.ProjectService;
import xyz.zhenhua.ateam.data.remote.TeamService;
import xyz.zhenhua.ateam.ui.common.DividerGridItemDecoration;

public class TeamManageActivity extends Activity implements View.OnClickListener{
    long current_team_id;
    RelativeLayout rl_creat_project;
    RelativeLayout rl_add_user;
    RecyclerView rcv_team_users;
    TeamService teamService;
    ProjectService projectService;
    SPUtil spUtil;
    List<User> userList;
    UsersAdapter usersAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_manage);
        current_team_id = getIntent().getLongExtra("team_id",0);
        initView();
        initData();
    }

    public void initView(){
        teamService = (TeamService) RetrofitConfig.builder(TeamService.class);
        projectService = (ProjectService) RetrofitConfig.builder(ProjectService.class);
        spUtil = new SPUtil(this);
        rl_creat_project = (RelativeLayout)findViewById(R.id.rl_create_project);
        rl_add_user = (RelativeLayout)findViewById(R.id.rl_add_user);
        rcv_team_users = (RecyclerView)findViewById(R.id.rcv_team_users);
        rl_add_user.setOnClickListener(this);
        rl_creat_project.setOnClickListener(this);
    }

    public void initData(){
        Call<Result<List<User>>> call =  teamService.getTeamUsers(spUtil.getToken(),current_team_id);
        Log.d("team_manager",spUtil.getToken()+":"+current_team_id);
        call.enqueue(new Callback<Result<List<User>>>() {
            @Override
            public void onResponse(Call<Result<List<User>>> call, Response<Result<List<User>>> response) {
                if(response.code() ==200){
                    if(response.body().getCode() == 100){
                        userList = response.body().getContent();
                        for (User user:userList
                             ) {
                            Log.d("users",user.getUsername());
                        }
                        MyLayoutManager layout = new MyLayoutManager();
                        //必须，防止recyclerview高度为wrap时测量item高度0 layout.setAutoMeasureEnabled(true);
                        //rcv_team_users.setLayoutManager(layout);
                        rcv_team_users.setLayoutManager(new GridLayoutManager(TeamManageActivity.this,4));
                        rcv_team_users.setAdapter(usersAdapter = new UsersAdapter());
                        //rcv_team_users.addItemDecoration(new DividerGridItemDecoration(TeamManageActivity.this));
                    }
                }
            }

            @Override
            public void onFailure(Call<Result<List<User>>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_add_user:
                showUserAddWindow(v);
                break;
            case R.id.rl_create_project:
                showProjectCreateWindow(v);
                break;
            default:
                break;
        }
    }

    public class MyLayoutManager extends RecyclerView.LayoutManager {

        @Override
        public RecyclerView.LayoutParams generateDefaultLayoutParams() {
            return new RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.WRAP_CONTENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            detachAndScrapAttachedViews(recycler);

            int sumWidth = getWidth();

            int curLineWidth = 0, curLineTop = 0;
            int lastLineMaxHeight = 0;
            for (int i = 0; i < getItemCount(); i++) {
                View view = recycler.getViewForPosition(i);

                addView(view);
                measureChildWithMargins(view, 0, 0);
                int width = getDecoratedMeasuredWidth(view);
                int height = getDecoratedMeasuredHeight(view);

                curLineWidth += width;
                if (curLineWidth <= sumWidth) {//不需要换行
                    layoutDecorated(view, curLineWidth - width, curLineTop, curLineWidth, curLineTop + height);
                    //比较当前行多有item的最大高度
                    lastLineMaxHeight = Math.max(lastLineMaxHeight, height);
                } else {//换行
                    curLineWidth = width;
                    if (lastLineMaxHeight == 0) {
                        lastLineMaxHeight = height;
                    }
                    //记录当前行top
                    curLineTop += lastLineMaxHeight;

                    layoutDecorated(view, 0, curLineTop, width, curLineTop + height);
                    lastLineMaxHeight = height;
                }
            }

        }

    }
    class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(TeamManageActivity.this)
                    .inflate(R.layout.list_item_team_users, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position)
        {
            holder.tv_user_name.setText(userList.get(position).getUsername());
            //holder.iv_user_header.setImageResource(R.drawable.icon_user);
        }

        @Override
        public int getItemCount()
        {
            return userList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {

            ImageView iv_user_header;
            TextView tv_user_name;

            public MyViewHolder(View view)
            {
                super(view);
                tv_user_name = (TextView) view.findViewById(R.id.tv__item_username);
                iv_user_header = (ImageView) view.findViewById(R.id.img_user_header);
            }
        }
    }

    public void showUserAddWindow(View v){
        // TODO: 2016/5/17 构建一个popupwindow的布局
        View popupView = TeamManageActivity.this.getLayoutInflater().inflate(R.layout.window_adduser, null);
        // TODO: 2016/5/17 为了演示效果，简单的设置了一些数据，实际中大家自己设置数据即可，相信大家都会。
        final EditText et_mail = (EditText)popupView.findViewById(R.id.et_user_email);
        Button button = (Button)popupView.findViewById(R.id.btn_commit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usermail = et_mail.getText().toString();
                Log.d("createteam",usermail+":"+spUtil.getToken());
                Call<Result> call = teamService.inviteUser(spUtil.getToken(),current_team_id,usermail);
                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        Log.d("response",response.code()+"");
                        if(response.body().getCode()==100){
                            Toasty.success(TeamManageActivity.this, response.body().getContent().toString(), Toast.LENGTH_SHORT, true).show();
                        }else{
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {

                    }
                });

            }
        });

        // TODO: 2016/5/17 创建PopupWindow对象，指定宽度和高度
        PopupWindow window = new PopupWindow(popupView, 600, 400);
        // TODO: 2016/5/17 设置动画
        //window.setAnimationStyle(R.style.popup_window_anim);
        // TODO: 2016/5/17 设置背景颜色
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        // TODO: 2016/5/17 设置可以获取焦点
        window.setFocusable(true);
        // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
        window.setOutsideTouchable(true);
        // TODO：更新popupwindow的状态
        window.update();
        // TODO: 2016/5/17 以下拉的方式显示，并且可以设置显示的位置
        window.showAsDropDown(v,RelativeLayout.CENTER_HORIZONTAL,0);
    }

    public void showProjectCreateWindow(View v){
        // TODO: 2016/5/17 构建一个popupwindow的布局
        View popupView = TeamManageActivity.this.getLayoutInflater().inflate(R.layout.window_createproject, null);
        // TODO: 2016/5/17 为了演示效果，简单的设置了一些数据，实际中大家自己设置数据即可，相信大家都会。
        final EditText et_project_name = (EditText)popupView.findViewById(R.id.et_project_name);
        final EditText et_project_detail = (EditText) popupView.findViewById(R.id.et_project_detail);
        Button sbutton = (Button)popupView.findViewById(R.id.btn_commit);
        sbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String projectname = et_project_name.getText().toString();
                String projectdetail = et_project_detail.getText().toString();
                Log.d("createProject",projectname+":"+spUtil.getToken());
                Call<Result> call = projectService.createTeamProject(spUtil.getToken(),projectname,projectdetail,current_team_id);
                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        Log.d("response",response.code()+"");
                        if(response.body().getCode()==100){
                            Toasty.success(TeamManageActivity.this, "项目创建成功!", Toast.LENGTH_SHORT, true).show();
                        }else{
                            Toasty.success(TeamManageActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {

                    }
                });

            }
        });

        // TODO: 2016/5/17 创建PopupWindow对象，指定宽度和高度
        PopupWindow window = new PopupWindow(popupView, 600, 400);
        // TODO: 2016/5/17 设置动画
        //window.setAnimationStyle(R.style.popup_window_anim);
        // TODO: 2016/5/17 设置背景颜色
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
        // TODO: 2016/5/17 设置可以获取焦点
        window.setFocusable(true);
        // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
        window.setOutsideTouchable(true);
        // TODO：更新popupwindow的状态
        window.update();
        // TODO: 2016/5/17 以下拉的方式显示，并且可以设置显示的位置
        window.showAsDropDown(v,RelativeLayout.CENTER_HORIZONTAL,0);
    }
}
