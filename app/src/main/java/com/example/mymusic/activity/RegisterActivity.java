package com.example.mymusic.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mymusic.R;
import com.example.mymusic.dao.UserDao;
import com.example.mymusic.until.Tools;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        // 实现注册功能
        ImageView avatar = findViewById(R.id.register_avatar);
        EditText nick = findViewById(R.id.register_nick);
        EditText account = findViewById(R.id.register_account);
        EditText address = findViewById(R.id.register_address);
        EditText pwd = findViewById(R.id.register_pwd);
        RadioButton sex = findViewById(R.id.register_man);
        sex.setChecked(true);

        Button reg = findViewById(R.id.register_btn);

        reg.setOnClickListener(v -> {
            if (nick.getText().toString().equals("")) {
                Tools.Toast(RegisterActivity.this, "请输入昵称");
            } else if (account.getText().toString().equals("")) {
                Tools.Toast(RegisterActivity.this, "请输入账号");
            } else if (pwd.getText().toString().equals("")) {
                Tools.Toast(RegisterActivity.this, "请输入密码");
            } else if (address.getText().toString().equals("")) {
                Tools.Toast(RegisterActivity.this, "请输入地址");
            } else {
                String sexStr = sex.isChecked() ? "男" : "女";
                String accountT = account.getText().toString();
                String pwdT = pwd.getText().toString();
                String nickT = nick.getText().toString();
                String addressT = address.getText().toString();

                int status = UserDao.register(accountT, pwdT, nickT, "", addressT, sexStr);
                if (status == 1) {
                    Tools.Toast(RegisterActivity.this, "注册成功");
                } else {
                    Tools.Toast(RegisterActivity.this, "注册失败");
                }
            }
        });


//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
    }

}