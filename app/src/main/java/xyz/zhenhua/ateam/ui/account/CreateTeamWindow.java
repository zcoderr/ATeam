package xyz.zhenhua.ateam.ui.account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import xyz.zhenhua.ateam.R;

/**
 * Created by zachary on 2017/5/20.
 */

public class CreateTeamWindow extends PopupWindow {
    private Context context;
    private LayoutInflater inflater;
    private View mview;
    private EditText tv_tean_name;
    private Button btn_commit;
    public CreateTeamWindow(Context context) {
        super(context);
        this.context = context;
    }
    public void initView(){
        inflater = LayoutInflater.from(context);
        mview = inflater.inflate(R.layout.window_createteam,null);
        setContentView(mview);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        tv_tean_name = (EditText) mview.findViewById(R.id.et_team_name);
        btn_commit = (Button)mview.findViewById(R.id.btn_commit);
    }


}
