package com.example.mymusic.until;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import android.net.Uri;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.mymusic.R;

public class FileUntil {

    /**
     * 生成随机图片的名字
     * @return
     */
    public static String getFileName() {
        // 随机生成的图片目录
        String pigName = "/" + UUID.randomUUID().toString().replace("-", "") + ".png";
        // 环境的绝对目录
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + pigName;
    }

    /**
     * 将二进制图片保存到png
     * @param uri
     * @param context
     */
    public static String saveImageBitmapToFileImg(Uri uri, Context context) {
        String path = FileUntil.getFileName();

        CustomTarget<Bitmap> target = new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                File file = new File(path); // 创建文件

                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    resource.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        };

        // 实现保存
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .into(target);

        return path;
    }


    /**
     * 将drawable里的文件转换并保存到手机的真实位置中
     * 将apk的内容释放
     * @param context
     * @param id
     */
    public static String saveDrawableToFileSave(Context context, int id) {
        String path = FileUntil.getFileName();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Drawable defaultDrawable = ContextCompat.getDrawable(context, id);
                Bitmap bitmapDef = ((BitmapDrawable) defaultDrawable).getBitmap(); // 获取图片二进制文件
                FileUntil.saveImageBitmapToFileImgEx(bitmapDef, path); // 保存图片
            }
        });

        return path;
    }

    /**
     * 将二进制转为文件
     * @param bitmap
     * @param path
     */
    public static void saveImageBitmapToFileImgEx(Bitmap bitmap, String path) {
        File file = new File(path);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
