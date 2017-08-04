package xyz.zhenhua.ateam.ui.task;


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
import xyz.zhenhua.ateam.data.model.Task;
import xyz.zhenhua.ateam.data.remote.TaskService;
import xyz.zhenhua.ateam.ui.project.ProjectFragment;
import xyz.zhenhua.ateam.ui.project.TaskgroupViewPagerActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment {
    RecyclerView rcv_tasks;
    SPUtil spUtil;
    TaskService taskService;
    List<Task> taskList;



    public TaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    public void initView(){
        rcv_tasks = (RecyclerView) getView().findViewById(R.id.icv_tasks);
        spUtil = new SPUtil(getContext());
        taskService = (TaskService) RetrofitConfig.builder(TaskService.class);


    }

    public void initData(){
        Call<Result<List<Task>>> call = taskService.v2GetAllTask(spUtil.getToken());
        call.enqueue(new Callback<Result<List<Task>>>() {
            @Override
            public void onResponse(Call<Result<List<Task>>> call, Response<Result<List<Task>>> response) {
                Log.d("tasks",response.code()+"");
                if(response.code() == 200){
                    if(response.body().getCode() == 100){
                        taskList = response.body().getContent();
                        rcv_tasks.setLayoutManager(new LinearLayoutManager(getContext()));
                        rcv_tasks.setAdapter(new TasksAdapter());
                    }
                }
            }

            @Override
            public void onFailure(Call<Result<List<Task>>> call, Throwable t) {
                Log.d("tasks","失败");
            }
        });

    }

    class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    getContext()).inflate(R.layout.list_item_tasks, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position)
        {
            holder.tv_name.setText("任务名称："+taskList.get(position).getTask_name());
            holder.tv_detail.setText("任务详情："+taskList.get(position).getTask_detail());
            holder.tv_status.setText("任务状态："+taskList.get(position).getTask_status());
            holder.imgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2017/5/25
                    showWIndow(v,taskList.get(position).getTask_id());
                }
            });
        }

        @Override
        public int getItemCount()
        {
            return taskList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {

            TextView tv_name;
            TextView tv_detail;
            TextView tv_status;
            ImageButton imgbtn;

            public MyViewHolder(View view)
            {
                super(view);
                tv_status = (TextView)view.findViewById(R.id.tv_task_status);
                tv_name = (TextView) view.findViewById(R.id.tv_task_name);
                tv_detail = (TextView)view.findViewById(R.id.tv_task_detail);
                imgbtn = (ImageButton) view.findViewById(R.id.imgbtn_modify_task);
            }
        }
    }

    public void showWIndow(View v , long id){
        // TODO: 2016/5/17 构建一个popupwindow的布局
        View popupView = getActivity().getLayoutInflater().inflate(R.layout.window_modify_taskstatus, null);
        // TODO: 2016/5/17 为了演示效果，简单的设置了一些数据，实际中大家自己设置数据即可，相信大家都会。
        final EditText et_new_status = (EditText)popupView.findViewById(R.id.et_new_status);
        final long t_id = id;
        Button button = (Button)popupView.findViewById(R.id.btn_commit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newStatus = et_new_status.getText().toString();
                Log.d("createteam",newStatus+":"+spUtil.getToken());
                Call<Result> call = taskService.modifyStatus(spUtil.getToken(),t_id,newStatus);
                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        Log.d("response",response.code()+"");
                        if(response.body().getCode()==100){
                            Toasty.success(getContext(), "任务状态修改成功!", Toast.LENGTH_SHORT, true).show();
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
        window.showAsDropDown(v, RelativeLayout.CENTER_HORIZONTAL,0);
    }

}
