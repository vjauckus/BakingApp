package com.android.udacity.vjauckus.mybackingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.IdlingResource;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.udacity.vjauckus.mybackingapp.DataDownloader;
import com.android.udacity.vjauckus.mybackingapp.IdlingResource.SimpleIdlingResource;
import com.android.udacity.vjauckus.mybackingapp.R;
import com.android.udacity.vjauckus.mybackingapp.fragments.MasterListFragment;
import com.android.udacity.vjauckus.mybackingapp.models.CakeModel;

import javax.annotation.Nonnull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * This activity is responsible for displaying the master list of all recipes
 * Copyright (C) 2018 VJauckus
 */

public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener,
    DataDownloader.DataLoadedCallback{

    @BindView(R.id.nested_scroll_view_mainActivity) NestedScrollView nestedScrollView;
   private Unbinder unbinder;

   //    Add a SimpleIdlingResource variable that will be null in production
    @Nullable
    private SimpleIdlingResource mSimpleIdlingResource;

    /**
     *  Create a method that returns the IdlingResource variable. It will
     * instantiate a new instance of SimpleIdlingResource if the IdlingResource is null.
     * This method will only be called from test.
     */
    @VisibleForTesting
    @Nonnull
    public IdlingResource getIdlingResource(){
        if (mSimpleIdlingResource == null){
            mSimpleIdlingResource = new SimpleIdlingResource();
        }
        return mSimpleIdlingResource;
    }

    @Override
    protected void onStart() {
        super.onStart();
        DataDownloader.downloadBackingData(this, MainActivity.this, mSimpleIdlingResource);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);

        //only create fragments when there is no previously saved state
        if (savedInstanceState == null){
            //create MasterListFragment

            MasterListFragment masterListFragment = new MasterListFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.master_list_container, masterListFragment)
                    .commit();

        }

    }
    @Override
    public void onImageSelected(CakeModel currentCake){

        Intent intent = new Intent(MainActivity.this, CakeDetailActivity.class);
        Bundle bundle = new Bundle();

       // Log.v(MainActivity.class.getSimpleName(), "The cake was clicked: "+ currentCake.getCakeName());
        bundle.putSerializable("cakeModel", currentCake);

        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public void showSnackBarError() {
        Snackbar.make(nestedScrollView,
                getString(R.string.data_loading_error),
                Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.retry),
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.master_list_container, new MasterListFragment())
                                .commit();
                    }
                }).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
    /**
     * When the thread/ delay in {@link DataDownloader} is finished, it will return the callback's onDone().
     */
    @Override
    public void onDone() {
        if (mSimpleIdlingResource != null){
            mSimpleIdlingResource.setIdleState(true);
        }

    }
}
