package uabc.iec.examen1;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Random;

public class BackgroundMusicService extends Service {

    MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Random rand = new Random();
        Log.d("mylog","Starting music");
        switch (rand.nextInt(4 - 1) + 1){
            case 1:
                playMusicA();
                break;
            case 2:
                playMusicB();
                break;
            case 3:
                playMusicC();
                break;
            case 4:
                playMusicD();
                break;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public void playMusicA(){
        mediaPlayer = MediaPlayer.create(this,R.raw.eleven_pm_accf);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
                playMusicB();
            }
        });
    }
    public void playMusicB(){
        mediaPlayer = MediaPlayer.create(this,R.raw.four_am_acnl);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
                playMusicC();
            }
        });
    }
    public void playMusicC(){
        mediaPlayer = MediaPlayer.create(this,R.raw.ten_pm_accf);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
                playMusicD();
            }
        });
    }
    public void playMusicD(){
        mediaPlayer = MediaPlayer.create(this,R.raw.eleven_pm_acnh);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.release();
                playMusicA();
            }
        });
    }

    @Override
    public boolean stopService(Intent name){
        return super.stopService(name);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

}
