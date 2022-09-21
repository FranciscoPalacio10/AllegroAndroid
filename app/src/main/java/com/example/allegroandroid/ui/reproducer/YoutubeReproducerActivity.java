package com.example.allegroandroid.ui.reproducer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.allegroandroid.R;
import com.example.allegroandroid.constants.AppConstant;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseRequest;
import com.example.allegroandroid.models.historialdeclase.HistorialDeClaseResponse;
import com.example.allegroandroid.models.pointsxuser.PointXUserRequest;
import com.example.allegroandroid.models.pointsxuser.PointXUserResponse;
import com.example.allegroandroid.repository.HistorialDeClaseRepository;
import com.example.allegroandroid.repository.PointXUserRepository;
import com.example.allegroandroid.repository.resource.Resource;
import com.example.allegroandroid.repository.resource.Status;
import com.example.allegroandroid.services.FireBaseLoginService;
import com.example.allegroandroid.services.token.SessionService;
import com.example.allegroandroid.ui.MainActivity;
import com.example.allegroandroid.ui.MyViewModelFactory;
import com.example.allegroandroid.utils.AppExecutors;
import com.example.allegroandroid.utils.AppModule;
import com.example.allegroandroid.viewmodel.HistorialDeClasesViewModel;
import com.example.allegroandroid.viewmodel.PointXUserViewModel;
import com.google.firebase.database.annotations.NotNull;
import com.google.gson.Gson;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class YoutubeReproducerActivity extends AppCompatActivity {

    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayerListener listener;
    private CustomPlayerUiController customPlayerUiController;

    private HistorialDeClaseResponse historialDeClaseResponse;
    private Activity activity;
    private HistorialDeClasesViewModel historialDeClasesViewModel;
    private AppExecutors appExecutors;
    private AppModule appModule;
    private FireBaseLoginService fireBaseLoginService;
    private PointXUserViewModel pointXUserViewModel;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_reproductor);
        init();
   }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);

    }

    private void init() {
        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Espera unos segundos...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
        HistorialDeClaseResponse historialDeClaseResponse = getExtras(AppConstant.HISTORIAL_DE_CLASE_RESPONSE);
        this.historialDeClaseResponse = historialDeClaseResponse;
        this.activity = this;
        initViewModelService();
        initYoutbePlayer(historialDeClaseResponse);
        postHistorial();
        hideSystemUi();
    }

    private void postHistorial() {
        historialDeClasesViewModel.setHistorialClaseRequestPost(new HistorialDeClaseRequest(false, historialDeClaseResponse.clase.claseId, 1,
                1, 0));
        historialDeClasesViewModel.postHistorialDeClases(fireBaseLoginService.getCurrentUser().getEmail()).observe(this, new Observer<Resource<HistorialDeClaseResponse>>() {
            @Override
            public void onChanged(Resource<HistorialDeClaseResponse> historialDeClaseResponseResource) {
                if (historialDeClaseResponseResource.status == Status.SUCCESS) {
                    historialDeClaseResponse = historialDeClaseResponseResource.data;
                }
            }
        });
    }

    private void initViewModelService() {
        appExecutors = new AppExecutors();
        appModule = new AppModule(getApplicationContext());

        fireBaseLoginService = new FireBaseLoginService(getString(R.string.default_web_client_id), this);

        historialDeClasesViewModel = ViewModelProviders.of(this, new MyViewModelFactory<>(getApplication(),
                new HistorialDeClaseRepository(appExecutors, appModule.provideHistorialDeClaseRetrofit(), appModule.provideDb(),
                        new SessionService(this)
                )))
                .get(HistorialDeClasesViewModel.class);

        pointXUserViewModel = ViewModelProviders.of(this, new MyViewModelFactory<>(getApplication(),
                new PointXUserRepository(appExecutors, appModule.providePointsXUserRetrofit(), appModule.provideDb(),
                        new SessionService(this)
                )))
                .get(PointXUserViewModel.class);
    }

    private void postPointXUser(int historialDeClaseId){
        pointXUserViewModel.setHistorialPointXUserRequest(new PointXUserRequest(historialDeClaseId,0));
    }

    private void initYoutbePlayer(HistorialDeClaseResponse historialDeClaseResponse) {
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);
        View customPlayerUi = youTubePlayerView.inflateCustomPlayerUi(R.layout.custom_player_ui);

        listener = new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                progress.dismiss();
                customPlayerUiController = new CustomPlayerUiController(YoutubeReproducerActivity.this, customPlayerUi, youTubePlayer,
                        youTubePlayerView, activity);
                youTubePlayer.addListener(customPlayerUiController);
                youTubePlayerView.addFullScreenListener(customPlayerUiController);
                youTubePlayer.cueVideo(historialDeClaseResponse.clase.url, historialDeClaseResponse.timeLeaveVideo);
                customPlayerUiController.onYouTubePlayerExitFullScreen();
            }
        };

        // disable web ui
        IFramePlayerOptions options = new IFramePlayerOptions.Builder().controls(0).build();
        youTubePlayerView.initialize(listener, options);

    }


    private HistorialDeClaseResponse getExtras(String key) {
        String historialClaseString = getIntent().getExtras().getString(key);
        Gson gson = new Gson();
        HistorialDeClaseResponse jsonHistorialDeClaseResponse = gson.fromJson(historialClaseString, HistorialDeClaseResponse.class);
        return jsonHistorialDeClaseResponse;
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
        updateHistorialAndPointsXUser();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        youTubePlayerView.release();
        Intent intent = new Intent(YoutubeReproducerActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        youTubePlayerView.release();
        super.onDestroy();
    }

    private void updateHistorialAndPointsXUser() {
        historialDeClasesViewModel.setHistorialClaseRequestPost(new HistorialDeClaseRequest(false,
                historialDeClaseResponse.claseId, customPlayerUiController.getTotalVideoSeconds().intValue(),
                customPlayerUiController.getCurrentSecond().intValue(), 0));
        historialDeClasesViewModel.putHistorialDeClases(fireBaseLoginService.getCurrentUser().getEmail(),
                historialDeClaseResponse.historialDeClaseId).observe(this, new Observer<Resource<HistorialDeClaseResponse>>() {
            @Override
            public void onChanged(Resource<HistorialDeClaseResponse> historialDeClaseResponseResource) {
                if (historialDeClaseResponseResource.status == Status.SUCCESS) {
                    historialDeClaseResponse = historialDeClaseResponseResource.data;
                    postPointXUser(historialDeClaseResponseResource.data.historialDeClaseId);
                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(@NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ConfigScreenRotation(newConfig);
    }

    private void ConfigScreenRotation(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            customPlayerUiController.onYouTubePlayerEnterFullScreen();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            customPlayerUiController.onYouTubePlayerExitFullScreen();
        }
    }
}

