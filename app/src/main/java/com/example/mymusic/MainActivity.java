package com.example.mymusic;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mymusic.dao.UserDao;
import com.example.mymusic.until.DBUntil;
import com.example.mymusic.until.Tools;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // 先初始化数据库
        DBUntil db = new DBUntil(this);

        // 获取登录信息
        EditText accountT = findViewById(R.id.login_account);
        EditText pwdT = findViewById(R.id.login_pwd);
        Button loginBtn = findViewById(R.id.login_login_btn);

        // 执行登录
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = accountT.getText().toString();
                String pwd = pwdT.getText().toString();

                // 判断账号和用户名
                if (account == null || account.equals("")) {
                    Tools.Toast(MainActivity.this, "请输入账号");
                } else if (pwd == null || pwd.equals("")) {
                    Tools.Toast(MainActivity.this, "请输入密码");
                } else {
                    // 登录后判断权限
                    int status = UserDao.isLogin(account, pwd);

                    if (status == 0) {
                        Tools.Toast(MainActivity.this, "管理员登录");
                    } else if (status == 1) {
                        Tools.Toast(MainActivity.this, "用户登录");
                    } else {
                        Tools.Toast(MainActivity.this, "数据格式错误");
                    }

                }

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}