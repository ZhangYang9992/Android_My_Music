package com.example.mymusic.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
}
