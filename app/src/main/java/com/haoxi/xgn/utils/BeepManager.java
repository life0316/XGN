package com.haoxi.xgn.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import java.io.IOException;

public class BeepManager {

    private static final float BEEP_VOLUME = 0.10f;
    private static final long VIBRATE_DURATION = 2000L;

    private static MediaPlayer mediaPlayer;
    private static AssetFileDescriptor file;
    private static Vibrator vibrator;
    private static AssetManager assetManager;

    public static void playBeepSoundAndVibrate(Activity activity){
        init(activity);
//        vibrator.vibrate(VIBRATE_DURATION);
        vibrator.vibrate(new long[]{100,10,100,1000}, 0);
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public static void init(Activity activity){
        vibrator = (Vibrator) activity
                .getSystemService(Context.VIBRATOR_SERVICE);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setLooping(true);
//        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                mp.release();
//                mediaPlayer = null;
//            }});
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener(){
            @Override
            public boolean onError(MediaPlayer mp, int arg1, int arg2) {
                mp.release();
                mediaPlayer = null;
                return true;
            }});
        assetManager = activity.getAssets();
        AssetFileDescriptor fileDescriptor = null;
        try {
            fileDescriptor = assetManager.openFd("takephone.mp3");
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(),
                    fileDescriptor.getLength());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cancle(){
        if (mediaPlayer != null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (vibrator != null){
            vibrator.cancel();
        }
    }
}
