package com.prg.flappybird;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

    SurfaceHolder holder;
    GameView gameView;
    boolean running;
    Canvas canvas;
    long startTime,loopTime;
    long delay=60;


    public MainThread(SurfaceHolder holder,GameView gameView){

        super();

        this.holder=holder;
        this.gameView=gameView;

    }

    public void startLoop() {
        running = true;
        start();
    }

    public void run(){
        while(running){
            startTime= SystemClock.uptimeMillis();
            canvas=null;
            try{
                canvas=this.holder.lockCanvas();
                synchronized (holder){
                    this.gameView.update();
                    this.gameView.onDraw(canvas);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            finally {
                if (canvas != null) {
                    try {
                        holder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            loopTime=SystemClock.uptimeMillis()-startTime;
            if(loopTime<delay){

                try{
                    Thread.sleep(delay-loopTime);
                }catch (InterruptedException e){
                    Log.e("Interrupted","While Sleep");
                }

            }

        }
    }
    public void setRunning(boolean isRunning){
        running=isRunning;
    }
}
