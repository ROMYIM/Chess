package View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jimmy.romyim.chesstest.ChessActivity;
import com.jimmy.romyim.chesstest.R;
import com.jimmy.romyim.chesstest.Rule;

/**
 * Created by Administrator on 2017/4/30.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    private DrawThread thread;//刷帧的线程
    private TimeThread timeThread;
    ChessActivity activity;//声明Activity的引用
    Bitmap qiPan;//棋盘
    Bitmap chessBackground;//棋子的背景图片
    Bitmap background;//背景图片
    Bitmap rtSign;
    Bitmap btSign;
    Bitmap versus;
    Bitmap[] blackChess = new Bitmap[7];//黑子的图片数组
    Bitmap[] redChess = new Bitmap[7];//红子的图片数组
    Bitmap[] redNum = new Bitmap[10];
    Bitmap[] blackNum = new Bitmap[10];
    Paint brush;//画笔

    boolean player = true;//是否为玩家走棋
    boolean selected = false;//当前是否有选中的棋子
    int redTime = 0;
    int blackTime = 0;
    int selectedChesspiece = 0; //选中的棋子
    int status = 0;//游戏状态。0游戏中，1胜利, 2失败
    float[][] redTimeLocation = new float[4][2];
    float[][] blTimeLocation = new float[4][2];
    int[] start = new int[2];
    int[] end = new int[2];
    Rule rule;

    int[][] chessBoard = new int[][]{//棋盘
            {2,3,6,5,1,5,6,3,2},
            {0,0,0,0,0,0,0,0,0},
            {0,4,0,0,0,0,0,4,0},
            {7,0,7,0,7,0,7,0,7},
            {0,0,0,0,0,0,0,0,0},

            {0,0,0,0,0,0,0,0,0},
            {14,0,14,0,14,0,14,0,14},
            {0,11,0,0,0,0,0,11,0},
            {0,0,0,0,0,0,0,0,0},
            {9,10,13,12,8,12,13,10,9},
    };

    public GameView(Context context, ChessActivity activity) {//构造器
        super(context);
        this.activity = activity;//得到Activity的引用
        getHolder().addCallback(this);
        this.thread = new DrawThread(getHolder(), this);//初始化刷帧线程
        this.timeThread = new TimeThread();
        this.rule = new Rule();
        init();//初始化所需资源
    }

    public void init(){//初始化方法
        brush = new Paint();//初始化画笔
        qiPan = BitmapFactory.decodeResource(getResources(), R.drawable.qipan);//棋盘图片
        chessBackground = BitmapFactory.decodeResource(getResources(), R.drawable.qizi);//棋子的背景
        background = BitmapFactory.decodeResource(getResources(), R.drawable.newbackground);
        rtSign = BitmapFactory.decodeResource(getResources(), R.drawable.redtime);
        btSign = BitmapFactory.decodeResource(getResources(), R.drawable.time);
        versus = BitmapFactory.decodeResource(getResources(), R.drawable.vs);


        blackChess[0] = BitmapFactory.decodeResource(getResources(), R.drawable.heishuai);//黑帅
        blackChess[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heiju);//黑车
        blackChess[2] = BitmapFactory.decodeResource(getResources(), R.drawable.heima);//黑马
        blackChess[3] = BitmapFactory.decodeResource(getResources(), R.drawable.heipao);//黑炮
        blackChess[4] = BitmapFactory.decodeResource(getResources(), R.drawable.heishi);//黑士
        blackChess[5] = BitmapFactory.decodeResource(getResources(), R.drawable.heixiang);//黑象
        blackChess[6] = BitmapFactory.decodeResource(getResources(), R.drawable.heibing);//黑兵

        redChess[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hongjiang);//红将
        redChess[1] = BitmapFactory.decodeResource(getResources(), R.drawable.hongju);//红车
        redChess[2] = BitmapFactory.decodeResource(getResources(), R.drawable.hongma);//红马
        redChess[3] = BitmapFactory.decodeResource(getResources(), R.drawable.hongpao);//红砲
        redChess[4] = BitmapFactory.decodeResource(getResources(), R.drawable.hongshi);//红仕
        redChess[5] = BitmapFactory.decodeResource(getResources(), R.drawable.hongxiang);//红相
        redChess[6] = BitmapFactory.decodeResource(getResources(), R.drawable.hongzu);//红卒

        blackNum[0] = BitmapFactory.decodeResource(getResources(), R.drawable.number0);//黑色数字0
        blackNum[1] = BitmapFactory.decodeResource(getResources(), R.drawable.number1);//黑色数字1
        blackNum[2] = BitmapFactory.decodeResource(getResources(), R.drawable.number2);//黑色数字2
        blackNum[3] = BitmapFactory.decodeResource(getResources(), R.drawable.number3);//黑色数字3
        blackNum[4] = BitmapFactory.decodeResource(getResources(), R.drawable.number4);//黑色数字4
        blackNum[5] = BitmapFactory.decodeResource(getResources(), R.drawable.number5);//黑色数字5
        blackNum[6] = BitmapFactory.decodeResource(getResources(), R.drawable.number6);//黑色数字6
        blackNum[7] = BitmapFactory.decodeResource(getResources(), R.drawable.number7);//黑色数字7
        blackNum[8] = BitmapFactory.decodeResource(getResources(), R.drawable.number8);//黑色数字8
        blackNum[9] = BitmapFactory.decodeResource(getResources(), R.drawable.number9);//黑色数字9

        redNum[0] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber0);//红色数字0
        redNum[1] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber1);//红色数字1
        redNum[2] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber2);//红色数字2
        redNum[3] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber3);//红色数字3
        redNum[4] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber4);//红色数字4
        redNum[5] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber5);//红色数字5
        redNum[6] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber6);//红色数字6
        redNum[7] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber7);//红色数字7
        redNum[8] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber8);//红色数字8
        redNum[9] = BitmapFactory.decodeResource(getResources(), R.drawable.rednumber9);//红色数字9

        int templength = 0;
        for (int i = 0; i < redTimeLocation.length; i++) {
            if (i == redTimeLocation.length / 2) {
                templength += 35;
                redTimeLocation[i][0] = 732 + templength;
                blTimeLocation[i][0] = 203 + templength;
            }
            else {
                templength += 25;
                redTimeLocation[i][0] = 732 + templength;
                blTimeLocation[i][0] = 203 + templength;
            }
            redTimeLocation[i][1] = 425 + qiPan.getHeight();
            blTimeLocation[i][1] = 425 + qiPan.getHeight();
        }
    }

    private void selectChess(int row, int column) {
        this.selectedChesspiece = this.chessBoard[row][column];
        this.selected = true;
        this.start[0] = row;
        this.start[1] = column;
    }

    private void moveChess(int row, int column) {
        this.end[0] = row;
        this.end[1] = column;
        if (this.rule.canMove(this.selectedChesspiece, this.start, this.end, this.chessBoard)) {
            this.chessBoard[row][column] = this.chessBoard[start[0]][start[1]];
            this.chessBoard[start[0]][start[1]] = 0;
            this.start[0] = this.start[1] = this.end[0] = this.end[1] = -1;
            this.selected = false;
            this.player = !this.player;
        }
    }

    private int[] getPos(double x, double y) {
        int[] pos = new int[2];
        pos[0] = (int)((y - 225) / 105);
        pos[1] = (int)((x - 70) / 102);
        if (pos[1] > 8 || pos[0] > 9 || pos[1] < 0 || pos[0] < 0) {
            return null;
        }else {
            return pos;
        }
    }

    private void drawTime(Canvas canvas, int timeObj, float[][] Location, Bitmap[] num) {
        int minute, seconds;
        minute = timeObj / 60;
        seconds = timeObj % 60;
        String minStr = "";
        String secStr = seconds + "";
        if (minute < 10) {
            minStr = "0" + minute;
        }else {
            minStr = minute + "";
        }
        if (seconds < 10)
            secStr = "0" + secStr;
        int tempScore;
        for (int i = 0; i < 4; i++) {
            if (i < 2) {
                tempScore = minStr.charAt(i) - '0';
            }else {
                tempScore = secStr.charAt(i - 2) - '0';
            }
            canvas.drawBitmap(num[tempScore], Location[i][0], Location[i][1], this.brush);
        }
    }

    public void success(){//胜利了
        status = 1;//切换到胜利状态
    }
    /**
     * 该方法是自己定义的并非重写的

     */
    @Override
    public void onDraw(Canvas canvas){//自己写的绘制方法
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(background, 0, 0, null);//清背景
        canvas.drawBitmap(qiPan, 70, 220, null);//绘制棋盘
        for(int i = 0; i < chessBoard.length; i++){
            for(int j = 0; j<chessBoard[i].length; j++){//绘制棋子
                if(chessBoard[i][j] != 0) {
                    canvas.drawBitmap(chessBackground, 63 + j * 102, 220 + i * 105, null);//绘制棋子的背景
                    switch (chessBoard[i][j]) {
                        case 1:
                            canvas.drawBitmap(this.blackChess[0], 70 + j * 102, 225 + i * 105, this.brush);
                            break;
                        case 2:
                            canvas.drawBitmap(this.blackChess[1], 70 + j * 102, 225 + i * 105, this.brush);
                            break;
                        case 3:
                            canvas.drawBitmap(this.blackChess[2], 70 + j * 102, 225 + i * 105, this.brush);
                            break;
                        case 4:
                            canvas.drawBitmap(this.blackChess[3], 70 + j * 102, 225 + i * 105, this.brush);
                            break;
                        case 5:
                            canvas.drawBitmap(this.blackChess[4], 70 + j * 102, 225 + i * 105, this.brush);
                            break;
                        case 6:
                            canvas.drawBitmap(this.blackChess[5], 70 + j * 102, 225 + i * 105, this.brush);
                            break;
                        case 7:
                            canvas.drawBitmap(this.blackChess[6], 70 + j * 102, 225 + i * 105, this.brush);
                            break;
                        case 8:
                            canvas.drawBitmap(this.redChess[0], 70 + j * 102, 225 + i * 105, this.brush);
                            break;
                        case 9:
                            canvas.drawBitmap(this.redChess[1], 70 + j * 102, 225 + i * 105, this.brush);
                            break;
                        case 10:
                            canvas.drawBitmap(this.redChess[2], 70 + j * 102, 225 + i * 105, this.brush);
                            break;
                        case 11:
                            canvas.drawBitmap(this.redChess[3], 70 + j * 102, 225 + i * 105, this.brush);
                            break;
                        case 12:
                            canvas.drawBitmap(this.redChess[4], 70 + j * 102, 225 + i * 105, this.brush);
                            break;
                        case 13:
                            canvas.drawBitmap(this.redChess[5], 70 + j * 102, 225 + i * 105, this.brush);
                            break;
                        case 14:
                            canvas.drawBitmap(this.redChess[6], 70 + j * 102, 225 + i * 105, this.brush);
                            break;
                    }
                }
            }
        }
        canvas.drawBitmap(versus, 70, qiPan.getHeight() + 270, this.brush);
        canvas.drawBitmap(rtSign, 203 + 75, 420 + qiPan.getHeight(), this.brush);
        canvas.drawBitmap(btSign, 732 + 75, 420 + qiPan.getHeight(), this.brush);
        this.drawTime(canvas, redTime, redTimeLocation, redNum);
        this.drawTime(canvas, blackTime, blTimeLocation, blackNum);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (this.status == 1) {

            }else if (this.status == 2) {

            }else if (this.status == 0) {
                double x = event.getX();
                double y = event.getY();
                if (x > 70 && y > 220 && x < this.qiPan.getWidth() + 70 && y < this.qiPan.getHeight() + 220) {
                    int[] pos = this.getPos(x, y);
                    if (pos != null) {
                        int i = pos[0];
                        int j = pos[1];
                        if (this.player) {
                            if (this.chessBoard[i][j] > 7) {
                                this.selectChess(i, j);
                            }else if ((this.chessBoard[i][j] == 0 || (this.chessBoard[i][j] > 0 && this.chessBoard[i][j] < 8)) && this.selected) {
                                this.moveChess(i, j);
                            }
                        }
                        else {
                            if (this.chessBoard[i][j] > 0 && this.chessBoard[i][j] < 8) {
                                this.selectChess(i, j);
                            }else if ((this.chessBoard[i][j] == 0 || this.chessBoard[i][j] > 7) && this.selected) {
                                this.moveChess(i, j);
                            }
                        }
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {//重写的
        this.thread.setFlag(true);
        this.thread.start();//启动刷帧线程
        this.timeThread.setFlag(true);
        this.timeThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {//view被释放时调用的
        boolean retry = true;
        thread.setFlag(false);//停止刷帧线程
        timeThread.setFlag(false);
        while (retry) {
            try {
                thread.join();
                timeThread.join();
                retry = false;//设置循环标志位为false
            }
            catch (InterruptedException e) {//不断地循环，直到等待的线程结束
            }
        }
    }

    class DrawThread extends Thread {
        private int span = 200;
        private SurfaceHolder surfaceHolder;
        private GameView gameView;
        private boolean flag;

        public DrawThread(SurfaceHolder surfaceHolder, GameView gameView) {
            this.surfaceHolder = surfaceHolder;
            this.gameView = gameView;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        @Override
        public void run() {
            super.run();
            Canvas c;
            while (this.flag) {
                c = null;
                try {
                    c = this.surfaceHolder.lockCanvas(null);
                    synchronized (this.surfaceHolder) {
                        this.gameView.onDraw(c);
                    }
                }finally {
                    if (c != null)
                        this.surfaceHolder.unlockCanvasAndPost(c);
                }
                try {
                    Thread.sleep(this.span);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class TimeThread extends Thread {

        boolean flag;

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        @Override
        public void run() {
            super.run();
            while (this.flag) {
                if (status == 0) {
                    if (player)
                        redTime++;
                    else
                        blackTime++;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
