package com.example.payam1991.msmusic.view.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.payam1991.msmusic.R;
import com.example.payam1991.msmusic.models.SongUtil;
import com.example.payam1991.msmusic.models.classes.Song;
import com.example.payam1991.msmusic.models.services.MusicService;
import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;

public class SongActivity extends AppCompatActivity {

    TextView tvSongName;
    TextView tvArtist;
    HoloCircleSeekBar circleSeekBar;
    TextView tvTime;
    ImageView ivPlay;
    ImageView ivRep;
    ImageView ivBackground;
    ImageView ivForward;
    ImageView ivBackward;


    MusicService musicService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        musicService = MainActivity.musicSrv;
        initViews();

        ivBackground.setImageBitmap(SongUtil.fastblur(SongUtil.musicAlbumCover((int) musicService.getCurrentSong().getAlbumId(), getApplicationContext()), 1, 15));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMessageReceiver,
                new IntentFilter(MusicService.CONTROLLER_INTENT));
    }

    void initViews() {
        tvSongName = (TextView) findViewById(R.id.tv_song_name);
        tvArtist = (TextView) findViewById(R.id.tv_singer);
        tvTime = (TextView) findViewById(R.id.tv_time);

        circleSeekBar = (HoloCircleSeekBar) findViewById(R.id.seekbar);
        ivBackground = (ImageView) findViewById(R.id.iv_background);
        ivPlay = (ImageView) findViewById(R.id.iv_play);
        ivRep = (ImageView) findViewById(R.id.iv_repeat);
        ivBackward = (ImageView) findViewById(R.id.iv_backward);
        ivForward = (ImageView) findViewById(R.id.iv_forward);

        ivBackward.setOnClickListener(ivBackCickListener());
        ivForward.setOnClickListener(ivForwardCickListener());
        ivPlay.setOnClickListener(ivPlayCickListener());
        ivRep.setOnClickListener(ivRepeatCickListener());

        tvArtist.setText(musicService.getCurrentSong().getArtist());
        tvSongName.setText(musicService.getCurrentSong().getTitle());


        circleSeekBar.setMax(100);
        circleSeekBar.postDelayed(onEverySecond, 1000);

        circleSeekBar.setOnSeekBarChangeListener(new HoloCircleSeekBar.OnCircleSeekBarChangeListener() {
            @Override
            public void onProgressChanged(HoloCircleSeekBar seekBar, int progress, boolean fromUser) {
                musicService.seek((progress * musicService.getPlayer().getDuration()) / 100);
            }

            @Override
            public void onStartTrackingTouch(HoloCircleSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(HoloCircleSeekBar seekBar) {

            }
        });

        // seekController(musicService.getPosn(),musicService.getDur(),0);
    }


    private View.OnClickListener ivForwardCickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicService.playNext();
                ivBackground.setImageBitmap(SongUtil.fastblur(SongUtil.musicAlbumCover((int) musicService.getCurrentSong().getAlbumId(), getApplicationContext()), 1, 15));
                circleSeekBar.postDelayed(onEverySecond, 1000);

            }
        };
    }

    private View.OnClickListener ivBackCickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicService.playPrev();
                ivBackground.setImageBitmap(SongUtil.fastblur(SongUtil.musicAlbumCover((int) musicService.getCurrentSong().getAlbumId(), getApplicationContext()), 1, 15));
                circleSeekBar.postDelayed(onEverySecond, 1000);

            }
        };
    }

    private View.OnClickListener ivPlayCickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playBtnDrawable();

            }
        };
    }

    private View.OnClickListener ivRepeatCickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (musicService.isPng()) {
                    musicService.playSong();
                    circleSeekBar.postDelayed(onEverySecond, 1000);
                } else {
                }

            }
        };
    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Song song = (Song) intent.getSerializableExtra(MusicService.CONTROLLER_SONG);
            tvSongName.setText(song.getTitle());
            tvArtist.setText(song.getArtist());

        }
    };

    private Runnable onEverySecond = new Runnable() {

        @Override
        public void run() {
            try{
                if (circleSeekBar != null) {
                    circleSeekBar.setValue((100 * musicService.getPlayer().getCurrentPosition() / musicService.getPlayer().getDuration()));
                    int minutes = musicService.getPlayer().getCurrentPosition() / 60000;
                    int second = (musicService.getPlayer().getCurrentPosition() - (minutes * 60000)) / 1000;
                    if (second > 9)
                        tvTime.setText(minutes + " : " + second);
                    else tvTime.setText(minutes + " : 0" + second);
                }

                if (musicService.getPlayer().isPlaying()) {
                    circleSeekBar.postDelayed(onEverySecond, 1000);
                    Drawable drPause = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_pause_white_48dp);
                    ivPlay.setImageDrawable(drPause);
                } else {
                    Drawable drPlay = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_play_arrow_white_48dp);
                    ivPlay.setImageDrawable(drPlay);
                }
            }catch (Exception e){

            }



        }
    };


    private void playBtnDrawable() {
        if (musicService.isPng()) {
            musicService.pausePlayer();
            Drawable drPlay = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_play_arrow_white_48dp);
            ;
            ivPlay.setImageDrawable(drPlay);
        } else {
            musicService.go();
            Drawable drPause = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_pause_white_48dp);
            ivPlay.setImageDrawable(drPause);
            circleSeekBar.postDelayed(onEverySecond, 1000);
        }
    }

}
