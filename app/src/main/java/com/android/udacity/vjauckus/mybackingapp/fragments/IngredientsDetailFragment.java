package com.android.udacity.vjauckus.mybackingapp.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.udacity.vjauckus.mybackingapp.R;
import com.android.udacity.vjauckus.mybackingapp.adapters.IngredientsAdapter;
import com.android.udacity.vjauckus.mybackingapp.models.IngredientModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fragment for Ingredients detail
 * Created on 06.02.18.
 *  Copyright (C) 2018 VJauckus
 */

public class IngredientsDetailFragment extends Fragment {

    private static final String TAG = IngredientsDetailFragment.class.getSimpleName();

    @BindView(R.id.ingredients_rv_list)
    RecyclerView mRecyclerIngredients;

    private ArrayList<IngredientModel> mIngredientsList;
   // private IngredientsAdapter mIngredientsAdapter;

    // Mandatory empty Constructor
    public IngredientsDetailFragment(){

    }

    /**
     *  Inflates the details of ingredients
     * @param inflater LayoutInflater
     * @param container The ViewGroup that these ViewHolders are contained within.
     * @param savedInstanceState Bundle
     * @return rootView
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.ingredients_list, container, false);
        ButterKnife.bind(this, rootView);
      //  Log.v(TAG, "I am in onCreateView()");

        IngredientsAdapter mIngredientsAdapter = new IngredientsAdapter();
        mRecyclerIngredients.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false));


        mRecyclerIngredients.setNestedScrollingEnabled(false);
        if (savedInstanceState != null){
           mIngredientsList = (ArrayList<IngredientModel>) savedInstanceState.getSerializable("ingredients");
          // Log.v(TAG, mIngredientsList.toString());
        }
        mIngredientsAdapter.setIngredientsList(mIngredientsList);
        mRecyclerIngredients.setAdapter(mIngredientsAdapter);

      /*  if (mIngredientsList != null){
          //  mIngredientsAdapter.setIngredientsList(mIngredientsList);
           // Log.v(TAG, "Adapter ItemCount(): "+mIngredientsAdapter.getItemCount());
        }
       else {
          //  Log.v(TAG, "The ingredientsList in IngredientsDetailFragment is empty");
        }
        */
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putParcelable("layoutState", mRecyclerIngredients.getLayoutManager().onSaveInstanceState());
        outState.putSerializable("ingredients", mIngredientsList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {

        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null){
            Parcelable saveRecyclerState = savedInstanceState.getParcelable("layoutState");
            mIngredientsList = (ArrayList<IngredientModel>) savedInstanceState.getSerializable("ingredients");
            mRecyclerIngredients.getLayoutManager().onRestoreInstanceState(saveRecyclerState);

        }
    }

    public void setIngredientsList(ArrayList<IngredientModel> ingredientsList){
        mIngredientsList = ingredientsList;

    }

}
