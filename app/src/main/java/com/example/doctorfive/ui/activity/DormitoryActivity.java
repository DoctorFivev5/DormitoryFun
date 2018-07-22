package com.example.doctorfive.ui.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.doctorfive.adapter.MessageFourRecyclerViewAdapter;
import com.example.doctorfive.adapter.MessageOneRecyclerViewAdapter;
import com.example.doctorfive.adapter.MessageThreeRecyclerViewAdapter;
import com.example.doctorfive.adapter.MessageTwoRecyclerViewAdapter;
import com.example.doctorfive.base.BaseActivity;
import com.example.doctorfive.db.DBHelper;
import com.example.doctorfive.dormitoryfun.R;
import com.example.doctorfive.entity.User;
import com.example.doctorfive.entity.UserMessage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * tab我的 --> 我的宿舍界面
 */
public class DormitoryActivity extends BaseActivity implements View.OnClickListener {

    private ImageView userIconOne;
    private ImageView userIconTwo;
    private ImageView userIconThree;
    private ImageView userIconFour;
    private User myUser;
    private User user2;
    private User user3;
    private User user4;

    private List<UserMessage> messageList = new ArrayList<>();
    private EditText inputText;
    private Button sendMessageButton;
    private RecyclerView oneMessageRecycler;
    private RecyclerView twoMessageRecycler;
    private RecyclerView threeMessageRecycler;
    private RecyclerView fourMessageRecycler;

    private MessageOneRecyclerViewAdapter messageOneRecyclerViewAdapter;
    private MessageTwoRecyclerViewAdapter messageTwoRecyclerViewAdapter;
    private MessageThreeRecyclerViewAdapter messageThreeRecyclerViewAdapter;
    private MessageFourRecyclerViewAdapter messageFourRecyclerViewAdapter;

    private DBHelper myDBHelper;
    private DBHelper.DBListener dbListener;
    private Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    Toast.makeText(DormitoryActivity.this, "网络不佳", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    sendMessageSuccess((UserMessage) msg.obj);
                    Toast.makeText(DormitoryActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    //messageList.clear();
                    messageList = (List<UserMessage>) msg.obj;
                    messageOneRecyclerViewAdapter = new MessageOneRecyclerViewAdapter(messageList);
                    oneMessageRecycler.setAdapter(messageOneRecyclerViewAdapter);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dormitory);
        initView();
    }

    private void initView() {
        dbListener = new DBHelper.DBListener() {
            @Override
            public void doNetRequestChange(Object object) {
                if (object==null){
                    Message message = new Message();
                    message.what = 0;
                    myHandler.sendMessage(message);
                }else if (object instanceof List ){
                    Message message = new Message();
                    message.what = 2;
                    message.obj = object;
                    myHandler.sendMessage(message);
                }else if(object instanceof UserMessage){
                    Message message = new Message();
                    message.what = 1;
                    message.obj = object;
                    myHandler.sendMessage(message);
                }
            }
        };
        myDBHelper = new DBHelper(this, dbListener);

        Intent intent = getIntent();
        myUser = (User) intent.getSerializableExtra("myUser");
        userIconOne = (ImageView) findViewById(R.id.user_icon_one);
        userIconTwo = (ImageView)findViewById(R.id.user_icon_two);
        userIconThree = (ImageView)findViewById(R.id.user_icon_three);
        userIconFour = (ImageView)findViewById(R.id.user_icon_four);

        userIconOne.setOnClickListener(this);

        inputText = findViewById(R.id.message_editText);
        sendMessageButton = findViewById(R.id.send_message);
        oneMessageRecycler = findViewById(R.id.room_recyclerView_1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        oneMessageRecycler.setLayoutManager(linearLayoutManager);

        sendMessageButton.setOnClickListener(this);
        initIcon();
        showAllMessage();
    }

    private void initIcon() {
        loadingHeaderIcon(userIconOne);
        loadingHeaderIcon(userIconTwo);
        loadingHeaderIcon(userIconThree);
        loadingHeaderIcon(userIconFour);
        /*
        ObjectAnimator.ofFloat(userIconOne, "X", 200F, 400F, 200F, 400F).setDuration(5000).start();
        ObjectAnimator.ofFloat(userIconOne, "Y", 200F, 400F, 200F, 400F).setDuration(5000).start();
        ObjectAnimator.ofFloat(userIconFour, "X", 400F, 600F, 400F, 600F).setDuration(5000).start();
        ObjectAnimator.ofFloat(userIconFour, "Y", 400F, 600F, 400F, 600F).setDuration(5000).start();
        */
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
                break;

            case R.id.send_message:
                sendMessage();
                break;

        }

    }


    private void sendMessage(){
        String messageContent = inputText.getText().toString();
        if (!"".equals(messageContent)){
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            UserMessage userMessage = new UserMessage(0, messageContent, 1, hour+":"+minute , myUser.getPhoneNum(), "1", myUser.getDormitoryID());
            myDBHelper.okhttpSendUserMessage(userMessage);
            inputText.setText("");
        }
    }

    private void sendMessageSuccess(UserMessage userMessage){
        messageList.add(userMessage);
        //当有新消息时，刷新recyclerview中的显示
        messageOneRecyclerViewAdapter.notifyItemInserted(messageList.size()-1);
        //将消息定位到最后一条
        oneMessageRecycler.scrollToPosition(messageList.size()-1);

    }

    private void showAllMessage(){
        myDBHelper.okhttpQueryAllMessageFromDormitory(myUser.getPhoneNum(), myUser.getDormitoryID());
    }
}
