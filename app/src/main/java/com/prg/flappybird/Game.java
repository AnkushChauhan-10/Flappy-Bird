package com.prg.flappybird;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class Game extends AppCompatActivity {

    GameView gameView;
    FrameLayout game;
    RelativeLayout button;
    ImageButton pauseButton;
    Dialog dialog;
    Sound music;
    Boolean pause=false,resume=false,stop=false,pauseTap=false,resumeDialog=false,mute=false;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        gameView=new GameView(this);
        game=new FrameLayout(this);
        button=new RelativeLayout(this);
        mute=getIntent().getBooleanExtra("Mute",false);

        pauseButton = new ImageButton(this);
        pauseButton.setBackgroundResource(R.mipmap.pause);
        pauseButton.setId(12345);
        pauseButton.setSoundEffectsEnabled(true);
        music=new Sound(this);
        music.setVolume(mute);


        RelativeLayout.LayoutParams pb=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        button.setLayoutParams(params);
        button.addView(pauseButton);
        pb.height=200;
        pb.width=150;
        pb.topMargin=100;
        pb.leftMargin=50;

        pb.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
        pauseButton.setLayoutParams(pb);


        game.addView(gameView);
        game.addView(button);


        setContentView(game);
        pauseButton.setOnClickListener(this::onClick);

        gameView.setMute(mute);
    }
    public void onClick(View v){
        music.sound(0);
        pauseTap=true;
        onPause();
        resumeButton();
    }

    public void resumeButton(){
        if(!resumeDialog){
            resumeDialog=true;
            dialog=new Dialog(this);
            dialog.setCancelable(false);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            dialog.setContentView(R.layout.activity_game);
            ImageButton dialogButton=(ImageButton) dialog.findViewById(R.id.imageButton2);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    music.sound(0);
                    gameView.resume();
                    dialog.dismiss();
                    resumeDialog=false;
                    pauseTap=false;
                }

            });
            dialog.show();
        }
    }


    @Override
    protected void onStart() {
        Log.d("Game.java", "onStart()");
        super.onStart();
    }

    @Override
    protected void onResume() {

        resume=true;
        Log.d("Game.java", "onResume()");
        if(pause && !pauseTap){
          resumeButton();
        }
        super.onResume();
        pause=false;
    }

    @Override
    protected void onPause() {

        pause=true;
        gameView.pause();
        Log.d("Game.java", "onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("Game.java", "onStop()");
        super.onStop();
        stop=true;
    }

    @Override
    protected void onDestroy() {
        Log.d("Game.java", "onDestroy()");
        super.onDestroy();
    }


    public void onBackPressed(){
        gameView.pause();
        resumeButton();
    }
}