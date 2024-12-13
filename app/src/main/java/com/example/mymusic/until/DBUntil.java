package com.example.mymusic.until;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import androidx.annotation.Nullable;

/**
 * 用于链接数据库
 */
public class DBUntil extends SQLiteOpenHelper {
    private static final int version = 2; // 版本号，每次更改表结构都需要+1 否则不生效

    private static final String databaseName = "db_music.db"; // 数据库名称 以db结尾

    private static SQLiteDatabase con; // 链接数据库的链接，通过该对象可以操作数据库

    private Context context;

    public DBUntil(@Nullable Context context) {
        super(context, databaseName, null, version, null);
        this.context = context;
        con = this.getWritableDatabase(); // 创建数据库的链接
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("PRAGMA foreign_keys = false");
        // --------------------------------------
        // 所有数据库操作

        // 创建一个用户表
        db.execSQL("drop table if exists d_user");

        // 账号，昵称，密码，头像，权限，地址，性别，听歌时长，曲数，注册时间
        db.execSQL("create table d_user(account varchar(20) primary key," +
                "nickname VARCHAR(50)," +
                "pwd VARCHAR(50)," +
                "img VARCHAR(50)," +
                "pow VARCHAR(50)," +
                "address VARCHAR(50)," +
                "sex VARCHAR(50)," +
                "listening_time INTEGER DEFAULT 0," +
                "song_count INTEGER DEFAULT 0," +
                "registration_time DEFAULT CURRENT_TIMESTAMP" +
                ")");

        // 插入数据
        // 用户数据
        String[] data = {"admin", "小羊", "123456", "0", "", "天津", "女"};
        db.execSQL("insert into d_user (account, nickname, pwd, pow, img, address, sex) values(?, ?, ?, ?, ?, ?, ?)", data);
        // 管理员数据
        String[] data1 = {"root", "小杰", "123456", "1", "", "杭州", "男"};
        db.execSQL("insert into d_user (account, nickname, pwd, pow, img, address, sex) values(?, ?, ?, ?, ?, ?, ?)", data1);

        // --------------------------------------
        db.execSQL("PRAGMA foreign_keys = true");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
    
}
