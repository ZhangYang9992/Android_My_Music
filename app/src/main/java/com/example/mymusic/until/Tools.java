package com.example.mymusic.until;

import android.content.Context;
import android.widget.Toast;

public class Tools {

    /**
     * 弹窗方法
     * @param context
     * @param text
     */
    public static void Toast(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
