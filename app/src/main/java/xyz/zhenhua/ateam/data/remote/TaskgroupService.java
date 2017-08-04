package xyz.zhenhua.ateam.data.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.adapter.rxjava.Result;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import xyz.zhenhua.ateam.data.model.Taskgroup;

/**
 * Created by zachary on 2017/5/20.
 */

public interface TaskgroupService {

    @POST("taskgroup/create")
    public Call<Result> createTaskgroup(@Header("authorization") String token , @Query("taskgroup_name") String name
            , @Query("project_id") long project_id);

    @POST("taskgroup/delete")
    public Call<Result> deleteTaskgroup(@Header("authorization") String token , @Query("taskgroup_id") long taskgroup_id);


    @POST("taskgroup/get")
    public Call<Result<List<Taskgroup>>> getTaskGroups(@Header("authorization") String token , @Query("project_id") long project_id);

}
