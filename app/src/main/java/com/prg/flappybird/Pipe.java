package com.prg.flappybird;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Random;

public class Pipe {

    int gapBetweenPipe ;
    Bitmap TopPipe,BottomPipe;
    GameView gameView;
    Random rand = new Random();
    int TopTubeBottomY,BottomTubeY;
    int x,xSpeed;
    Rect Tscr,Tdst,Bscr,Bdst;
    int height,width;




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Pipe(Bitmap pipe1, Bitmap pipe2, GameView gameView,int x){

        TopPipe=pipe2;
        BottomPipe=pipe1;
        this.x=x;
        this.gameView=gameView;
        height=gameView.getHeight()/10*5;
        width=gameView.getWidth()/6;
        TopTubeBottomY=rand.nextInt(height-gameView.getHeight()/10*2)+gameView.getHeight()/10*2;
    }

    public void onDrawPipe(Canvas canvas){

        gapBetweenPipe=gameView.getGap();
        BottomTubeY=TopTubeBottomY+gapBetweenPipe;

        Tscr=new Rect(0,0,TopPipe.getWidth(),TopPipe.getHeight());
        Tdst=new Rect(x,0,x+width,TopTubeBottomY);

        Bscr=new Rect(0,0,TopPipe.getWidth(),TopPipe.getHeight());
        Bdst=new Rect(x,BottomTubeY,x+width,gameView.getBottom());

        canvas.drawBitmap(TopPipe,Tscr,Tdst,null);
        canvas.drawBitmap(BottomPipe,Bscr,Bdst,null);

    }

    public void upDate(){
        x-=xSpeed;
    }

    public void setTopTubeY(int TopTubeY){
        this.TopTubeBottomY=TopTubeY;
    }

    public void setxSpeed(int speed){
        xSpeed=speed;
    }

    public void setX(int x){
        this.x=x;
    }
}
