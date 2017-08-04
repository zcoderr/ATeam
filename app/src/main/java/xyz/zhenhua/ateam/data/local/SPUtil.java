package xyz.zhenhua.ateam.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import xyz.zhenhua.ateam.data.model.User;
import xyz.zhenhua.ateam.data.model.UserAuths;

/**
 * Created by zachary on 2017/5/21.
 */

public class SPUtil {

    Context context;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public SPUtil(Context context){
        this.context = context;
        sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void setToken(String token){
        editor.putString("token",token);
        editor.commit();
    }

    public String getToken(){
        return sp.getString("token" , "");
    }

    public void setCurrentUserAuth(String email , String password){
        editor.putString("email" , email);
        editor.putString("password" , password);
        editor.commit();
    }
    public UserAuths getCurrentUserAuth(){
        UserAuths userAuths = new UserAuths();
        userAuths.setEmail(sp.getString("email",""));
        userAuths.setPassword(sp.getString("password" , ""));
        return userAuths;
    }

    public void logout(){
        editor.putString("token" , "");
        editor.putString("email" , "");
        editor.putString("password" , "");
        editor.commit();
    }
}
