package com.example.allegroandroid.ui.reproducer;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;


import com.example.allegroandroid.R;
import com.example.allegroandroid.services.DateService;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;

class CustomPlayerUiController extends AbstractCustomPlayerUiController implements YouTubePlayerFullScreenListener {

    private static final String TAG = "CustomPlayerUi";


    CustomPlayerUiController(Context context, View customPlayerUi, YouTubePlayer youTubePlayer,
                             YouTubePlayerView youTubePlayerView, Activity activity) {
        this.playerUi = customPlayerUi;
        this.context = context;
        this.youTubePlayer = youTubePlayer;
        this.youTubePlayerView = youTubePlayerView;
        dateService = DateService.getInstance();
        playerTracker = new YouTubePlayerTracker();
        this.activity = activity;
        youTubePlayer.addListener(playerTracker);
        initViews(customPlayerUi);
    }

    private void initViews(View playerUi) {
        panel = playerUi.findViewById(R.id.panel);
        progessVideo = playerUi.findViewById(R.id.progressVideo);
        txtTimeNowVideo = playerUi.findViewById(R.id.txtTimeNowVideo);
        txtTotalTimeVideo = playerUi.findViewById(R.id.txtTotalTimeVideo);
        btnRestartVideo = playerUi.findViewById(R.id.btnRestartVideo);
        btnPlay = playerUi.findViewById(R.id.btnPlay);
        btnPause = playerUi.findViewById(R.id.btnPause);
        btnBack = playerUi.findViewById(R.id.btn_back);
        btnReduceView = playerUi.findViewById(R.id.btnReduceView);
        btnExpandView = playerUi.findViewById(R.id.btnExpandView);
        layoutControllers = playerUi.findViewById(R.id.layoutControllers);
        layoutProgessiveBar = playerUi.findViewById(R.id.layoutProgessVideo);
        layoutTop = playerUi.findViewById(R.id.layoutTop);
        btnRewFiveSeconds = playerUi.findViewById(R.id.exo_rew);
        btnAddFiveSeconds = playerUi.findViewById(R.id.exo_ffwd);

        btnListeners();
        goneElements();

    }

    private void btnListeners() {
        btnBack.setOnClickListener((view) -> {
            activity.onBackPressed();
        });

        btnExpandView.setOnClickListener((view) -> {
            btnExpandView.setVisibility(View.GONE);
            btnReduceView.setVisibility(View.VISIBLE);
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        });

        btnReduceView.setOnClickListener((view) -> {
            btnExpandView.setVisibility(View.VISIBLE);
            btnReduceView.setVisibility(View.GONE);
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        });

        btnPlay.setOnClickListener((view) -> {
            youTubePlayer.play();
        });

        btnPause.setOnClickListener((view) -> {
            youTubePlayer.pause();
        });

        btnRewFiveSeconds.setOnClickListener(view -> {
            youTubePlayer.seekTo(getCurrentSecond() - 5);
        });

        btnAddFiveSeconds.setOnClickListener(view -> {
            youTubePlayer.seekTo(getCurrentSecond() + 5);
        });

        panel.setOnClickListener(view -> {
            if (isShowingButtons) {
                hidenButtons();
                isShowingButtons = false;
            } else {
                showButtons();
                isShowingButtons = true;
            }
        });

        progessVideo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                youTubePlayer.pause();
                txtTimeNowVideo.setText(dateService.convertSecondsToMinutes(progressChangedValue));
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                txtTimeNowVideo.setText(dateService.convertSecondsToMinutes(progressChangedValue));
                youTubePlayer.seekTo(progressChangedValue);
                setLastSecondWasShowingMenu((float) progressChangedValue);
            }
        });


        btnRestartVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);
                youTubePlayer.seekTo(0f);
                youTubePlayer.play();
            }
        });
    }

    @Override
    public void onError(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerError error) {
        super.onError(youTubePlayer, error);
        Log.e(TAG, error.name());
    }

    @Override
    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
        Log.e(TAG, "onReady");
    }

    @Override
    public void onStateChange(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerState state) {
        if (state == PlayerConstants.PlayerState.UNSTARTED) {
            youTubePlayer.play();
        }

        if (state == PlayerConstants.PlayerState.PLAYING || state == PlayerConstants.PlayerState.VIDEO_CUED) {
            btnPause.setVisibility(View.VISIBLE);
            btnPlay.setVisibility(View.GONE);
            btnRestartVideo.setVisibility(View.GONE);
            panel.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        } else {
            if (state == PlayerConstants.PlayerState.BUFFERING || state == PlayerConstants.PlayerState.PAUSED) {
                btnPause.setVisibility(View.GONE);
                btnPlay.setVisibility(View.VISIBLE);
                btnRestartVideo.setVisibility(View.GONE);
                panel.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
            }
        }

        if (state == PlayerConstants.PlayerState.ENDED) {
            panel.setBackgroundColor(ContextCompat.getColor(context, android.R.color.background_dark));
            btnRestartVideo.setVisibility(View.VISIBLE);
        }

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onCurrentSecond(@NonNull YouTubePlayer youTubePlayer, float second) {
        txtTimeNowVideo.setText(dateService.convertSecondsToMinutes((long) second));
        progessVideo.setProgress((int) second);
        setCurrentSecond(second);
        hiddenButtonAfterSeconds(6);
    }

    private void hiddenButtonAfterSeconds(int seconds) {
        Float getLastSecondWasShowingMenuPlusSeconds = getLastSecondWasShowingMenu() + seconds;
        if (getLastSecondWasShowingMenuPlusSeconds < getCurrentSecond()) {
            hidenButtons();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onVideoDuration(@NonNull YouTubePlayer youTubePlayer, float duration) {
        setTotalVideoSeconds(duration);
        progessVideo.setMax((int) duration);
        txtTotalTimeVideo.setText(dateService.convertSecondsToMinutes((long) duration));
    }

    @Override
    public void onYouTubePlayerEnterFullScreen() {
        ViewGroup.LayoutParams viewParams = playerUi.getLayoutParams();
        viewParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        viewParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        playerUi.setLayoutParams(viewParams);
    }

    @Override
    public void onYouTubePlayerExitFullScreen() {
        ViewGroup.LayoutParams viewParams = playerUi.getLayoutParams();
        viewParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        viewParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        playerUi.setLayoutParams(viewParams);
    }

    @Override
    public void onVideoLoadedFraction(@NonNull YouTubePlayer youTubePlayer, float loadedFraction) {
        super.onVideoLoadedFraction(youTubePlayer, loadedFraction);
    }


}