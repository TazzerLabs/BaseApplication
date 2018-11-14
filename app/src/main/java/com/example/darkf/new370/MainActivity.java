package com.example.darkf.new370;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.util.Util;

public class MainActivity extends AppCompatActivity {
    private PlayerView playerView;
    private Context mContext;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    ExoPlayer player;
    ProgressBar loading;
    public void forwardpage (View view) {
        Intent home_activity = new Intent (this, home.class);
        startActivity(home_activity);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getBaseContext();
        playerView = (PlayerView)findViewById(R.id.video_view);
        loading = (ProgressBar)findViewById(R.id.loading);

    }
    @Override
    public void onStart() {
        super.onStart();
        //--------------------------------------
        //Creating default track selector
        //and init the player

        TrackSelection.Factory adaptiveTrackSelection = new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(mContext),
                new DefaultTrackSelector(adaptiveTrackSelection),
                new DefaultLoadControl());

        //init the player
        playerView.setPlayer(player);

        //-------------------------------------------------
        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mContext,
                Util.getUserAgent(mContext, "Exo2"), defaultBandwidthMeter);

        //-----------------------------------------------
        //Create media source
        //String hls_url = "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8";
        String hls_url = "https://video-weaver.lax03.hls.ttvnw.net/v1/playlist/CrUDxX8ze2ZRVmD_PGGKFzwX1qD0JsMba0UNLUFzL21EN7KrlL9KdEb-ofOrBk7hntlpwzl39tFYc2yI3FtQ-s3SGNbjO8nEueLIz-fx2SBRfZDBd1K2rBtL2DV6RFUYMdVqY46DTRRGTDz9wTm4XWGrZaQ48sIyFFeaI9szCGi-Mw9PporOJR2SLV8Ib2GA29Ve8JgsT-kRMV0XTzGOvWmx8YH3flAX0icG9W1m2XzfGKfvGbJW_sFuVCxzxZZkTP_Wj7IssNVqFv1w1Q1Anft5m32URoF9jtkh5hoLehkfYvzuQ0kkSSdnAaf5X-QUI_ZM6U2ieb3snXqW5SBML33osDeGDkCaqn28DB7ZLQRdpYel067Gzv2mdKhvckWf6amZPm-my-Ct82heZ-5078l9ToT_Rh18SltoyRvaiSUuqBc5k3xYDrgUnccBtYGroustkQP0uhHgbVTTBrnWnJoWhO0PxzkeHC2hGn9zb1CRNRnVsdAKkcuklqlxs2W9IxSv59RytVkEkoqnSAJ8FdQuN9nNp2Ox3F_41939sNjb2ANoCqzBYyjfUXN7c8rpn6uCKSsF5twSELsbBoNcogusFezy2CeBSoYaDJw69pmHMkOexVCv8g.m3u8";
        Uri uri = Uri.parse(hls_url);


        Handler mainHandler = new Handler();
        MediaSource mediaSource = new HlsMediaSource(uri,
                dataSourceFactory, mainHandler, null);
        player.prepare(mediaSource);


        player.setPlayWhenReady(playWhenReady);
        player.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {
                    case ExoPlayer.STATE_READY:
                        loading.setVisibility(View.GONE);
                        break;
                    case ExoPlayer.STATE_BUFFERING:
                        loading.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });
        player.seekTo(currentWindow, playbackPosition);
        player.prepare(mediaSource, true, false);

    }
    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }
    @Override
    public void onStop () {
        super.onStop();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }
}
