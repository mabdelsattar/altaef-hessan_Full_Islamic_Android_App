
package com.daralmathour.altaefhessan.PrayerAlarm;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import com.daralmathour.altaefhessan.Activities.StopAthanActivity;

import java.io.IOException;

public class AthanService extends Service implements
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        AudioManager.OnAudioFocusChangeListener
{
    private static final String TAG = "AthanService";


    private MediaPlayer mAudioPlayer = null;
    private BroadcastReceiver mVolumeChangeReceiver = null;
    private boolean mReceiverRegistered = false;

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        stopAthan();           // athan can be played by Alarm or from settings in parallel
        registerVolumeChangeReceiver();
        initMediaPlayer();
        return Service.START_NOT_STICKY;
    }

    private void registerVolumeChangeReceiver() {
        if (mReceiverRegistered) {
            return;
        }
        mVolumeChangeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO: the level checking code doesn't work. It returns -1 for old & new level.
                final int level = intent.getIntExtra("AudioManager.EXTRA_VOLUME_STREAM_VALUE", -1);
                final int oldLevel = intent.getIntExtra("AudioManager.EXTRA_PREV_VOLUME_STREAM_VALUE", -1);
                Log.i(TAG, "VOLUME_CHANGED_ACTION stream=" + " level=" + level + " oldLevel=" + oldLevel);
                if (oldLevel < level) {
                    stopAthan();
                }
            }
        };
        // FIXME: VOLUME_CHANGED_ACTION is internal to the framework and is not part of API. It might change!
        // Here is an alternative with MediaSession & MediaController
        // https://stackoverflow.com/questions/10154118/listen-to-volume-buttons-in-background-service
        registerReceiver(mVolumeChangeReceiver, new IntentFilter("android.media.VOLUME_CHANGED_ACTION"));
        mReceiverRegistered = true;
    }

    private void unregisterVolumeChangeReceiver() {
        if (mReceiverRegistered) {
            unregisterReceiver(mVolumeChangeReceiver);
            mReceiverRegistered = false;
        }
    }

    private void initMediaPlayer() {
        if (null == mAudioPlayer) {
            mAudioPlayer = new MediaPlayer();
            String path = "android.resource://" + getPackageName() + "/" + com.daralmathour.altaefhessan.R.raw.azan_abdel_baset;

            try {
                mAudioPlayer.setDataSource(this, Uri.parse(path));
                mAudioPlayer.setOnPreparedListener(this);
                mAudioPlayer.setOnErrorListener(this);
                mAudioPlayer.setWakeMode(this, PowerManager.PARTIAL_WAKE_LOCK);
                mAudioPlayer.prepareAsync(); // prepare async to not block main thread
                Log.d(TAG, "Audio player prepared asynchronously!");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage(), e);
            }
        }
    }

    /** Called when MediaPlayer is ready */
    public void onPrepared(MediaPlayer player) {
        mAudioPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.i(TAG, "onCompletion");

                Intent stopIntent = new Intent(AthanService.this, AthanService.class);
                stopService(stopIntent);            }
        });
        mAudioPlayer.start();

        Log.i(TAG, "Audio started playing!");
        if(!mAudioPlayer.isPlaying()) {
	        Log.w(TAG, "Problem in playing audio");
	    }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        // TODO ... react appropriately ...
        // The MediaPlayer has moved to the Error state, must be reset!
        Log.e(TAG, "what=" + what + " extra=" + extra);
        return false; // TODO change to true if error is handled by here.
    }

    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                // resume playback
                if (mAudioPlayer == null) initMediaPlayer();
                else if (!mAudioPlayer.isPlaying()) mAudioPlayer.start();
                mAudioPlayer.setVolume(1.0f, 1.0f);
                break;

            case AudioManager.AUDIOFOCUS_LOSS:
                // Lost focus for an unbounded amount of time: stop playback and release media player
                if (mAudioPlayer.isPlaying()) mAudioPlayer.stop();
                mAudioPlayer.release();
                mAudioPlayer = null;
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                // Lost focus for a short time, but we have to stop
                // playback. We don't release the media player because playback
                // is likely to resume
                if (mAudioPlayer.isPlaying()) mAudioPlayer.pause();
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // Lost focus for a short time, but it's ok to keep playing
                // at an attenuated level
                if (mAudioPlayer.isPlaying()) mAudioPlayer.setVolume(0.1f, 0.1f);
                break;
        }
    }

	private void stopAthan() {
		if (mAudioPlayer != null) {
            Log.d(TAG, "Stopping Athan");
            if (mAudioPlayer.isPlaying())
            {
                mAudioPlayer.stop();
            }
            mAudioPlayer = null;
        }
        unregisterVolumeChangeReceiver();
        try {
            this.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }

	public void onPause() {
        if (mAudioPlayer.isPlaying()) { mAudioPlayer.stop(); }
	}

	public void onDestroy() {
        Log.d(TAG, "onDestroy");
        stopAthan();
    }

	@Override
	public IBinder onBind(Intent intent) {
	    return null;
	}
}
