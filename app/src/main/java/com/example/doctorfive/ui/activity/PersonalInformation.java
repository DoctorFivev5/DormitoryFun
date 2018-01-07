package com.example.doctorfive.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.doctorfive.db.DBHelper;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.User;
import com.example.doctorfive.util.CircleCropUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class PersonalInformation extends AppCompatActivity implements View.OnClickListener {

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    public static final int CHANGE_IMFORMATION = 0x11;
    private Uri imageUri;
    private TextView goback;
    private TextView edit;
    private ImageView bg;
    private ImageView icon;
    private TextView username;
    private TextView autograph;
    private TextView phoneNum;
    private TextView sex;
    private TextView school;

    private View inflate;
    private Button choosePhoto;
    private Button takePhoto;
    private Button cancel;
    private Dialog dialog;
    private DBHelper dbHelper;
    private User myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_information);
        initView();
        changeData();
    }

    private void changeData() {
        username.setText(myUser.getUsername());
        autograph.setText(myUser.getAutograph());
        phoneNum.setText(myUser.getPhoneNum());
        sex.setText(myUser.isSex()?"男":"女");
        school.setText(myUser.getSchool());
        loadingHeaderIcon(myUser.getIcon());
    }

    private void initView(){
        dbHelper = new DBHelper(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        myUser = new User();
        myUser.setPhoneNum(bundle.getString("phoneNum"));
        myUser = dbHelper.export(myUser);
        goback = (TextView) findViewById(R.id.left_text);
        edit = (TextView) findViewById(R.id.right_text);
        bg = (ImageView) findViewById(R.id.bg);
        icon = (ImageView) findViewById(R.id.icon_image);
        username = (TextView) findViewById(R.id.username);
        autograph = (TextView) findViewById(R.id.autograph);
        phoneNum = (TextView) findViewById(R.id.phoneNum);
        sex = (TextView) findViewById(R.id.sex);
        school = (TextView) findViewById(R.id.school);
        goback.setOnClickListener(this);
        edit.setOnClickListener(this);
        bg.setOnClickListener(this);
        icon.setOnClickListener(this);
        changeData();
    }


    private void loadingHeaderIcon(String url){
        Glide.with(this).load(url)
                .transform(new CircleCropUtil(this))
                .placeholder(R.mipmap.ic_launcher)
                .into(icon);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.left_text:
                finish();
                break;
            case R.id.right_text:
                Intent intent1 =  new Intent(PersonalInformation.this, EditImformationActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("myUser",myUser);
                intent1.putExtras(bundle1);
                startActivityForResult(intent1,CHANGE_IMFORMATION);
                break;
            case R.id.bg:
                //Toast.makeText(this,"更换背景",Toast.LENGTH_SHORT).show();
                break;
            case R.id.icon_image:
                showMyDialog();
                break;
            case R.id.takePhoto:
                File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
                try{
                    if (outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT>=24){
                    imageUri = FileProvider.getUriForFile(PersonalInformation.this,"com.example.doctorfive.dormitoryfun.fileprovider",outputImage);
                }else {
                    imageUri = Uri.fromFile(outputImage);
                }
                //启动相机程序
                Intent intent2 = new Intent("android.media.action.IMAGE_CAPTURE");
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent2,TAKE_PHOTO);
                dialog.dismiss();
                break;
            case R.id.choosePhoto:
                if(ContextCompat.checkSelfPermission(PersonalInformation.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(PersonalInformation.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else {
                    openAlbum();
                }
                dialog.dismiss();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
            default:
                break;

        }
    }

    public void showMyDialog(){
        dialog = new Dialog(this,R.style.myDialog);
        inflate = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
        choosePhoto = (Button) inflate.findViewById(R.id.choosePhoto);
        takePhoto = (Button) inflate.findViewById(R.id.takePhoto);
        cancel = (Button) inflate.findViewById(R.id.btn_cancel);
        choosePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        cancel.setOnClickListener(this);
        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity( Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 40;
        dialogWindow.setAttributes(lp);
        dialog.show();
    }
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);//打开相册
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK){
                        //将拍摄的照片显示出来
                    loadingHeaderIcon(imageUri.toString());
                    myUser.setIcon(imageUri.toString());
                    dbHelper.update(myUser);
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK){
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT>=19){
                        //4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    }else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
        switch (resultCode){
            case CHANGE_IMFORMATION:
                Bundle bundle = data.getExtras();
                myUser = (User) bundle.getSerializable("myUser");
                changeData();
                break;
        }
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)){
            //如果是document类型的Uri，则通过document id 处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的Uri，则使用普通方法处理
            imagePath = getImagePath(uri, null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            //如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath);//根据图片路径显示图片
    }
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    private void displayImage(String imagePath) {

        if (imagePath != null){
            loadingHeaderIcon(imagePath);
            myUser.setIcon(imagePath);
            dbHelper.update(myUser);
        }else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
}
