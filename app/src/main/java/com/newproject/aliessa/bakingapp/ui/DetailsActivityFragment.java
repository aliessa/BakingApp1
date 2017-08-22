package com.newproject.aliessa.bakingapp.ui;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.newproject.aliessa.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ali Essa on 5/30/2017
 */

public class DetailsActivityFragment extends Fragment implements ExoPlayer.EventListener {

    private View view;
    @BindView(R.id.next)
    Button next;
    @BindView(R.id.prev)
    Button prev;
    @BindView(R.id.TV_description)
    TextView longDescription;
    @BindView(R.id.playeView)
    SimpleExoPlayerView simpleExoPlayerView;
    protected static int index = -1;
    private SimpleExoPlayer simpleExoPlayer;
    private static MediaSessionCompat mediaSessionCompat;
    private PlaybackStateCompat.Builder builder;
    private static long position = 0;
    private static final String TAG = DetailsActivity.class.getSimpleName();

    public DetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.details_activity_fragment, container, false);
        ButterKnife.bind(this, view);

            if (index < 0) {
                index = getActivity().getIntent().getExtras().getInt("item");
            }

        initializeionMediaSession();
        initializeionPlayer(Uri.parse(StepsActivityFragment.steps.get(index).getVideoURL()));
        getActivity().setTitle(StepsActivityFragment.steps.get(index).getShortDescription());
        longDescription.setText(StepsActivityFragment.steps.get(index).getDescription());
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index > 0) {
                    index--;
                    longDescription.setText(StepsActivityFragment.steps.get(index).getDescription());
                    previousExoPlayer(0, false);
                    initializeionPlayer(Uri.parse(StepsActivityFragment.steps.get(index).getVideoURL()));
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index < StepsActivityFragment.steps.size() - 1) {
                    index++;
                    longDescription.setText(StepsActivityFragment.steps.get(index).getDescription());
                    previousExoPlayer(0, false);
                    initializeionPlayer(Uri.parse(StepsActivityFragment.steps.get(index).getVideoURL()));
                }
            }
        });
        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE && !MainActivity.tablet_on) {
            hideSystemUI();
            simpleExoPlayerView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            longDescription.setVisibility(View.GONE);
            prev.setVisibility(View.GONE);
            next.setVisibility(View.GONE);
            initializeionPlayer(Uri.parse(StepsActivityFragment.steps.get(index).getVideoURL()));
        }
        return view;
    }

    private void initializeionMediaSession() {
        mediaSessionCompat = new MediaSessionCompat(getContext(), TAG);
        mediaSessionCompat.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSessionCompat.setMediaButtonReceiver(null);
        builder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mediaSessionCompat.setPlaybackState(builder.build());
        mediaSessionCompat.setCallback(new MediaSessionCallback());
        mediaSessionCompat.setActive(true);
    }

    private void initializeionPlayer(Uri mediaUri) {
        getActivity().setTitle(StepsActivityFragment.steps.get(index).getShortDescription());
        String userAgent = Util.getUserAgent(getContext(), "StepsDetailsFragment");
        MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
        simpleExoPlayerView.setPlayer(simpleExoPlayer);
        simpleExoPlayer.addListener(this);
        simpleExoPlayer.prepare(mediaSource);
        previousExoPlayer(position, false);
    }

    private class MediaSessionCallback extends MediaSessionCompat.Callback {

        @Override
        public void onPlay() {
            simpleExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            simpleExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            previousExoPlayer(0, false);
        }

    }


    private void previousExoPlayer(long position, boolean playWhenReady) {
        this.position = position;
        simpleExoPlayer.seekTo(position);
        simpleExoPlayer.setPlayWhenReady(playWhenReady);
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPause() {
        super.onPause();
        simpleExoPlayer.setPlayWhenReady(false);
        mediaSessionCompat.setActive(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.stop();
        simpleExoPlayer.release();
        simpleExoPlayer = null;
        mediaSessionCompat.setActive(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mediaSessionCompat.setActive(true);
    }

    private void hideSystemUI() {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
