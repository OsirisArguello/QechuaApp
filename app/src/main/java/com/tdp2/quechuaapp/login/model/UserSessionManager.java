package com.tdp2.quechuaapp.login.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.tdp2.quechuaapp.login.LoginActivity;
import com.tdp2.quechuaapp.model.PeriodoActividad;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UserSessionManager {

    public static final String SELECTED_ROLE = "selectedRole";
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

    public static final String KEY_TOKEN = "accessToken";

    public static final String KEY_ACTIVITIES = "activitiesPeriod";

    public UserSessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void saveActividadValida(ArrayList<PeriodoActividad> actividades) {
        Set<String> set = new HashSet<>();
        for (PeriodoActividad actividad: actividades) {
            set.add(actividad.name());
        }

        editor.putStringSet(KEY_ACTIVITIES, set);

        // commit changes
        editor.commit();
    }

    public void saveUserLogged(UserLogged userLogged, String accessToken){
        editor.putBoolean(IS_USER_LOGIN, true);
        // Storing name in preferences
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

    public ArrayList<PeriodoActividad> getActividadValida() {
        Set<String> set = pref.getStringSet(KEY_ACTIVITIES, null);
        ArrayList<PeriodoActividad> returnSet = new ArrayList<>();
        for (String name: set) {
            returnSet.add(PeriodoActividad.valueOf(name));
        }
        return returnSet;
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
