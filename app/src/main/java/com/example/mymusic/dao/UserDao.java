package com.example.mymusic.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mymusic.until.DBUntil;

public class UserDao {

    public static SQLiteDatabase db = DBUntil.con;

    /**
     * 输入账号和密码来判断是否可以登录 区分管理员与用户
     * @param account
     * @param pwd
     * @return
     */
    public static int isLogin(String account, String pwd) {
        String[] data = {account, pwd};
        String sql = "select * from d_user where account = ? and pwd = ?";

        Cursor result = db.rawQuery(sql, data);

        if(result.moveToNext()) {
            int powIndex = result.getColumnIndex("pow");

            // 获取列值
            int pow = result.getInt(powIndex);
            return pow;
        }
        return -1;
    }

    /**
     * 注册用户
     * @param account
     * @param pwd
     * @param nickName
     * @param img
     * @param address
     * @param sex
     * @return
     */
    public static int register(String account, String pwd, String nickName, String img, String address, String sex) {
        String[] data = {account, nickName, pwd, "0", img, address, sex};
        String sql = "insert into d_user(account, nickname, pwd, pow, img, address, sex) values(?, ?, ?, ?, ?, ?, ?)";

        try {
            db.execSQL(sql, data);
            return 1;
        } catch (Exception e) {
            Log.e("Database Error", "Error executing SQL: " + e.getMessage(), e); // 输出异常信息
            return 0;
        }
    }

}
