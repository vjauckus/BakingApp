package com.android.udacity.vjauckus.mybackingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by veronika on 14.02.18.
 */

public class ListViewService extends RemoteViewsService {

    private static final String TAG = ListViewService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        //Log.v(TAG, "I am in onGetViewsFactory");

        return new ListRemoteViewsFactory(this.getApplicationContext()) ;
    }
}