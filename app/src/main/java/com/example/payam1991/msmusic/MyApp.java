package com.example.payam1991.msmusic;

import android.app.Application;
import android.content.Intent;

import com.bumptech.glide.request.target.ViewTarget;
import com.example.payam1991.msmusic.models.services.MusicService;

/**
 * Created by msoltanian on 8/30/2017.
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Intent playIntent = new Intent(this, MusicService.class);
        startService(playIntent);
    }
}
