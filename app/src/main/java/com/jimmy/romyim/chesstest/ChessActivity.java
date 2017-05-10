package com.jimmy.romyim.chesstest;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import View.GameView;


public class ChessActivity extends AppCompatActivity {


    @Override
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
