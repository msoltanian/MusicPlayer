package com.example.payam1991.msmusic.view.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.payam1991.msmusic.R;

/**
 * @author Payam1991 on 8/29/2017.
 */

public class MainSongListHolder extends RecyclerView.ViewHolder {

    private ImageView ivThumb;
    private TextView tvName;
    private TextView tvArtist;

    public MainSongListHolder(View itemView) {
        super(itemView);
        ivThumb = (ImageView) itemView.findViewById(R.id.iv_music_thumb);
        tvArtist = (TextView) itemView.findViewById(R.id.tv_singer);
        tvName = (TextView) itemView.findViewById(R.id.tv_song_name);
    }

    public ImageView getIvThumb() {
        return ivThumb;
    }

    public void setIvThumb(ImageView ivThumb) {
        this.ivThumb = ivThumb;
    }

    public TextView getTvName() {
        return tvName;
    }

    public void setTvName(TextView tvName) {
        this.tvName = tvName;
    }

    public TextView getTvArtist() {
        return tvArtist;
    }

    public void setTvArtist(TextView tvArtist) {
        this.tvArtist = tvArtist;
    }
}
