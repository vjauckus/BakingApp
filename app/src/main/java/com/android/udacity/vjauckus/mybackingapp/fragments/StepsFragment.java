package com.android.udacity.vjauckus.mybackingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.udacity.vjauckus.mybackingapp.R;
import com.android.udacity.vjauckus.mybackingapp.adapters.StepsAdapter;
import com.android.udacity.vjauckus.mybackingapp.models.StepsModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Step Fragment
 * Created on 09.02.18.
 * Copyright (C) 2018 VJauckus
 */

public class StepsFragment extends Fragment implements StepsAdapter.StepAdapterOnClickHandler{

   // private static final String TAG = StepsFragment.class.getSimpleName();

    @BindView(R.id.steps_rv_list)
    RecyclerView mRecyclerViewSteps;

    private ArrayList<StepsModel> mStepsList;
    private int mStepNumber;
    //private StepsAdapter mStepsAdapter;
    public OnStepClickListener mStepCallback;

    public interface OnStepClickListener{
        void onStepItemClicked(ArrayList<StepsModel> stepsModelArrayList, int stepPosition);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mStepCallback = (OnStepClickListener) context;
        }
        catch (ClassCastException ex){
            throw new ClassCastException(context.toString() +
            " must implement OnStepClickListener");
        }
    }

    // Mandatory empty Constructor
    public StepsFragment(){

    }

    /**
     * Inflates the RecyclerView of all backing steps
     * @param inflater LayoutInflater
     * @param container The ViewGroup that these ViewHolders are contained within.
     * @param savedInstanceState Bundle
     * @return rootView
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.steps_list_layout, container, false);
        ButterKnife.bind(this, rootView);
       // Log.v(TAG, "I am in onCreateView()");

        StepsAdapter mStepsAdapter = new StepsAdapter(this);
        mRecyclerViewSteps.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));

        mStepsAdapter.setStepsDataList(mStepsList);
        mRecyclerViewSteps.setNestedScrollingEnabled(false);
        mRecyclerViewSteps.setAdapter(mStepsAdapter);
        if (savedInstanceState != null){
            mStepsList = (ArrayList<StepsModel>) savedInstanceState.getSerializable("steps");
            mStepNumber = savedInstanceState.getInt("stepNumber");
           // Log.v(TAG, mStepsList.toString());
        }

        /*if (mStepsList != null){
            //  mIngredientsAdapter.setIngredientsList(mIngredientsList);
          //  Log.v(TAG, "Adapter ItemCount(): "+mStepsAdapter.getItemCount());
        }
        else {
           // Log.v(TAG, "The StepsList in StepsFragment is empty");
        }
        */
        return rootView;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("layoutState", mRecyclerViewSteps.getLayoutManager().onSaveInstanceState());
        outState.putSerializable("steps", mStepsList);
        outState.putInt("stepNumber", mStepNumber);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    public void setStepsList(ArrayList<StepsModel> stepsModels){
        mStepsList = stepsModels;
    }

    @Override
    public void onClickStep(ArrayList<StepsModel> stepsModelArrayList, int stepPosition) {
        mStepCallback.onStepItemClicked(stepsModelArrayList, stepPosition);
       // mStepNumber = stepPosition;
       // Log.v(TAG, "The Step was clicked: "+stepsModel.getShortDescription());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
