package xyz.zhenhua.ateam.data.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import xyz.zhenhua.ateam.data.model.Result;
import xyz.zhenhua.ateam.data.model.Task;

/**
 * Created by zachary on 2017/5/20.
 */

public interface TaskService {

    @POST("task/create")
    public Call<Result> createTask(@Header("authorization") String token  , @Body Task task);

    @POST("task/delete")
    public Call<Result> deleteTask(@Header("authorization") String token , @Query("task_id") long task_id);

    @POST("task/getdetail")
    public Call<Result<Task>> getDetail(@Header("authorization") String token , @Query("task_id") long task_id);

    @POST("task/gettasks")
    public Call<Result<List<Task>>> get(@Header("authorization") String token , @Query("taskgroup_id") long taskgroup_id);

    @POST("task/updata")
    public Call<Result> modifyStatus(@Header("authorization") String token , @Query("task_id") long task_id
            , @Query("new_status") String new_status);

    @POST("task/gettasklist")
    public Call<Result<List<Task>>> getTasks(@Header("authorization") String token);

    // TODO: 2017/5/25 v2接口
    @POST("task/v2/getalltask")
    public Call<Result<List<Task>>> v2GetAllTask(@Header("authorization") String token );

    @POST("task/v2/getaptasks")
    public Call<Result<List<Task>>> v2GetAPTasks(@Header("authorization") String token , @Query("project_id") long project_id);

    @POST("task/v2/createtask")
    public Call<Result> v2CreateTask(@Header("authorization") String token , @Body Task task ,@Query("project_id") long project_id);
}
