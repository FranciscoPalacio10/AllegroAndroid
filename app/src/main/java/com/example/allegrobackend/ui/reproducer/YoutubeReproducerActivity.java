package com.example.allegrobackend.ui.reproducer;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.allegrobackend.R;
import com.google.firebase.database.annotations.NotNull;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;


public class YoutubeReproducerActivity extends AppCompatActivity{

    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayerListener listener;
    private CustomPlayerUiController customPlayerUiController;
    public Float timeToStartVideo=0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_reproductor);
        init();
    }

    private void init() {
        Bundle extras = getBundle();
        initYoutbePlayer(extras.getString("VideoId"));
        hideSystemUi();
    }

    private void initYoutbePlayer(String videoId) {
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);
        View customPlayerUi = youTubePlayerView.inflateCustomPlayerUi(R.layout.custom_player_ui);
        listener = new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                customPlayerUiController = new CustomPlayerUiController(YoutubeReproducerActivity.this, customPlayerUi, youTubePlayer, youTubePlayerView);
                youTubePlayer.addListener(customPlayerUiController);
                youTubePlayerView.addFullScreenListener(customPlayerUiController);
                youTubePlayer.cueVideo(videoId,timeToStartVideo);
            }
        };

        // disable web ui
        IFramePlayerOptions options = new IFramePlayerOptions.Builder().controls(0).build();
        youTubePlayerView.initialize(listener, options);
    }

    private Bundle getBundle() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        }
        return extras;
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        youTubePlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void saveCurrentSecond() {
        if(customPlayerUiController != null){
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        youTubePlayerView.release();
    }

    @Override
    public void onConfigurationChanged(@NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            customPlayerUiController.getBtnExpandView().setVisibility(View.GONE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            customPlayerUiController.onYouTubePlayerExitFullScreen();
            customPlayerUiController.getBtnExpandView().setVisibility(View.VISIBLE);
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }

    }
}

