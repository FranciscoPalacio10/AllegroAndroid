package com.example.allegroandroid.reprodcutor;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.allegroandroid.R;
import com.example.allegroandroid.registroClasesFireBase;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.notifiacion;

public class reproductorClase extends AppCompatActivity {
    private Uri videoUri, urlvideos;
    private String urlvideo, titulo, estilo, nivel, nroClase, profesora, usuario, minuto, estado;
    private LinearLayout root;
    private SimpleExoPlayer player;
    private PlayerView playerView;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private boolean isplaying = true;
    private long playbackPosition = 0;
    private int playbackState = 0;
    private PlaybackStateListener playbackStateListener;
    private static final String TAG = reproductorClase.class.getName();
    private boolean cliqueado = false;
    private ProgressBar cargando;
    private int contador = 0;
    private int caragando = 0;
    private FirebaseAuth mAuth;
    private DatabaseReference basededatos;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseUser user;
    private ImageButton btn_Volver, btn_CargardeNuevo;
    private TextView tituloClase;
    private notifiacion notificacion;
    private String CHANNEL_ID = "NOTIREPRODUCTOR";
    private int notficacionid = 1;
    private boolean finalizado = false;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    public void onBackPressed() {
        root.setVisibility(View.GONE);
        AlertDialog.Builder builder = new AlertDialog.Builder(reproductorClase.this);
        builder.setTitle("¿Quiere finalizar la clase " + tituloClase.getText() + " ?");
        builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                registrarEnBaseDeDATOS();
                finish();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                hideSystemUi();
                if ((Util.SDK_INT < 19 || player == null)) {
                    initializePlayer();
                }
            }
        });
        builder.setMessage("Podrás ver el historial de clases en la seccion CLASES");
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    protected void onDestroy() {
        NotificationManager notificationManager = ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
        notificationManager.cancelAll();
        super.onDestroy();

    }

    @Override
    public void finish() {
        NotificationManager notificationManager = ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
        notificationManager.cancelAll();
        super.finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle miBundle = this.getIntent().getExtras();
        urlvideo = "https://youtu.be/6Nhc7eYZ2Uc";
        if (miBundle != null) {
            estilo = miBundle.getString("estilo");
            nivel = miBundle.getString("nivel");
            nroClase = miBundle.getString("nroClase");
            profesora = miBundle.getString("profesora");
            playbackPosition = miBundle.getLong("tiempo");

            titulo = estilo.concat(" ").concat(nivel).concat(" ").concat(nroClase);
        }
        setContentView(R.layout.activity_reproductor_clase);
        obtenerUsuario();
        obtenerUsuarioAtuh();
        declariacionParametros();
        getSupportActionBar().hide();
        volver();
        finalizado = false;
    }

    private void declariacionParametros() {
        notificacion = new notifiacion(getApplicationContext(), CHANNEL_ID, notficacionid, urlvideo, estilo, nivel, nroClase, profesora);
        playerView = findViewById(R.id.player);
        playbackStateListener = new PlaybackStateListener();
        cargando = findViewById(R.id.progressBar);
        root = findViewById(R.id.top);
        btn_Volver = findViewById(R.id.btn_back);
        tituloClase = findViewById(R.id.txt_title);
        btn_CargardeNuevo = findViewById(R.id.btn_CargardeNuevo);
    }

    private void obtenerUsuario() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestProfile()
                .requestEmail()
                .build();
        mAuth = FirebaseAuth.getInstance();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void obtenerUsuarioAtuh() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            usuario = user.getUid();
            Log.e("id", usuario);
        }

    }

    public void volver() {
        btn_Volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.setVisibility(View.GONE);
                onBackPressed();
            }
        });
    }

    private void registrarEnBaseDeDATOS() {
        obtenerEstado();
        registroClasesFireBase registro = new registroClasesFireBase(usuario, estilo, nivel, profesora, urlvideo, minuto, estado,
                String.valueOf(currentWindow), nroClase);
        registro.guardarClaseenCurso();
    }

    private void obtenerEstado() {
        if (player != null) {
            if (finalizado == true) {
                estado = "finalizado";
                minuto = String.valueOf(player.getCurrentPosition());
                Log.e("estado", estado);
            } else {
                estado = "play";
                minuto = String.valueOf(player.getCurrentPosition());
                Log.e("estado", estado);
            }


        }
    }

    private void volverACargarVideo() {
        btn_CargardeNuevo.setVisibility(View.VISIBLE);
        btn_CargardeNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.seekTo(0);
                player.setPlayWhenReady(false);

            }
        });

    }

    private void ocultarBarraTitulo() {
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.setVisibility(View.GONE);
            }
        });
    }


    private void initializePlayer() {
        if (player == null) {
            DefaultTrackSelector trackSelector = new DefaultTrackSelector();
            trackSelector.setParameters(
                    trackSelector.buildUponParameters().setMaxVideoSizeSd());
            player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            playerView.setPlayer(player);
            videoUri = Uri.parse(urlvideo);
            MediaSource mediaSource = buildMediaSource(videoUri);
            player.setPlayWhenReady(playWhenReady);
            isplaying = true;
            player.seekTo(currentWindow, playbackPosition);
            player.addListener(playbackStateListener);
            player.prepare(mediaSource, false, false);
            cargando.setVisibility(View.INVISIBLE);
            tituloClase.setText(titulo);
            root.setVisibility(View.VISIBLE);
            btn_CargardeNuevo.setVisibility(View.GONE);
            ocultarBarraTitulo();
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(this, "exoplayer-codelab");
        DashMediaSource.Factory mediaSourceFactory = new DashMediaSource.Factory(dataSourceFactory);
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 19) {
            initializePlayer();

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        notificacion.cerrarNotificacion();
        hideSystemUi();
        if ((Util.SDK_INT < 19 || player == null)) {
            initializePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void openNotifiacion() {
        notificacion.createNotificationChannel("Notifiacion", "nuevo");
        notificacion.pendingIntent();
        notificacion.crearNotificacion(R.drawable.icono1,
                "ESTAS EN LA CLASE:", tituloClase.getText().toString(), "Volver a la clase");
    }

    @Override
    public void onPause() {
        super.onPause();
        openNotifiacion();
        if (Util.SDK_INT < 19) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        registrarEnBaseDeDATOS();
        super.onStop();
        if (Util.SDK_INT >= 19) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            player.removeListener(playbackStateListener);
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }


    private class PlaybackStateListener implements Player.EventListener {

        @Override
        public void onPlayerStateChanged(boolean playWhenReady,
                                         int playbackState) {

            String stateString;
            switch (playbackState) {
                case ExoPlayer.STATE_IDLE:
                    stateString = "ExoPlayer.STATE_IDLE";
                    break;
                case ExoPlayer.STATE_BUFFERING:
                    root.setVisibility(View.VISIBLE);
                    cargando.setVisibility(View.VISIBLE);
                    stateString = "ExoPlayer.STATE_BUFFERING";
                    break;
                case ExoPlayer.STATE_READY:
                    stateString = "ExoPlayer.STATE_READY";
                    root.setVisibility(View.VISIBLE);
                    cargando.setVisibility(View.INVISIBLE);
                    btn_CargardeNuevo.setVisibility(View.GONE);
                    break;
                case ExoPlayer.STATE_ENDED:
                    stateString = "ExoPlayer.STATE_ENDED";
                    finalizado = true;
                    root.setVisibility(View.VISIBLE);
                    volverACargarVideo();
                    break;
                default:
                    stateString = "UNKNOWN_STATE";
                    break;
            }

            Log.d(TAG, "changed state to " + stateString
                    + " playWhenReady: " + playWhenReady);
        }

        @Override
        public void onIsPlayingChanged(boolean isPlaying) {
            if (isPlaying) {
                root.setVisibility(View.GONE);
/*
                Handler handler = new Handler();
                //Llamamos al método postDelayed
                handler.postDelayed(new Runnable() {
                    public void run() {


                    }
                }, 4300); // 2 segundos de "delay

 */


            } else {
                /*
                root.setVisibility(View.VISIBLE);

                playerView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(!cliqueado){
                            root.setVisibility(View.GONE);
                            cliqueado=true;
                        }else{
                            root.setVisibility(View.VISIBLE);
                            cliqueado=false;
                        }



                    }
                });


                 */
            }
        }
    }


}





