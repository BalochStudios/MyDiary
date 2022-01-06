package com.muaz.mydiary.ui.others;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreference {
    static SharedPreferences usersSession;
    SharedPreferences.Editor editor;
    Context context;

    public  static void  setCurrentTheme(Context context, String position){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        pref.edit().putString("position",position).apply();

    }
    public static String getCurrentTheme(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String position=pref.getString("position","");
        return position;
    }


}
