package com.prg.flappybird;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Background {

    GameView gameView;
    Bitmap bg;

    public Background(Bitmap bg,Bitmap bg1,Bitmap bg2,Bitmap bg3,Bitmap bg4,Bitmap bg5,GameView gameView){
        int i=(int)(Math.random()*10)%6;

        if(i==0)
            this.bg=bg;
        else if(i==1){
            this.bg=bg1;
        }
        else if(i==2){
            this.bg=bg2;
        }
        else if(i==3){
            this.bg=bg3;
        }else if(i==4){
            this.bg=bg4;
        }else{
            this.bg=bg5;
        }
        this.bg=bg;
        this.gameView=gameView;
    }

    public void drawBackground(Canvas canvas){

        Rect src= new Rect(0,0,bg.getWidth(),bg.getHeight());
        Rect dst= new Rect(0,0,gameView.getRight(),gameView.getBottom());

        canvas.drawBitmap(bg,src,dst,null);
    }

}
