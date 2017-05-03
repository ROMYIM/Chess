package com.jimmy.romyim.chesstest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import View.GameView;


public class ChessActivity extends AppCompatActivity {

//    boolean isSound = true;//是否播放声音
//    MediaPlayer startSound;//开始和菜单时的音乐
//    MediaPlayer gamesound;//游戏声音

//    public Handler myHandler = new Handler(){//用来更新UI线程中的控件
//        public void handleMessage(Message msg) {
//            if(msg.what == 1){	//WelcomeView或HelpView或GameView传来的消息，切换到MenuView
//                initMenuView();//初始化并切换到菜单界面
//            }
//            else if(msg.what == 2){//MenuView传来的消息，切换到GameView
//                initGameView();//初始化并切换到游戏界面
//            }
//            else if(msg.what == 3){//MenuView传来的消息，切换到HelpView
//                initHelpView();//初始化并切换到帮助界面
//            }
//        }
//    };
    public void onCreate(Bundle savedInstanceState) {//重写的onCreate
        super.onCreate(savedInstanceState);
        //全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.initGameView();//初始化欢迎界面
    }


    public void initGameView(){//初始化游戏界面
        this.setContentView(new GameView(this,this)); //切换到游戏界面
    }

}
