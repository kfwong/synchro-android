package com.synchro.synchro_prototype01;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by angja_000 on 26/5/2016.
 *
 *  handles storage of API key and authentication token for ivle via SharedPref
 *  can be reset when token/key expires
 *  is this secure?
 */
public class StoreData {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public StoreData(Context context) {
        prefs = context.getSharedPreferences("pref_01", Context.MODE_PRIVATE);
        editor = prefs.edit();
        setApiKey("PK3n2PGjXR4OooZPZyelQ");
    }

    public String getApiKey() {return prefs.getString("apiKey", "error");}
    public String getAuthToken() {return prefs.getString("authToken", "error");}

    public boolean setApiKey(String newKey) {
        editor.putString("apiKey", newKey);
        editor.commit();
        return true;
    }
    public boolean setAuthToken(String token) {
        editor.putString("authToken", token);
        editor.commit();
        return true;
    }
}
