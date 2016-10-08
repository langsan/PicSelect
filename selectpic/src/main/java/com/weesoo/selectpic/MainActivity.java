package com.weesoo.selectpic;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
    private ImageView imageview_pick, imageview_take;
    Button btn_select;
    private String jiashiUri = null;
    private Bitmap bitmap_xingshi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageview_pick = (ImageView) findViewById(R.id.imageview_pick);
        imageview_take = (ImageView) findViewById(R.id.imageview_take);
        btn_select = (Button) findViewById(R.id.btn_select);
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SelectPic(MainActivity.this, R.layout.activity_main).showPop();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == SelectPic.TAKE_PHOTO) {
            //获取拍照照片的真实地址
//            try {
//                bitmap_xingshi = MediaStore.Images.Media.getBitmap(this.getContentResolver(), SelectPic.CameraUri());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            jiashiUri = getRealPathFromURI(SelectPic.CameraUri());
            imageview_take.setImageBitmap(PictureTools.getSmallBitmap(getRealPathFromURI(SelectPic.CameraUri()), 400, 800));

        }
        if (resultCode == RESULT_OK && requestCode == SelectPic.PICK_PHOTO) {
            //获取相册照片的真实地址
            jiashiUri = getRealPathFromURI(data.getData());
            imageview_pick.setImageBitmap(PictureTools
                    .getSmallBitmap(getRealPathFromURI(data.getData()), 480, 800));

        }
    }

    //获取真实路径啊啊啊
    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}