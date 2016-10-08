package com.weesoo.selectpic;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.io.File;

/**
 * Created by Administrator on 2016/6/23.
 */
public class SelectPic {
    private PopupWindow popWindow;
    private LayoutInflater layoutInflater;
    private LinearLayout llayout_pop_takephoto, llayout_pop_pickphoto, llayout_pop_cancel, top_bg;
    private Activity activity;
    private int resource;
    private static Uri outputFileUri;
    public final static int TAKE_PHOTO = 1;
    public final static int PICK_PHOTO = 2;

    public SelectPic(Activity activity, int laoutRes) {
        this.activity = activity;
        resource = laoutRes;
    }

    public void showPop() {
        //设置contentView
        View contentView = LayoutInflater.from(activity).inflate(R.layout.pop_menu, null);
        popWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        popWindow.setContentView(contentView);
        //点击外部消失
        popWindow.setFocusable(true); // 设置PopupWindow可获得焦点
        popWindow.setTouchable(true); // 设置PopupWindow可触摸
        popWindow.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸


        //设置各个控件的点击响应

        //显示PopupWindow
        View rootview = LayoutInflater.from(activity).inflate(resource, null);
        popWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
        initPop(contentView);
    }

    private void initPop(View view) {
        llayout_pop_takephoto = (LinearLayout) view.findViewById(R.id.llayout_pop_takephoto);
        llayout_pop_pickphoto = (LinearLayout) view.findViewById(R.id.llayout_pop_pickphoto);
        llayout_pop_cancel = (LinearLayout) view.findViewById(R.id.llayout_pop_cancel);
        top_bg = (LinearLayout) view.findViewById(R.id.top_bg);

        //点击了拍照
        llayout_pop_takephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
        //点击了相册选择
        llayout_pop_pickphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhoto();
            }
        });
        //点击取消
        llayout_pop_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        top_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
    }

    private void pickPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, PICK_PHOTO);
        popWindow.dismiss();
    }

    private void takePhoto() {
        File file = FileUtils.createImageFile();
        outputFileUri = Uri.fromFile(file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        activity.startActivityForResult(intent, TAKE_PHOTO);
        CameraUri();
        popWindow.dismiss();
    }

    public static   Uri CameraUri() {
        Uri uri = outputFileUri;
        return uri;
    }
}
