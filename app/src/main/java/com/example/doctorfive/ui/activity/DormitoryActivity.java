package com.example.doctorfive.ui.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.doctorfive.base.BaseActivity;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.User;

/**
 * tab我的 --> 我的宿舍界面
 */
public class DormitoryActivity extends BaseActivity implements View.OnClickListener, View.OnLongClickListener {

    private ImageView userIconOne;
    private ImageView userIconTwo;
    private ImageView userIconThree;
    private ImageView userIconFour;
    private User myUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dormitory);
        initView();
    }

    private void initView() {

        Intent intent = getIntent();
        myUser = (User) intent.getSerializableExtra("myUser");
        userIconOne = (ImageView) findViewById(R.id.user_icon_one);
        userIconTwo = (ImageView)findViewById(R.id.user_icon_two);
        userIconThree = (ImageView)findViewById(R.id.user_icon_three);
        userIconFour = (ImageView)findViewById(R.id.user_icon_four);

        userIconOne.setOnClickListener(this);
        userIconOne.setOnLongClickListener(this);

        initIcon();
    }

    private void initIcon() {
        loadingHeaderIcon(userIconOne);
        loadingHeaderIcon(userIconTwo);
        loadingHeaderIcon(userIconThree);
        loadingHeaderIcon(userIconFour);
        ObjectAnimator.ofFloat(userIconOne, "X", 200F, 400F, 200F, 400F).setDuration(5000).start();
        ObjectAnimator.ofFloat(userIconOne, "Y", 200F, 400F, 200F, 400F).setDuration(5000).start();
        ObjectAnimator.ofFloat(userIconFour, "X", 400F, 600F, 400F, 600F).setDuration(5000).start();
        ObjectAnimator.ofFloat(userIconFour, "Y", 400F, 600F, 400F, 600F).setDuration(5000).start();

    }


    /**
     * 根据url加载头像
     */
    private void loadingHeaderIcon(ImageView imageView){
        String map_url = myUser.getUserIcon();
        Log.e("MyFragment", map_url+"");
        Glide.with(this).load(map_url)
                //.diskCacheStrategy(DiskCacheStrategy.NONE)
                //.skipMemoryCache(true)
                //.transform(new CircleCrop(getContext()))
                .dontAnimate()
                .into(imageView);
    }

    @Override
    public void onClick(View v) {
        Log.e("onClick","ok");
        switch (v.getId()){
            case R.id.user_icon_one:

        }

    }

    @Override
    public boolean onLongClick(View v) {
        Log.e("onLongClick","ok");

        return true;
    }
}
