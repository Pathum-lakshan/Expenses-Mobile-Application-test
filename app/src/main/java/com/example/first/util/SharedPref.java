package com.example.first.util;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;


import com.example.first.model.User;
import com.google.gson.Gson;

public class SharedPref {

    private SharedPreferences userData;

    public SharedPref(Context context)
    {

         userData = context.getSharedPreferences("userData",MODE_PRIVATE);
    }


    public void saveUser(User user)
    {

       SharedPreferences.Editor editor = userData.edit();
       Gson gson = new Gson();
       String json = gson.toJson(user);

       editor.putString("user",json);
       editor.commit();

    }

    public User getUser()
    {
        User user = null;
        Gson gson = new Gson();

        if (userData.contains("user"))
        {
            String json = userData.getString("user","{}");
            user = gson.fromJson(json,User.class);

        }
        return user;
    }

}
