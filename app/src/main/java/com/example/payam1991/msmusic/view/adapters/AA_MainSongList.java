package com.example.payam1991.msmusic.view.adapters;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.payam1991.msmusic.MainActivity;
import com.example.payam1991.msmusic.R;
import com.example.payam1991.msmusic.SongActivity;
import com.example.payam1991.msmusic.models.classes.Song;
import com.example.payam1991.msmusic.models.services.MusicService;
import com.example.payam1991.msmusic.view.holders.MainSongListHolder;

import java.io.File;
import java.util.List;

/**
 * @author Payam1991 on 8/29/2017.
 */

public class AA_MainSongList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Song> songList;
    Context context;
    Activity act;
    private LayoutInflater mInflater;

    public AA_MainSongList(Context context, Activity act, List<Song> songList) {
        this.songList = songList;
        this.context = context;
        this.act=act;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainSongListHolder(mInflater.inflate(R.layout.item_music_in_main_grid_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        MainSongListHolder songListHolder= (MainSongListHolder) holder;
        final Song song=songList.get(position);
        songListHolder.getTvArtist().setText(song.getArtist());
        songListHolder.getTvName().setText(song.getTitle());
        Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
        Uri uri = ContentUris.withAppendedId(sArtworkUri, Integer.valueOf((int) song.getAlbumId()));
        Glide.with(context)
                .load(uri)
        .into(songListHolder.getIvThumb());

        songListHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(act, SongActivity.class);
                MainActivity.musicSrv.setSong(position);
                MainActivity.musicSrv.playSong();
                act.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return songList.size();
    }
}
