package com.substarry.me.surfaceviewtraining;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Ling.He on 2016/3/24.
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder mHolder;
    private Thread mThread;
    private boolean run;

    public MySurfaceView(Context context) {
        super(context);
       init();
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mHolder = getHolder();
        mHolder.addCallback(this);
        mThread = new Thread(this);
    }

    //SurfaceHolder.Callback 中三个接口都是在主线程调用，不是在绘制线程
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        setRun(true);
        mThread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        setRun(false);
    }

    private boolean isRun(){
        return this.run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    @Override
    public void run() {

        int count = 0;
        Canvas canvas = null;
        while (isRun()){
            //锁定画布，并获取
            canvas = mHolder.lockCanvas();
            if(canvas!=null){
                //画背景
                canvas.drawColor(Color.WHITE);

                //设置画笔
                Paint paint = new Paint();
                paint.setColor(genColor(count));
                Rect rect = new Rect(200, 200 , 400 , 600);
                //画图
                canvas.drawRect(rect, paint);
                //画文字
                paint.setTextSize(40);
                canvas.drawText("count = " + (count++), 200, 700, paint);
                //解除画布锁定，提交修改
                mHolder.unlockCanvasAndPost(canvas);
            }

            try {
                Thread.sleep(1000);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    private int genColor(int count){
        int index = count % 5;
        switch (index){
            case 0:
                return Color.BLUE;
            case 1:
                return Color.RED;
            case 2:
                return Color.CYAN;
            case 3:
                return Color.YELLOW;
            case 4:
                return Color.GREEN;
            default:
                return Color.BLACK;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
    }
}
