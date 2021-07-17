package com.prg.flappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class End extends AppCompatActivity {

    int bestScore;
    TextView scoreView,bestScoreView;
    SharedPreferences saveBestScore;
    Sound sound;
    Boolean mute=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("GAME OVER");
        setContentView(R.layout.activity_end);
        mute=getIntent().getBooleanExtra("Mute",false);
        sound=new Sound(this);
        sound.setVolume(mute);

        saveBestScore=getSharedPreferences("BestScore",MODE_PRIVATE);

        int score=getIntent().getIntExtra("score",0);
        bestScore(score);
        scoreView=(TextView)findViewById(R.id.textView3);
        bestScoreView=(TextView)findViewById(R.id.textView5);
        scoreView.setText(""+score);
        bestScoreView.setText(""+bestScore);
    }

    public void HomeFun(View v){
        sound.sound(0);
        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("Mute",mute);
        startActivity(intent);
        finish();
    }

    public void PlayAgain(View v){
        sound.sound(0);
        Intent intent=new Intent(this,Game.class);
        intent.putExtra("Mute",mute);
        startActivity(intent);
        finish();
    }
    public void bestScore(int score){
        SharedPreferences getBestScore=getSharedPreferences("BestScore",MODE_PRIVATE);
        bestScore = getBestScore.getInt("BestScore",0);
        if(score>bestScore){
            bestScore=score;
            SharedPreferences.Editor editor=saveBestScore.edit();
            editor.putInt("BestScore", bestScore);
            editor.apply();
        }
    }

    public void onBackPressed(){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}