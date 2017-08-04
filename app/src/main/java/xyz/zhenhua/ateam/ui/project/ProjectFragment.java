package xyz.zhenhua.ateam.ui.project;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.zhenhua.ateam.R;
import xyz.zhenhua.ateam.config.RetrofitConfig;
import xyz.zhenhua.ateam.data.local.SPUtil;
import xyz.zhenhua.ateam.data.model.Project;
import xyz.zhenhua.ateam.data.model.Result;
import xyz.zhenhua.ateam.data.model.Task;
import xyz.zhenhua.ateam.data.remote.ProjectService;
import xyz.zhenhua.ateam.ui.account.AccountFragment;
import xyz.zhenhua.ateam.ui.account.TeamManageActivity;
import xyz.zhenhua.ateam.ui.common.DividerItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectFragment extends Fragment {
    RecyclerView rcv_projects;
    List<Project> projectList;
    ProjectService projectService;
    SPUtil spUtil;
    ProjectsAdapter projectsAdapter;
    public ProjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();

    }

    public void initView(){
        spUtil = new SPUtil(getContext());

        projectService = (ProjectService) RetrofitConfig.builder(ProjectService.class);
    }

    public void initData(){
        Call<Result<List<Project>>> call = projectService.getMyProjects(spUtil.getToken());
        Log.d("projects","token:"+spUtil.getToken());
        call.enqueue(new Callback<Result<List<Project>>>() {
            @Override
            public void onResponse(Call<Result<List<Project>>> call, Response<Result<List<Project>>> response) {
                Log.d("projects",response.code()+"");
                if (response.code() == 200){
                    if(response.body().getCode() == 100){
                        Log.d("projects","获取到");
                        projectList = response.body().getContent();
                        rcv_projects = (RecyclerView)getView().findViewById(R.id.rcv_projects);
                        rcv_projects.setLayoutManager(new LinearLayoutManager(getContext()));
                        rcv_projects.setAdapter(projectsAdapter = new ProjectsAdapter());
                        rcv_projects.addItemDecoration(new DividerItemDecoration(getContext(),
                                DividerItemDecoration.VERTICAL_LIST));
                    }
                }
            }

            @Override
            public void onFailure(Call<Result<List<Project>>> call, Throwable t) {
                Log.d("projects","加载失败");
            }
        });
    }

    class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    getContext()).inflate(R.layout.list_item_projects, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position)
        {
            holder.tv_name.setText("项目名称： "+projectList.get(position).getProject_name());
            holder.tv_detail.setText("项目详情："+projectList.get(position).getProject_detail());
            holder.imgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(),TaskgroupViewPagerActivity.class);
                    intent.putExtra("project_id",projectList.get(position).getProject_id());
                    startActivity(intent);

                }
            });
        }

        @Override
        public int getItemCount()
        {
            return projectList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {

            TextView tv_name;
            TextView tv_detail;
            ImageButton imgbtn;

            public MyViewHolder(View view)
            {
                super(view);
                tv_name = (TextView) view.findViewById(R.id.tv_project_name);
                tv_detail = (TextView)view.findViewById(R.id.tv_project_detail);
                imgbtn = (ImageButton) view.findViewById(R.id.imgbtn_to_project_detail);
            }
        }
    }

}
