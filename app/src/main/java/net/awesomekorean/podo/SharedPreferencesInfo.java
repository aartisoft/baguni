package net.awesomekorean.podo;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesInfo {

    static Gson gson = new GsonBuilder().create();

    // 앱에 유저 데이터 저장하기
    public static void setUserInfo(Context context, UserInformation userInformation) {
        String stringToSP = gson.toJson(userInformation, UserInformation.class);
        SharedPreferences sp = context.getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("info", stringToSP);
//        editor.clear();
        editor.commit();
    }

    // 엡에서 유저 데이터 불러오기
    public static UserInformation getUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("user", MODE_PRIVATE);
        String stringFromSP = sp.getString("info", "");
        UserInformation userInformation = gson.fromJson(stringFromSP, UserInformation.class);
        return userInformation;
    }

    // 모든 데이터 지우기 (개발용)
    public static void deleteUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    // 마지막 동기화 날짜 불러오기
    public static String getDateLastSync(Context context) {
        SharedPreferences sp = context.getSharedPreferences("date", MODE_PRIVATE);
        String dateLastSync = sp.getString("dateLastSync", "0000-00-00 00:00:00");
        return dateLastSync;
    }

    // 마지막 동기화 날짜 저장하기
    public static void setDateLastSync(Context context, String dateLastSync) {
        SharedPreferences sp = context.getSharedPreferences("date", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("dateLastSync", dateLastSync);
        editor.commit();
    }
}