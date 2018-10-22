package com.tdp2.quechuaapp.login.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.tdp2.quechuaapp.login.LoginActivity;

public class UserSessionManager {

    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    private Context context;

    // Shared preferences mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    public static final String PREFER_NAME = "Reg";

    // All Shared Preferences Keys
    public static final String IS_USER_LOGIN = "isUserLoggedIn";

    public static final String USER_LOGGED = "userLogged";
    //public static final String KEY_LAST_NAME = "lastName";
    public static final String KEY_TOKEN = "accessToken";


    public UserSessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void saveUserLogged(UserLogged userLogged, String accessToken){
        editor.putBoolean(IS_USER_LOGIN, true);
        // Storing name in preferences
        //editor.putString(KEY_FIRST_NAME, userLogged.firstName);
        //editor.putString(KEY_LAST_NAME, userLogged.lastName);
        Gson gson = new Gson();
        String userAsJson = gson.toJson(userLogged);
        editor.putString(USER_LOGGED, userAsJson);
        editor.putString(KEY_TOKEN, accessToken);

        // commit changes
        editor.commit();
    }

    public String getAuthorizationToken() {
        String accessToken = pref.getString(KEY_TOKEN, null);
        return accessToken;
    }

    /*public String getFullName() {
        String name = pref.getString(KEY_FIRST_NAME, null);
        String lastName = pref.getString(KEY_LAST_NAME, null);
        return name + " " + lastName;
    }*/

    public UserLogged getUserLogged() {
        Gson gson = new Gson();
        String userAsJson = pref.getString(USER_LOGGED, null);
        return gson.fromJson(userAsJson, UserLogged.class);
    }

    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

    /**
     * Clear session details
     * */
    public void logout() {

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to MainActivity
        Intent intent = new Intent(context, LoginActivity.class);

        // Closing all the Activities
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(intent);
    }

}
