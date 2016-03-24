package com.substarry.me.surfaceviewtraining;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

/**
 * Created by Ling.He on 2016/3/24.
 */
public class MySurfaceThread extends Thread {

    private SurfaceHolder mHolder;
    private boolean run;

    public MySurfaceThread(SurfaceHolder mHolder) {
        this.mHolder = mHolder;
        run = true;
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
                canvas.drawText("count = " + (++count), 200, 700, paint);
            }

            try {
                Thread.sleep(1000);
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                if(canvas != null){
                    //解除画布锁定，提交修改
                    mHolder.unlockCanvasAndPost(canvas);
                }
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
}

