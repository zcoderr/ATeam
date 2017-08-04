package xyz.zhenhua.ateam.ui.account;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import xyz.zhenhua.ateam.data.model.Result;
import xyz.zhenhua.ateam.data.model.Team;
import xyz.zhenhua.ateam.data.remote.TeamService;
import xyz.zhenhua.ateam.ui.NavicActivity;
import xyz.zhenhua.ateam.ui.common.DividerItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    TextView btn_logout;
    RelativeLayout rl_create_team;
    TextView tv_username;
    SPUtil spUtil;
    TeamsAdapter teamsAdapter;
    TeamService teamService;
    RecyclerView recyclerView;

    List<Team> teamList;



    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        spUtil = new SPUtil(getContext());
        teamService = (TeamService) RetrofitConfig.builder(TeamService.class);
        initView();
        initData();


    }

    public void initView(){
        btn_logout = (TextView) getView().findViewById(R.id.btn_logout);
        rl_create_team = (RelativeLayout)getView().findViewById(R.id.rl_create_team);
        tv_username = (TextView) getView().findViewById(R.id.tv_username);
        tv_username.setText(spUtil.getCurrentUserAuth().getEmail());
        rl_create_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("UI","点击创建团队");
                //window_createteam = new CreateTeamWindow(getContext());
                //window_createteam.showAsDropDown(v);
                showWIndow(v);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spUtil.logout();
                tv_username.setText("未登录");
                startActivity(new Intent(getContext(), NavicActivity.class));
            }
        });
    }

    public void initData(){
        Call<Result<List<Team>>> call = teamService.getMyTeam(spUtil.getToken());
        call.enqueue(new Callback<Result<List<Team>>>() {
            @Override
            public void onResponse(Call<Result<List<Team>>> call, Response<Result<List<Team>>> response) {
                Log.d("teamList",response.code()+"");
                if(response.code()==200){
                    if(response.body().getCode()==100){
                        teamList = response.body().getContent();
                        recyclerView = (RecyclerView) getView().findViewById(R.id.rcv_teams);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        recyclerView.setAdapter(teamsAdapter = new TeamsAdapter());
                        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                                DividerItemDecoration.VERTICAL_LIST));

                    }
                }
            }

            @Override
            public void onFailure(Call<Result<List<Team>>> call, Throwable t) {

            }
        });
    }

    public void showWIndow(View v){
        View popupView = getActivity().getLayoutInflater().inflate(R.layout.window_createteam, null);
        final EditText et_team_name = (EditText)popupView.findViewById(R.id.et_team_name);
        Button button = (Button)popupView.findViewById(R.id.btn_commit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String teamname = et_team_name.getText().toString();
                Log.d("createteam",teamname+":"+spUtil.getToken());
                Call<Result> call = teamService.createTeam(spUtil.getToken(),teamname);
                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        Log.d("response",response.code()+"");
                        if(response.body().getCode()==100){
                            Toasty.success(getContext(), "团队创建成功!", Toast.LENGTH_SHORT, true).show();
                        }else{
                            Toasty.success(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT, true).show();
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

    class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    getContext()).inflate(R.layout.list_item_teams, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position)
        {
            holder.tv.setText(teamList.get(position).getTeam_name());
            holder.imgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(),TeamManageActivity.class);
                    intent.putExtra("team_id",teamList.get(position).getTeam_id());
                    startActivity(intent);

                }
            });
        }

        @Override
        public int getItemCount()
        {
            return teamList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {

            TextView tv;
            ImageButton imgbtn;

            public MyViewHolder(View view)
            {
                super(view);
                tv = (TextView) view.findViewById(R.id.tv_teams_item_name);
                imgbtn = (ImageButton) view.findViewById(R.id.imgbtn_to_teamdetail);
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        initData();
    }


}
