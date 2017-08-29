package com.example.payam1991.msmusic;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.payam1991.msmusic.models.SongUtil;
import com.example.payam1991.msmusic.models.classes.Song;
import com.example.payam1991.msmusic.models.enums.MediaController;
import com.example.payam1991.msmusic.models.services.MusicService;
import com.example.payam1991.msmusic.view.adapters.AA_MainSongList;
import com.example.payam1991.msmusic.view.customs.RtlGridLayoutManager;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Song> songList;
    RecyclerView rvSongList;

    TextView tvArtist;
    TextView tvSongName;
    LinearLayout llSongPanel;

    public static MusicService musicSrv;
    ServiceConnection musicConnection;
    private Intent playIntent;
    private boolean musicBound = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return;
            }
        }

        initViews();
        SongUtil.getSongList(songList, getContentResolver());
        connectToMusicService();

        // Toast.makeText(getApplicationContext(), songList.get(0).getTitle()+"", Toast.LENGTH_LONG).show();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (playIntent == null) {
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
            // Toast.makeText(getApplicationContext(), songList.get(0).getAlbumId() + "", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        stopService(playIntent);
        musicSrv = null;
        super.onDestroy();
    }

    void initViews() {
        songList = new ArrayList<>();
        rvSongList = (RecyclerView) findViewById(R.id.rv_song_list);
        tvArtist = (TextView) findViewById(R.id.tv_singer);
        tvSongName = (TextView) findViewById(R.id.tv_song_name);
        llSongPanel= (LinearLayout) findViewById(R.id.ll_song_panel);
        recyclerViewData();
    }

    void recyclerViewData() {

        AA_MainSongList aaMainSongList = new AA_MainSongList(getApplicationContext(), this, songList);
        RtlGridLayoutManager gridLayoutManager = new RtlGridLayoutManager(this, 3);
        rvSongList.setLayoutManager(gridLayoutManager);
        rvSongList.setAdapter(aaMainSongList);

    }

    private void connectToMusicService() {

        musicConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
                musicSrv = binder.getService();
                musicSrv.setList(songList);
                musicBound = true;
                if(musicSrv.isPng())
                    llSongPanel.setVisibility(View.VISIBLE);
                LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mMessageReceiver,
                        new IntentFilter(MusicService.CONTROLLER_INTENT));
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                musicBound = false;
            }
        };

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            llSongPanel.setVisibility(View.VISIBLE);
            Song song = (Song) intent.getSerializableExtra(MusicService.CONTROLLER_SONG);
            tvSongName.setText(song.getTitle());
            tvArtist.setText(song.getArtist());
        }
    };

}
