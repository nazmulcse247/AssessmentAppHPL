package com.nazmul.assessmentapphpl.database;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreference {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static final String LOGIN_STATUS = "status";


    public UserPreference(Context context){
        sharedPreferences = context.getSharedPreferences("user_auth",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLoginStatus(String phone){
        editor.putString(LOGIN_STATUS,phone);
        editor.commit();
    }

    public String getLoginStatus(){
        return sharedPreferences.getString(LOGIN_STATUS,null);

    }
}
