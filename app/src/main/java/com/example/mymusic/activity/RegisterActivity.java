package com.example.mymusic.activity;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mymusic.R;
import com.example.mymusic.dao.UserDao;
import com.example.mymusic.until.FileUntil;
import com.example.mymusic.until.Tools;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private Uri result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        // 注册页面返回功能
        Toolbar bar = findViewById(R.id.register_bar);
        setSupportActionBar(bar);
        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 实现注册功能
        ImageView avatar = findViewById(R.id.register_avatar);

        EditText nick = findViewById(R.id.register_nick);

        EditText account = findViewById(R.id.register_account);

        EditText address = findViewById(R.id.register_address);

        EditText pwd = findViewById(R.id.register_pwd);

        RadioButton sex = findViewById(R.id.register_man);
        sex.setChecked(true);

        Button reg = findViewById(R.id.register_btn);

        // 头像上传
        avatar.setOnClickListener(v -> {
            openGallery(v);
        });

        // 对getContentLauncher初始化
        getContentLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                if(uri != null) {
                    avatar.setImageURI(uri);
                    result = uri;
                } else {
                    Tools.Toast(RegisterActivity.this, "未选择头像");
                }
            }
        });

        // 注册按钮点击
        reg.setOnClickListener(v -> {
            if (nick.getText().toString().equals("")) {
                Tools.Toast(RegisterActivity.this, "请输入昵称");
            } else if (account.getText().toString().equals("")) {
                Tools.Toast(RegisterActivity.this, "请输入账号");
            } else if (pwd.getText().toString().equals("")) {
                Tools.Toast(RegisterActivity.this, "请输入密码");
            } else if (address.getText().toString().equals("")) {
                Tools.Toast(RegisterActivity.this, "请输入地址");
            } else if (result == null) {
                Tools.Toast(RegisterActivity.this, "请上传头像");
            } else {
                String sexStr = sex.isChecked() ? "男" : "女";
                String accountT = account.getText().toString();
                String pwdT = pwd.getText().toString();
                String nickT = nick.getText().toString();
                String addressT = address.getText().toString();

                // 头像处理: 将内容存储到本地，返回一个路径来存储
                String path = FileUntil.saveImageBitmapToFileImg(result, RegisterActivity.this); // 保存图片

                int status = UserDao.register(accountT, pwdT, nickT, path, addressT, sexStr);
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

    private ActivityResultLauncher<String> getContentLauncher;

    /**
     * 打开相册
     * @param v
     */
    private void openGallery(View v) {
        getContentLauncher.launch("image/*");
    }
}