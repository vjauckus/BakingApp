package com.android.udacity.vjauckus.mybackingapp.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.udacity.vjauckus.mybackingapp.R;
import com.android.udacity.vjauckus.mybackingapp.adapters.CakeListAdapter;
import com.android.udacity.vjauckus.mybackingapp.models.CakeModel;
import com.android.udacity.vjauckus.mybackingapp.utilities.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * This fragment is responsible for asynchron downloading data from server and displaying the master list
 * Created on 29.01.18.
 * Copyright (C) 2018 VJauckus
 */

public class MasterListFragment extends Fragment implements CakeListAdapter.CakeAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<String>{

    // Define a new interface OnImageClickListener that triggers a callback in the host activity
    private final static String TAG = MasterListFragment.class.getSimpleName();
    private OnImageClickListener mCallback;
    private static final String CAKE_LINK__URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static final String CAKE_URL_EXTRA = "cake";
    private final Type mRecipeListType = new TypeToken<ArrayList<CakeModel>>(){}.getType();
    private final static int CAKE_LOADER = 12;
    private ArrayList<CakeModel> mListCakeModel;

    private CakeListAdapter mCakeListAdapter;

    private int intOrientation;

    @BindView(R.id.cake_recycle__list) RecyclerView mRecyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar mLoadingProgress;

    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
    public interface OnImageClickListener {
        void onImageSelected(CakeModel currentCake);
        void showSnackBarError();
    }



    //Constructor
    public MasterListFragment(){

    }

    // Inflates the GridView of all cake images
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);
        ButterKnife.bind(this,rootView);


        // Create the adapter
        // This adapter takes in the context and an ArrayList of ALL the image resources to display
        mCakeListAdapter = new CakeListAdapter(getContext(), this);
        //If orientation portrait, then is intOrientation = 2, else 3
        intOrientation = checkDeviceOrientation(getContext());

        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(rootView.getContext(),
                intOrientation, GridLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mCakeListAdapter);

         /* This connects our MasterListFragment into the loader lifecycle. */
        LoaderManager loaderManager = getActivity().getSupportLoaderManager();

        if (savedInstanceState == null){

            Bundle recipeBundle = new Bundle();
            recipeBundle.putString(CAKE_URL_EXTRA, CAKE_LINK__URL);
            Loader<String> recipeLoader = loaderManager.getLoader(CAKE_LOADER);

            if (recipeLoader == null){
                loaderManager.initLoader(CAKE_LOADER, recipeBundle, this);
            }
            else {
                loaderManager.restartLoader(CAKE_LOADER, recipeBundle, this);
            }
        }
        else {

            loaderManager.initLoader(CAKE_LOADER, null, this);

        }

        return rootView;

    }


    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnImageClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }
    @Override
    public void onClick(CakeModel currentCake){


        mCallback.onImageSelected(currentCake);
    }


    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {

       // Log.v(TAG, "I am in onCreateLoader");


        return new AsyncTaskLoader<String>(getContext()) {

            String mJsonStringCake;
            @Override
            protected void onStartLoading() {
               if (mJsonStringCake != null){
                   deliverResult(mJsonStringCake);
               }
               else {
                    mLoadingProgress.setVisibility(View.VISIBLE);
                   forceLoad();
               }

            }

            @Override
            public String loadInBackground() {

                        String cakeUrlString = args.getString(CAKE_URL_EXTRA);
                        String searchResults = NetworkUtils.getHttpResponse(cakeUrlString);
                       // Log.v(TAG, "Found: " + searchResults);

                        return searchResults;

            }


            @Override
            public void deliverResult(String data) {
                mJsonStringCake = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        //Not in use here
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        mLoadingProgress.setVisibility(View.GONE);

        if (data != null){
           // loadCakeData();

        mListCakeModel = new Gson().fromJson(data, mRecipeListType);

        mCakeListAdapter.setCakeList(mListCakeModel);

         mRecyclerView.setAdapter(mCakeListAdapter);

        }
        else {
            mCallback.showSnackBarError();
           // Log.v(TAG, "No Cake data are here.");
        }
    }
    //Check device orientation and phone/tablet
    private int checkDeviceOrientation(Context context) {
        intOrientation = context.getResources().getConfiguration().orientation;
        boolean tabletSize = context.getResources().getBoolean(R.bool.isTablet);

        if(intOrientation == Configuration.ORIENTATION_PORTRAIT){
            //  Log.v(TAG, "*******Orientation is portrait");
            if (tabletSize){
                //if it is Tablet
                return 2;
            }
            else{
                //if it is Phone
                return 1;
            }

        }
        else{
            // Log.v(TAG, "*******Orientation is landscape");

            return 2;

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle bundle = new Bundle();

        bundle.putSerializable("cakeModels", mListCakeModel);
        outState.putBundle("newBundle",bundle);
    }
}
