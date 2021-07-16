package com.prg.flappybird;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
public class Bird {

    int height,width;
    Bitmap bird;
    int birdY=1000,velocity,gravity=13;
    int birdFrame=0;
    Rect scr,dst;
    int i=0;
    int srcX,frame;
    int circleX,circleY,radius;
    GameView gameView;
    int left,right,top,bottom,c=0;

    public Bird(Bitmap bird,GameView gameView){

        this.bird = bird;
        frame=bird.getWidth()/3;
        this.gameView=gameView;
        height=gameView.getHeight()/10;
        width=gameView.getWidth()/6;

        scr=new Rect(srcX,0,bird.getWidth()/3+srcX,bird.getHeight());
        dst=new Rect(width*2,birdY,width*3,height+birdY);
    }

    public void onDrawBird(Canvas canvas){
        Paint p=new Paint();
        p.setColor(Color.GREEN);
        srcX=birdFrame*frame;

        scr=new Rect(srcX,0,bird.getWidth()/3+srcX,bird.getHeight());
        dst=new Rect(width*2,birdY,width*3,height+birdY);
        //canvas.drawCircle(circleX,circleY,radius,p);
        canvas.drawBitmap(bird,scr,dst,null);

        gravity=13;
    }

    public void update(){
        if(i==0) {
            if (birdY + velocity > 0) {
                birdY += velocity;
                circleY+=velocity;
                velocity += gravity;
            } else {
                birdY += gravity;
                circleY+=gravity;
                velocity = gravity;
            }
        }
        birdFrame=++birdFrame%3;
    }

    public void circle(){

        if(c==0) {
            left = dst.left;
            right = dst.right;
            top = dst.top;
            bottom = dst.bottom;
            circleX=left+(right-left)/2;
            circleY=top+(bottom-top)/4;
            radius=(right-left)/2-30;
            c=1;
        }
    }

    public void IsTouch(){
        velocity=-60;
    }

    public int getGap(){
        return (bottom-top)*2;
    }


}
