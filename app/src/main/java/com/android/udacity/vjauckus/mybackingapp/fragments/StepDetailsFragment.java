package com.android.udacity.vjauckus.mybackingapp.fragments;

        import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.udacity.vjauckus.mybackingapp.R;
import com.android.udacity.vjauckus.mybackingapp.activities.StepDetailActivity;
import com.android.udacity.vjauckus.mybackingapp.models.StepsModel;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Step details fragment
 * Created on 12.02.18.
 *  Copyright (C) 2018 VJauckus
 */

public class StepDetailsFragment extends Fragment implements View.OnClickListener, ExoPlayer.EventListener{

    private final static String TAG = StepDetailsFragment.class.getSimpleName();

    private StepsModel mStepsModel;
    private ArrayList<StepsModel> mStepsList;

    @BindView(R.id.scrollView_step_long_description)
    NestedScrollView mNestedScrollView;

    @BindView(R.id.step_long_description)
    TextView mLongDescription;
    @BindView(R.id.no_video_holder) TextView mNoVideo;

    @BindView (R.id.iv_step_detail_noVideo) ImageView mNoVideoImage;

    @BindView(R.id.playerView)
    SimpleExoPlayerView mPlayerView;

    @BindView(R.id.bt_recycler_previous)
    Button mPrevious;
    @BindView(R.id.bt_recycler_next)
    Button mNext;

    private SimpleExoPlayer mExoPlayer;

