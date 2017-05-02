package view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jimmy.romyim.chess.MainActivity;
import com.jimmy.romyim.chess.R;
import com.jimmy.romyim.chess.Rule;

/**
 * Created by Administrator on 2017/4/27.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread drawThread;
    MainActivity activity;
    Bitmap qiPan;
    Bitmap chessBackground;
    Bitmap background;
    Bitmap[] blackChess = new Bitmap[7];
    Bitmap[] redChess = new Bitmap[7];
    Paint brush;

    boolean selected = false;
    boolean player = true;
    int[][] chessBoard = new int[][]{
            {2,3,6,5,1,5,6,3,2},
            {0,0,0,0,0,0,0,0,0},
            {0,4,0,0,0,0,0,4,0},
            {7,0,7,0,7,0,7,0,7},
            {0,0,0,0,0,0,0,0,0},

            {0,0,0,0,0,0,0,0,0},
            {14,0,14,0,14,0,14,0},
            {0,11,0,0,0,0,0,11,0},
            {0,0,0,0,0,0,0,0,0},
            {9,10,13,12,8,12,13,10,9},
    };
    int status = 0;
    int selectedChesspiece = 0;
    int[] start = new int[2];
    int[] end = new int[2];
    Rule rule;

    public GameView(Context context, MainActivity activity) {
        super(context);
        this.activity = activity;
        this.getHolder().addCallback(this);
        this.drawThread = new DrawThread(this.getHolder(), this);
        init();
        this.rule = new Rule();
    }

    public void init() {
        this.brush = new Paint();
        this.qiPan = BitmapFactory.decodeResource(getResources(), R.drawable.qipan);
        this.chessBackground = BitmapFactory.decodeResource(getResources(), R.drawable.qizi);
        this.background = BitmapFactory.decodeResource(getResources(), R.drawable.bacnground);

        this.blackChess[0] = BitmapFactory.decodeResource(getResources(), R.drawable.heishuai);
        this.blackChess[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heiju);
        this.blackChess[2] = BitmapFactory.decodeResource(getResources(), R.drawable.heima);
        this.blackChess[3] = BitmapFactory.decodeResource(getResources(), R.drawable.heipao);
        this.blackChess[4] = BitmapFactory.decodeResource(getResources(), R.drawable.heishi);
        this.blackChess[5] = BitmapFactory.decodeResource(getResources(), R.drawable.heixiang);
        this.blackChess[6] = BitmapFactory.decodeResource(getResources(), R.drawable.heibing);

        this.redChess[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hongjiang);
        this.redChess[1] = BitmapFactory.decodeResource(getResources(), R.drawable.hongju);
        this.redChess[2] = BitmapFactory.decodeResource(getResources(), R.drawable.hongma);
        this.redChess[3] = BitmapFactory.decodeResource(getResources(), R.drawable.hongpao);
        this.redChess[4] = BitmapFactory.decodeResource(getResources(), R.drawable.hongshi);
        this.redChess[5] = BitmapFactory.decodeResource(getResources(), R.drawable.hongxiang);
        this.redChess[6] = BitmapFactory.decodeResource(getResources(), R.drawable.hongzu);
    }

    private int[] getPos(MotionEvent event) {
        int[] pos = new int[2];
        double x = event.getX();
        double y = event.getY();
        if (x > 10 && y > 10 && x < this.qiPan.getWidth() + 10 && y < this.qiPan.getHeight() + 10) {
            pos[0] = Math.round((float)((y - 21) / 36));
            pos[1] = Math.round((float)((x - 21) / 35));
        }else {
            pos[0] = -1;
            pos[1] = -1;
        }
        return pos;
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

    public void myDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(this.background, 0, 0, null);
        canvas.drawBitmap(this.qiPan, 10, 10, null);
        for (int i = 0; i < this.chessBoard.length; i++) {
            for (int j = 0; j < this.chessBoard[i].length; j++) {
                if (this.chessBoard[i][j] != 0) {
                    canvas.drawBitmap(this.chessBackground, 9+j*34, 10+i*35, null);

                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(this.background, 0, 0, null);
        canvas.drawBitmap(this.qiPan, 10, 10, null);
        for (int i = 0; i < this.chessBoard.length; i++) {
            for (int j = 0; j < this.chessBoard[i].length; j++) {
                if (this.chessBoard[i][j] != 0) {
                    canvas.drawBitmap(this.chessBackground, 9+j*34, 10+i*35, null);
                    switch (chessBoard[i][j]) {
                        case 1:
                            canvas.drawBitmap(this.blackChess[0], 12+j*34, 13+i*35, this.brush);
                            break;
                        case 2:
                            canvas.drawBitmap(this.blackChess[1], 12+j*34, 13+i*35, this.brush);
                            break;
                        case 3:
                            canvas.drawBitmap(this.blackChess[2], 12+j*34, 13+i*35, this.brush);
                            break;
                        case 4:
                            canvas.drawBitmap(this.blackChess[3], 12+j*34, 13+i*35, this.brush);
                            break;
                        case 5:
                            canvas.drawBitmap(this.blackChess[4], 12+j*34, 13+i*35, this.brush);
                            break;
                        case 6:
                            canvas.drawBitmap(this.blackChess[5], 12+j*34, 13+i*35, this.brush);
                            break;
                        case 7:
                            canvas.drawBitmap(this.blackChess[6], 12+j*34, 13+i*35, this.brush);
                            break;
                        case 8:
                            canvas.drawBitmap(this.redChess[0], 12+j*34, 13+i*35, this.brush);
                            break;
                        case 9:
                            canvas.drawBitmap(this.redChess[1], 12+j*34, 13+i*35, this.brush);
                            break;
                        case 10:
                            canvas.drawBitmap(this.redChess[2], 12+j*34, 13+i*35, this.brush);
                            break;
                        case 11:
                            canvas.drawBitmap(this.redChess[3], 12+j*34, 13+i*35, this.brush);
                            break;
                        case 12:
                            canvas.drawBitmap(this.redChess[4], 12+j*34, 13+i*35, this.brush);
                            break;
                        case 13:
                            canvas.drawBitmap(this.redChess[5], 12+j*34, 13+i*35, this.brush);
                            break;
                        case 14:
                            canvas.drawBitmap(this.redChess[6], 12+j*34, 13+i*35, this.brush);
                            break;
                    }
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (this.status == 1) {

            }else if (this.status == 2) {

            }else if (this.status == 0) {
                if (event.getX() > 10 && event.getX() < 310 && event.getY() > 10 && event.getY() < 360) {
                    int[] pos = this.getPos(event);
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
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.drawThread.setFlag(true);
        this.drawThread.run();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        this.drawThread.setFlag(false);
        while (retry) {
            try {
                this.drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class DrawThread extends Thread {
        private int span = 300;
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
                        this.gameView.myDraw(c);
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


}
