package com.example.doctorfive.ui.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.example.doctorfive.base.BaseActivity;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.weight.RoundEditImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URI;

public class SelectCircleActivity extends BaseActivity {

    private RoundEditImageView imageView;
    private Button button;
    private Bitmap bitmap;
    private Uri imageFromPath;//拍照存储的路径
    private Uri imageToPath;
    private Uri outputfile1;
    private int code;
    private File outputImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectcircle);
        initView();
    }

    private void initView(){
        outputImage = new File(getExternalCacheDir(), "usericon.png");
        Intent intent = getIntent();
        /*
        Bundle bundle = intent.getExtras();

        imageFromPath = Uri.parse(bundle.getString("imageFromPath"));
        code = bundle.getInt("code");
        imageToPath = Uri.parse(bundle.getString("imageToPath"));
        */
        imageFromPath = Uri.parse(intent.getStringExtra("outputfile1"));
        imageToPath = intent.getData();
        code = intent.getIntExtra("code",1);
        //outputfile1 = intent.getData();
        Log.e("selectimage",imageFromPath.toString());
        Log.e("selectimage",imageToPath.toString());
        Log.e("code",code+" ");


        //imageFromPath = FileProvider.getUriForFile(SelectCircleActivity.this,"com.example.doctorfive.dormitoryfun.fileprovider",outputImage);
        //Log.e("selectimage",imageFromPath.toString());
        imageView = (RoundEditImageView) findViewById(R.id.selectcircle_image);
        button = (Button) findViewById(R.id.selectcircle_button);
        //
        if (code==1){
            imageFromPath = FileProvider.getUriForFile(SelectCircleActivity.this,"com.example.doctorfive.dormitoryfun.fileprovider",outputImage);
            Log.e("selectimage",imageFromPath.toString());
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageFromPath));
            } catch (FileNotFoundException e) {
                Log.e("FileNotFoundException","文件没找到");
                e.printStackTrace();
            }
        }else if (code==2){
            bitmap = BitmapFactory.decodeFile(imageFromPath.getPath());
        }

        /*
        file uri   /storage/emulated/0/DCIM/Camera/IMG_20180403_172532.jpg 这个能找到文件
        Content URI   /my_images/Android/data/com.example.doctorfive.dormitoryfun/cache/usericon.png
        */
        imageView.setImageBitmap(bitmap);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //生成一个300*300的当前亮圆形中的图片
                Bitmap result = imageView.extractBitmap(300);
                //压缩成png
                try {
                    FileOutputStream out = new FileOutputStream(outputImage);
                    result.compress(Bitmap.CompressFormat.PNG, 100, out);
                    Intent intent1 =  new Intent(SelectCircleActivity.this, PersonalInformation.class);
                    setResult(3, intent1);
                    finish();
                } catch (FileNotFoundException e) {
                    Log.e("SelectCircleActivity","找不到文件");
                    e.printStackTrace();
                }

            }
        });
    }


}
