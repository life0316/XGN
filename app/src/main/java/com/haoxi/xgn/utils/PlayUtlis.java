package com.haoxi.xgn.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import java.io.IOException;

public class PlayUtlis {
    private static AssetManager assetManager;
    private static MediaPlayer mMediaPlayer;

    public static MediaPlayer playRing(Context context) {
        mMediaPlayer = null;
        try {
            mMediaPlayer = new MediaPlayer();
            assetManager = context.getAssets();
            AssetFileDescriptor fileDescriptor = assetManager.openFd("takephone.mp3");
            mMediaPlayer.setLooping(true);
            try {
                mMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(),
                        fileDescriptor.getLength());
            } catch (IOException e) {
                e.printStackTrace();
            }
            mMediaPlayer.prepare();
            mMediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return mMediaPlayer;
    }

    public static void cancle() {
        if(mMediaPlayer !=null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
