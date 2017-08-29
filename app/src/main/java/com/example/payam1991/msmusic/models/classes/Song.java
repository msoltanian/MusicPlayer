package com.example.payam1991.msmusic.models.classes;

import java.io.Serializable;

/**
 * @author Payam1991 on 8/29/2017.
 */

public class Song implements Serializable{
    private long id;
    private String title;
    private String artist;
    private long albumId;

    public Song(long songID, String songTitle, String songArtist, long albumId) {
        id = songID;
        title = songTitle;
        artist = songArtist;
        this.albumId = albumId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }
}
