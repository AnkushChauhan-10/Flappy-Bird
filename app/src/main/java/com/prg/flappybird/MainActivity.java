package com.prg.flappybird;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import static com.prg.flappybird.R.mipmap.play;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    int bestScore;
    Sound sound;
    ImageButton muteButton;
    Boolean mute=false;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start_screen);
        SharedPreferences getBestScore=getSharedPreferences("BestScore",MODE_PRIVATE);
        bestScore = getBestScore.getInt("BestScore",0);
        TextView score=(TextView)findViewById(R.id.textView7);
        score.setText(""+bestScore);
        sound=new Sound(this);
        mute=getIntent().getBooleanExtra("Mute",false);
        sound.setVolume(mute);
        muteButton=(ImageButton)findViewById(R.id.imageButton4);
        buttonImage();
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startGame(View view){
        sound.sound(0);
        intent=new Intent(this,Game.class);
        intent.putExtra("Mute",mute);
        startActivity(intent);
        finish();
    }

    public void onBackPressed(){

        finishAffinity();
        System.exit(0);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void volume(View view) {
        if (!mute) {
            mute = true;
        } else {
            mute = false;
        }
        buttonImage();
        sound.setVolume(mute);
    }

    public void buttonImage(){
        if(!mute){
            muteButton.setBackgroundResource(R.mipmap.sound);
        }else{
            muteButton.setBackgroundResource(R.mipmap.mute);
        }
    }



}