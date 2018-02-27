package com.android.udacity.vjauckus.mybackingapp;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.android.udacity.vjauckus.mybackingapp.IdlingResource.SimpleIdlingResource;

/**
 * Created by veronika on 19.02.18.
 */

public class DataDownloader  {
    private static final int DELAY_MILLIS = 10000;

    //Callback after data was loaded for setting IdlingResource
    public interface DataLoadedCallback{
        void onDone();
    }

    /**
     *simulates downloading data from the internet.
     * @param context Context
     * @param dataLoadedCallback DataLoadedCallback
     * @param idlingResource callback used to notify the caller asynchronously
     */
    public static void downloadBackingData(Context context, final DataLoadedCallback dataLoadedCallback,
                                    @Nullable final SimpleIdlingResource idlingResource){
        /*
         * The IdlingResource is null in production as set by the @Nullable annotation which means
         * the value is allowed to be null.
         *
         * If the idle state is true, Espresso can perform the next action.
         * If the idle state is false, Espresso will wait until it is true before
         * performing the next action.
         */
        if (idlingResource != null){
            idlingResource.setIdleState(false);

        }
        /*
         * {@link postDelayed} allows the {@link Runnable} to be run after the specified amount of
         * time set in DELAY_MILLIS elapses. An object that implements the Runnable interface
         * creates a thread. When this thread starts, the object's run method is called.
         *
         * After the time elapses, if there is a callback we return the image resource ID and
         * set the idle state to true.
         */

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dataLoadedCallback != null) {
                    dataLoadedCallback.onDone();
                    if (idlingResource != null) {
                        idlingResource.setIdleState(true);
                    }
                }
            }
        }, DELAY_MILLIS);
    }

}
