package com.example.payam1991.msmusic.models.services;

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.payam1991.msmusic.models.classes.Song;
import com.example.payam1991.msmusic.models.enums.MediaController;

import java.util.List;
import java.util.Random;

/**
 * @author Payam1991 on 8/29/2017.
 */

public class MusicService extends Service implements
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {

    public final static String CONTROLLER_INTENT = "CONTROLLER_INTENT";
    public final static String CONTROLLER_ACTION = "CONTROLLER_ACTION";
    public final static String CONTROLLER_SONG = "CONTROLLER_SONG";
    public final static String CONTROLLER_CURRENT_TIME = "CONTROLLER_CURRENT_TIME";


    Intent intent;

    private MediaPlayer player;
    private List<Song> songs;

    //current position
    private int songPosn;

    private final IBinder musicBind = new MusicBinder();
    private boolean shuffle = false;
    private Random rand;

    public void onCreate() {
        super.onCreate();
        intent = new Intent(CONTROLLER_INTENT);
        songPosn = 0;
        rand = new Random();
        player = new MediaPlayer();
        initMusicPlayer();
    }

    public void initMusicPlayer() {
        player.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
    }

    public void setList(List<Song> theSongs) {
        songs = theSongs;
    }

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return musicBind;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (!player.isPlaying()) {
            player.stop();
            player.release();
            return false;
        }
        return true;
    }

    public void playSong() {

        player.reset();
        Song playSong = songs.get(songPosn);
        long currSong = playSong.getId();
        Uri trackUri = ContentUris.withAppendedId(
                android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                currSong);
        //set the data source
        try {
            player.setDataSource(getApplicationContext(), trackUri);
            sendBroadcast(MediaController.PLAY, playSong, player.getCurrentPosition());
        } catch (Exception e) {
            Log.e("MUSIC SERVICE", "Error setting data source", e);
        }
        player.prepareAsync();
    }


    public void setSong(int songIndex) {
        songPosn = songIndex;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (player.getCurrentPosition() > 0) {
            mp.reset();
            playNext();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.v("MUSIC PLAYER", "Playback Error");
        mp.reset();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();


//        //notification
//        Intent notIntent = new Intent(this, MainActivity.class);
//        notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendInt = PendingIntent.getActivity(this, 0,
//                notIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Notification.Builder builder = new Notification.Builder(this);

//        builder.setContentIntent(pendInt)
//                .setSmallIcon(R.drawable.ic_search_white_48dp)
//                .setTicker(songTitle)
//                .setOngoing(true)
//                .setContentTitle("Playing")
//                .setContentText(songTitle);
//        Notification not = builder.build();
//        startForeground(NOTIFY_ID, not);
    }

    public int getPosn() {
        return player.getCurrentPosition();
    }

    public int getDur() {
        return player.getDuration();
    }

    public boolean isPng() {
        return player.isPlaying();
    }

    public void pausePlayer() {
        player.pause();
        Song playSong = songs.get(songPosn);
        sendBroadcast(MediaController.PAUSE, playSong, player.getCurrentPosition());
    }

    public void seek(int posn) {
        player.seekTo(posn);
    }

    public void go() {
        player.start();
    }

    public void playPrev() {
        songPosn--;
        if (songPosn < 0) songPosn = songs.size() - 1;
        playSong();

    }

    public void playNext() {
        if (shuffle) {
            int newSong = songPosn;
            while (newSong == songPosn) {
                newSong = rand.nextInt(songs.size());
            }
            songPosn = newSong;
        } else {
            songPosn++;
            if (songPosn >= songs.size()) songPosn = 0;
        }
        playSong();
    }

    public Song getCurrentSong() {
        return songs.get(songPosn);
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
    }

    public void setShuffle() {
        if (shuffle) shuffle = false;
        else shuffle = true;
    }


    private void sendBroadcast(MediaController controller, Song object, long cur) {
        intent.putExtra(CONTROLLER_ACTION, controller.ordinal());
        intent.putExtra(CONTROLLER_SONG, object);
        intent.putExtra(CONTROLLER_CURRENT_TIME, cur);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public MediaPlayer getPlayer() {
        return player;
    }
}