package com.prg.flappybird;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.shapes.OvalShape;
import android.media.MediaPlayer;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

import androidx.annotation.RequiresApi;

import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder holder;
    MainThread thread=null;
    Bitmap backgroundImg,rain_bg,leaf_bg,leafHD_bg,itachi_bg,dbz_bg,birdImg,pipeImg1,pipeImg2,tile;
    Background background;
    Bird bird;
    Pipe pipe1,pipe2;
    int gameSpeed,distanceBetweenPipe;
    Random rand = new Random();
    int score=0;
    Paint p;
    Game game;
    Context gameContext;
    Boolean gameOver=false,isTap=false,mute=false;
    int tileX=0,tileY=1000;
    int testX,testY;
    Sound sound;
    private boolean splash=false;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public GameView(Context context) {
        super(context);

        gameContext=context;
        gameContext=getContext();

        holder=getHolder();
        holder.addCallback(this);

        backgroundImg= BitmapFactory.decodeResource(getResources(),R.drawable.bg1);
        rain_bg=BitmapFactory.decodeResource(getResources(),R.drawable.rain_bg);
        leaf_bg=BitmapFactory.decodeResource(getResources(),R.drawable.leaf_bg);
        leafHD_bg=BitmapFactory.decodeResource(getResources(),R.drawable.leaf_hdbg);
        itachi_bg=BitmapFactory.decodeResource(getResources(),R.drawable.itachi_bg);
        dbz_bg=BitmapFactory.decodeResource(getResources(),R.drawable.dbz);

        birdImg= BitmapFactory.decodeResource(getResources(),R.drawable.dragon);


        tile=BitmapFactory.decodeResource(getResources(),R.drawable.tile);

        pipeImg1=BitmapFactory.decodeResource(getResources(),R.drawable.pipe1);
        pipeImg2=BitmapFactory.decodeResource(getResources(),R.drawable.pipe2);
        thread=new MainThread(holder,this);

        p=new Paint();
        p.setTextSize(150);
        p.setColor(Color.BLACK);
        p.setFakeBoldText(true);
        p.setStyle(Paint.Style.FILL);
        p.setStrokeWidth(10);

        background=new Background(backgroundImg,leaf_bg,leafHD_bg,rain_bg,itachi_bg,dbz_bg,this);
        sound=new Sound(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        distanceBetweenPipe=this.getWidth()/2+pipeImg2.getWidth()/2 +20;
        pipe1=new Pipe(pipeImg1,pipeImg2,this,(this.getWidth()));
        pipe2=new Pipe(pipeImg1,pipeImg2,this,(distanceBetweenPipe+this.getWidth()));
        bird=new Bird(birdImg,this);
        gameSpeed=(this.getWidth()*3)/100;

        thread.startLoop();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        thread.interrupt();

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void onDraw(Canvas canvas){
            if(canvas!=null){
              if(gameOver && bird.dst.bottom-5>=tileY){
                  holder.unlockCanvasAndPost(canvas);
                  End();
              }
              else {

                  background.drawBackground(canvas);

                  pipe1.onDrawPipe(canvas);
                  pipe2.onDrawPipe(canvas);

                  bird.onDrawBird(canvas);

                  if(pipe1.Tdst.right < 0) {
                      pipe1.setX(this.getWidth());
                      pipe1.setTopTubeY(rand.nextInt((this.getHeight()/10*5)-this.getHeight()/10*2)+this.getHeight()/10*2);
                  }
                  if(pipe2.Tdst.right < 0) {
                      pipe2.setX(this.getWidth());
                      pipe2.setTopTubeY(rand.nextInt((this.getHeight()/10*5)-this.getHeight()/10*2)+this.getHeight()/10*2);
                  }
                  canvas.drawText(""+score,this.getWidth()/2-75,300,p);

                  tile(canvas);
                  if(gameOver && !splash) {
                      splash=true;
                      canvas.drawColor(Color.WHITE);
                  }

              }
            }
    }

    public void update(){
        sound.setVolume(mute);
        if(isTap) {
            bird.update();
            bird.circle();
            pipe1.setxSpeed(gameSpeed);
            CollisionAndScore(pipe1);
            CollisionAndScore(pipe2);
            pipe2.setxSpeed(gameSpeed);
            pipe1.upDate();
            pipe2.upDate();
        }
        else {
            bird.birdFrame=++bird.birdFrame%2;
        }

    }

    public boolean onTouchEvent(MotionEvent event){
        if(!gameOver) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                isTap = true;
                bird.IsTouch();
                sound.sound(1);
                return true;
            }
        }
        return false;
    }



    public void pause() {
        thread.setRunning(false);
    }

    public void resume() {
        thread = new MainThread(holder,this);
        thread.startLoop();
        thread.setRunning(true);
    }


    public void CollisionAndScore(Pipe pipe){

        testY=0;
        testX=0;

        //Top Pipe Collision
        if(bird.circleY+bird.radius<pipe.Tdst.bottom && bird.left<=pipe.Tdst.left) {
            testX=pipe.Tdst.left;
            testY=bird.circleY;
            int distance= (int) Math.sqrt(((bird.circleX-testX)*(bird.circleX-testX))+((bird.circleY-testY)*(bird.circleY-testY)));
            if(distance<=bird.radius){
                bird.velocity=15;
                gameSpeed=0;
                gameOver=true;
            }
        }
        if(bird.right>pipe.Tdst.left && bird.left<pipe.Tdst.right && bird.circleY+bird.radius>pipe.Tdst.bottom){
            testX=bird.circleX;
            testY=pipe.Tdst.bottom;
            int distance= (int) Math.sqrt(((bird.circleX-testX)*(bird.circleX-testX))+((bird.circleY-testY)*(bird.circleY-testY)));
            if(distance<=bird.radius){
                bird.velocity=15;
                gameSpeed=0;
                gameOver=true;
            }
        }

        //Bottom Pipe Collision
        if(bird.circleY-bird.radius>pipe.Bdst.top) {
            testX=pipe.Bdst.left;
            testY=bird.circleY;
            int BottomDistance= (int) Math.sqrt(((bird.circleX-testX)*(bird.circleX-testX))+((bird.circleY-testY)*(bird.circleY-testY)));
            if(BottomDistance<=bird.radius){
                gameSpeed=0;
                gameOver=true;
            }
        }
        if(bird.right>pipe.Bdst.left && bird.left<pipe.Bdst.right && bird.circleY-bird.radius<pipe.Bdst.top){
            testX=bird.circleX;
            testY=pipe.Bdst.top;
            int BottomDistance= (int) Math.sqrt(((bird.circleX-testX)*(bird.circleX-testX))+((bird.circleY-testY)*(bird.circleY-testY)));
            if(BottomDistance<=bird.radius){
                gameSpeed=0;
                gameOver=true;
            }
        }
      if(bird.birdY>tileY){
          gameOver=true;
      }

      //Score Update
      if(bird.circleX>pipe.Tdst.left && bird.circleX<=pipe.Tdst.left+25 && !gameOver){
          score++;
          sound.sound(2);
      }

      if(gameOver){
          sound.sound(3);
      }

    }

    public void End(){
        Intent intent=new Intent(gameContext,End.class);
        intent.putExtra("score",score);
        intent.putExtra("Mute",mute);
        gameContext.startActivity(intent);
        game.finish();
    }


    public void tile(Canvas canvas){

        tileY=this.getHeight()/10*9;

        tileX -= gameSpeed;
        if(tileX<-this.getWidth()+gameSpeed){
            tileX=0;
        }

        canvas.drawBitmap(tile,tileX,tileY,null);
        if(tileX<0){
            canvas.drawBitmap(tile,this.getWidth()+tileX,tileY,null);
        }

    }

    public int getGap(){
        return bird.getGap();
    }

    public void setMute(Boolean mute){
        this.mute=mute;
    }

}
