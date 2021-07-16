package com.prg.flappybird;

import android.content.Context;
import android.media.MediaPlayer;

import static com.prg.flappybird.R.*;

public class Sound {

    MediaPlayer upSound,loosingSound,scoreSound,buttonSound;
    Context context;
    boolean mute=false;

   public Sound(Context context){

        this.context=context;
       upSound=MediaPlayer.create(context, R.raw.up_sound);
       loosingSound=MediaPlayer.create(context, raw.lossing);
       scoreSound=MediaPlayer.create(context, raw.touchsound);
       buttonSound=MediaPlayer.create(context, raw.playsound);
   }

    public void sound(int i){
       if(!mute) {
           if (i == 0) {
               buttonSound.start();
           } else if (i == 1) {
               upSound.start();
           } else if (i == 2) {
               scoreSound.start();
           } else if (i == 3) {
               loosingSound.start();
           }
       }
   }

   public void setVolume(Boolean volume){
       mute=volume;
   }

}
