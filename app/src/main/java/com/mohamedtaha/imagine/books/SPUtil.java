package com.mohamedtaha.imagine.books;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class SPUtil {
    private SPUtil(){}
    public static final String PREF_NAME = "BooksPReferences";
    public static final String POSITION = "position";
    public static final String QUERY = "query";

    public static SharedPreferences getPref(Context context){
        return context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
    }
    public static String getPreferencesString(Context context, String key){
        return getPref(context).getString(key,"");
    }
    public static int getPreferencesInt(Context context, String key){
        return getPref(context).getInt(key,0);
    }
    public static void setPreferencesString(Context context,String key, String value){
        SharedPreferences.Editor editor = getPref(context).edit();
        editor.putString(key,value);
        editor.apply();
    }
    public static void setPreferencesInt(Context context,String key, int value){
        SharedPreferences.Editor editor = getPref(context).edit();
        editor.putInt(key,value);
        editor.apply();
    }
    public static ArrayList<String> getQueryList(Context context){
        ArrayList<String> queryList = new ArrayList<>();
        for (int i=1;i<5;i++){
            String query = getPref(context).getString(QUERY + String.valueOf(i),"");
            if (!query.isEmpty()){
                query = query.replace(",","");
                queryList.add(query.trim());
            }
        }return queryList;
    }

}
