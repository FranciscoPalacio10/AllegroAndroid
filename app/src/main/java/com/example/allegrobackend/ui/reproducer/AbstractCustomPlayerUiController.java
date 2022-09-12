package com.example.allegrobackend.ui.reproducer;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.allegrobackend.services.DateService;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public abstract class AbstractCustomPlayerUiController extends AbstractYouTubePlayerListener {

    protected Float currentSecond =0f,timePlayingVideo=0f, lastSecondWasShowingMenu =0f;
    protected LinearLayout layoutControllers, layoutProgessiveBar,layoutTop;
    protected  View playerUi;
    protected DateService dateService;
    protected Context context;
    protected YouTubePlayer youTubePlayer;
    protected YouTubePlayerView youTubePlayerView;
    protected SeekBar progessVideo;
    protected ImageButton btnPlay, btnPause, btnRestartVideo, btnExpandView, btnReduceView;
    // panel is used to intercept clicks on the WebView, I don't want the user to be able to click the WebView directly.
    protected View panel;
    protected TextView txtTimeNowVideo, txtTotalTimeVideo;

    protected boolean isShowingButtons=true;
    protected YouTubePlayerTracker playerTracker;
    protected boolean fullscreen = false;

    protected void setCurrentSecond(Float currentSecond) {
        this.currentSecond = currentSecond;
    }

    protected Float getLastSecondWasShowingMenu() {
        return lastSecondWasShowingMenu;
    }

    protected YouTubePlayer getYouTubePlayer() {
        return youTubePlayer;
    }

    protected void setLastSecondWasShowingMenu(Float lastSecondWasShowingMenu) {
        this.lastSecondWasShowingMenu = lastSecondWasShowingMenu;
    }

    protected Float getCurrentSecond() {
        return currentSecond;
    }

    protected Float getTimePlayingVideo() {
        return timePlayingVideo;
    }

    protected void setTimePlayingVideo(Float timePlayingVideo) {
        this.timePlayingVideo = timePlayingVideo;
    }

    protected LinearLayout getLayoutControllers() {
        return layoutControllers;
    }

    protected void setLayoutControllers(LinearLayout layoutControllers) {
        this.layoutControllers = layoutControllers;
    }

    protected LinearLayout getLayoutProgessiveBar() {
        return layoutProgessiveBar;
    }

    protected void setLayoutProgessiveBar(LinearLayout layoutProgessiveBar) {
        this.layoutProgessiveBar = layoutProgessiveBar;
    }

    protected LinearLayout getLayoutTop() {
        return layoutTop;
    }

    protected void setLayoutTop(LinearLayout layoutTop) {
        this.layoutTop = layoutTop;
    }

    protected View getPlayerUi() {
        return playerUi;
    }

    protected void setPlayerUi(View playerUi) {
        this.playerUi = playerUi;
    }

    protected DateService getDateService() {
        return dateService;
    }

    protected void setDateService(DateService dateService) {
        this.dateService = dateService;
    }

    protected Context getContext() {
        return context;
    }

    protected void setContext(Context context) {
        this.context = context;
    }

    protected void setYouTubePlayer(YouTubePlayer youTubePlayer) {
        this.youTubePlayer = youTubePlayer;
    }

    protected YouTubePlayerView getYouTubePlayerView() {
        return youTubePlayerView;
    }

    protected void setYouTubePlayerView(YouTubePlayerView youTubePlayerView) {
        this.youTubePlayerView = youTubePlayerView;
    }

    protected SeekBar getProgessVideo() {
        return progessVideo;
    }

    protected void setProgessVideo(SeekBar progessVideo) {
        this.progessVideo = progessVideo;
    }

    protected ImageButton getBtnPlay() {
        return btnPlay;
    }

    protected void setBtnPlay(ImageButton btnPlay) {
        this.btnPlay = btnPlay;
    }

    protected ImageButton getBtnPause() {
        return btnPause;
    }

    protected void setBtnPause(ImageButton btnPause) {
        this.btnPause = btnPause;
    }

    protected ImageButton getBtnRestartVideo() {
        return btnRestartVideo;
    }

    protected void setBtnRestartVideo(ImageButton btnRestartVideo) {
        this.btnRestartVideo = btnRestartVideo;
    }

    protected ImageButton getBtnExpandView() {
        return btnExpandView;
    }

    protected void setBtnExpandView(ImageButton btnExpandView) {
        this.btnExpandView = btnExpandView;
    }

    protected ImageButton getBtnReduceView() {
        return btnReduceView;
    }

    protected void setBtnReduceView(ImageButton btnReduceView) {
        this.btnReduceView = btnReduceView;
    }

    protected View getPanel() {
        return panel;
    }

    protected void setPanel(View panel) {
        this.panel = panel;
    }

    protected TextView getTxtTimeNowVideo() {
        return txtTimeNowVideo;
    }

    protected void setTxtTimeNowVideo(TextView txtTimeNowVideo) {
        this.txtTimeNowVideo = txtTimeNowVideo;
    }

    protected TextView getTxtTotalTimeVideo() {
        return txtTotalTimeVideo;
    }

    protected void setTxtTotalTimeVideo(TextView txtTotalTimeVideo) {
        this.txtTotalTimeVideo = txtTotalTimeVideo;
    }

    protected boolean isShowingButtons() {
        return isShowingButtons;
    }

    protected void setShowingButtons(boolean showingButtons) {
        isShowingButtons = showingButtons;
    }

    protected YouTubePlayerTracker getPlayerTracker() {
        return playerTracker;
    }

    protected void setPlayerTracker(YouTubePlayerTracker playerTracker) {
        this.playerTracker = playerTracker;
    }

    protected boolean isFullscreen() {
        return fullscreen;
    }

    protected void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
    }

    protected void goneElements() {
        btnRestartVideo.setVisibility(View.GONE);
        btnPause.setVisibility(View.GONE);
        btnReduceView.setVisibility(View.GONE);
    }

    protected void showButtons() {
        setLastSecondWasShowingMenu(getCurrentSecond());
        layoutControllers.setVisibility(View.VISIBLE);
        layoutProgessiveBar.setVisibility(View.VISIBLE);
        layoutTop.setVisibility(View.VISIBLE);
    }

    protected void hidenButtons() {
        layoutControllers.setVisibility(View.GONE);
        layoutProgessiveBar.setVisibility(View.GONE);
        layoutTop.setVisibility(View.GONE);
    }


}