    private Unbinder unbinder;

    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    long videoPosition;
    private int mStepNumber;
    boolean mNetworkOk;
    private boolean shouldAutoPlay;
    //empty Constructor
    public StepDetailsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);


        unbinder = ButterKnife.bind(this, rootView);

        mNetworkOk = checkNetworkConnection();

        if (mNetworkOk){
            if (savedInstanceState == null){
        /*    ConnectivityManager connMgr = (ConnectivityManager)
                    getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

*/
                if (getVideoUrl() != null && mNetworkOk){
                    //Video is here
                    shouldAutoPlay = true;
                    mNoVideo.setVisibility(View.GONE);
                    mNoVideoImage.setVisibility(View.GONE);
                    videoPosition = 0;
                    initializeMediaPlayer(Uri.parse(getVideoUrl()));
                    // mPlayerView.setVisibility(View.VISIBLE);
                }
                else {
                    //Video is not available
                    mPlayerView.setVisibility(View.GONE);
                    mNoVideo.setVisibility(View.VISIBLE);
                    mNoVideoImage.setVisibility(View.VISIBLE);
                }


            }
            if (savedInstanceState != null){
                //when device is rooted or any thing else
                mStepsModel = (StepsModel) savedInstanceState.getSerializable("step");
                shouldAutoPlay = savedInstanceState.getBoolean("autoPlay");
                if ( getVideoUrl() != null){
                    //Video is here
                    mNoVideoImage.setVisibility(View.GONE);
                    mNoVideo.setVisibility(View.GONE);
                    // mPlayerView.setVisibility(View.VISIBLE);
                    if(savedInstanceState.containsKey("videoPosition")){
                        videoPosition = savedInstanceState.getLong("videoPosition");
                        // Log.v(TAG, "Video Position: "+videoPosition);
                    }
                    initializeMediaPlayer(Uri.parse(getVideoUrl()));
                }
                else {
                    //Video is not available
                    mPlayerView.setVisibility(View.GONE);
                    mNoVideo.setVisibility(View.VISIBLE);
                    mNoVideoImage.setVisibility(View.VISIBLE);
                }
            }
            mLongDescription.setText(mStepsModel.getLongDescription());

            // Initialize the Media Session.
            initializeMediaSession();

            mPrevious.setOnClickListener(this);
            mNext.setOnClickListener(this);


        }

        else {
            //No Internet Connection
            mPlayerView.setVisibility(View.GONE);
            mNoVideo.setVisibility(View.VISIBLE);
            mNoVideo.setText(getResources().getString(R.string.internet_connection_error));
            mNoVideoImage.setVisibility(View.GONE);

            // Toast.makeText(getContext().getApplicationContext(), getString(R.string.check_internet), Toast.LENGTH_SHORT).show();

        }

        return rootView;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    public boolean checkNetworkConnection(){
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();


        return   networkInfo!= null && networkInfo.isConnected();
    }


    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onClick(View view) {

        // Log.v(TAG, "Position of Step is: "+ mStepNumber);

        switch (view.getId()){
            case R.id.bt_recycler_previous:
                //  Log.v(TAG, "Button Previous was clicked");
                if (mStepNumber == 0){
                    // Log.v(TAG, "Position is still 0, because FIRST Step."+ mStepNumber);

                    Toast.makeText(getActivity().getApplicationContext(),getString(R.string.firstPositionOfSteps), Toast.LENGTH_SHORT).show();
                }
                else {
                    mStepNumber--;
                    replaceFragment();
                }

                break;
            case R.id.bt_recycler_next:

                // Log.v(TAG, "Button Next was clicked");
                if (mStepNumber < mStepsList.size() - 1){
                    mStepNumber++;
                    replaceFragment();
                }
                else {
                    // Log.v(TAG, "Position is still the same, because LAST Step."+ mStepNumber);

                    Toast.makeText(getActivity().getApplicationContext(),getString(R.string.lastPositionOfSteps), Toast.LENGTH_SHORT).show();
                }
                break;
        }


    }

    //Replace Fragment after Click Next/Previous Button
    private void replaceFragment(){

        boolean  isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isTablet){
            //I am on Tablet
            //mIngredientsClicked = false;
            // Log.v(TAG, "I am in Tablet");
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            stepDetailsFragment.setStepsListAndPosition(mStepsList, mStepNumber);

            getFragmentManager().beginTransaction()
                    .replace(R.id.ingredients_detail_container, stepDetailsFragment)
                    .commit();
        }
        else {
            // I am onPhone
            Intent stepIntent = new Intent( getContext(), StepDetailActivity.class);

            Bundle bundle = new Bundle();

            bundle.putSerializable("stepsList", mStepsList);
            bundle.putInt("stepNumber", mStepNumber);
            stepIntent.putExtra(Intent.EXTRA_TEXT, bundle);
            startActivity(stepIntent);
        }
    }

    // get Video or ThumbnailUrl String
    private String getVideoUrl(){

        if (! mStepsModel.getVideoURL().equals("")) return mStepsModel.getVideoURL();
        else if(! mStepsModel.getThumbnailURL().equals("")) return  mStepsModel.getThumbnailURL();

        return null;

    }

    /**
     * Initialize ExoPLayer
     * @param mediaUri The Uri of the step to play
     */
    private void initializeMediaPlayer(Uri mediaUri){

        if (mExoPlayer == null){
            //Create an instance of Exoplayer
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);

            //prepare the MediaSource
            String userAgent = Util.getUserAgent(getActivity(), "Backing instruction video");

            MediaSource mediaSource = new ExtractorMediaSource(mediaUri,
                    new DefaultDataSourceFactory(getActivity(), userAgent), new DefaultExtractorsFactory(),
                    null,null);
          /*  if (videoPosition > 0){
                //  Log.v(TAG,"Play AGAIN from position: "+videoPosition);
                mExoPlayer.seekTo(videoPosition);
            }*/
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(shouldAutoPlay);
            mExoPlayer.seekTo(videoPosition);
            mPlayerView.setPlayer(mExoPlayer);

        }
    }
    /**
     * Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
     * and media controller.
     */
    private void initializeMediaSession(){
        //Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(getContext(), TAG);

        //Enable callbacks from MediaButtons and Transport controls.

        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player, when the app is not visible
        mMediaSession.setMediaButtonReceiver(null);
        //Set an initial PlaybackState with Action
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);
        //My SessionCallbacks has methods that handle callbacks from the media controller
        mMediaSession.setCallback(new MySessionCallback());

        mMediaSession.setActive(true);


    }

    /**
     * Media Session callbacks, where all external clients control the player
     */
    private class MySessionCallback extends MediaSessionCompat.Callback{
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);

        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }
    }
    /**
     * Release ExoPlayer
     */
    private void releasePlayer(){
        if (!mStepsModel.getVideoURL().equals("") && mExoPlayer != null){
            //save current position of player
            videoPosition = mExoPlayer.getCurrentPosition();
            shouldAutoPlay = mExoPlayer.getPlayWhenReady();
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }

    }
    /**
     * Method that is called when the ExoPlayer state changes. Used to update the MediaSession
     * PlayBackState to keep in sync, and post the media notification.
     * @param playWhenReady true if ExoPlayer is playing, false if it's paused.
     * @param playbackState int describing the state of ExoPlayer. Can be STATE_READY, STATE_IDLE,
     *                      STATE_BUFFERING, or STATE_ENDED.
     */
    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState){

        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("videoPosition", videoPosition);
        outState.putSerializable("step", mStepsModel);
        outState.putBoolean("autoPlay", shouldAutoPlay);
    }


    /**
     * Release the player when the fragment is paused.
     */
    @Override
    public void onPause() {
        super.onPause();
        // if (mStepsModel != null){
        if (Util.SDK_INT <= 23){
            releasePlayer();
        }

        // }
    }



    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23){
            if (getVideoUrl() != null && mNetworkOk){
                //Video is here
                initializeMediaPlayer(Uri.parse(getVideoUrl()));

            }

        }
    }
    /**
     * Release the player when the fragment is stopped.
     */
    @Override
    public void onStop() {
        super.onStop();
        // if (mStepsModel != null){
        if (Util.SDK_INT > 23){
            releasePlayer();
        }

        // }
    }



    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || mExoPlayer == null){
            if (getVideoUrl() != null && mNetworkOk){
                //Video is here
                initializeMediaPlayer(Uri.parse(getVideoUrl()));

            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mMediaSession != null){
            mMediaSession.setActive(false);
        }

    }

    /*public void setStepModel(StepsModel stepModel){
        mStepsModel = stepModel;
    }
    */
    public void setStepsListAndPosition(ArrayList<StepsModel> stepsModels, int stepPosition){
        mStepsList = stepsModels;
        mStepNumber = stepPosition;

        if (stepsModels != null){
            mStepsModel = mStepsList.get(stepPosition);
        }
    }


}

