package com.android.udacity.vjauckus.mybackingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.udacity.vjauckus.mybackingapp.R;
import com.android.udacity.vjauckus.mybackingapp.models.IngredientModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *  Recipe Ingredient Fragment
 *  Created on 07.02.18.
 *  Copyright (C) 2018 VJauckus
 */

public class RecipeIngredientFragment extends Fragment {

    @BindView(R.id.ingredients_button)
    TextView mIngredientsButton;

    private OnIngredientsClickListener mIngredientCallback;

    private ArrayList<IngredientModel> mIngredientModelArrayList;


    public interface OnIngredientsClickListener{
        void onIngredientsClicked(ArrayList<IngredientModel> ingredientModels);
    }

    //empty Constructor
    public RecipeIngredientFragment(){

    }

    /**
     *  Inflates the RecyclerView of all ingredients
     * @param inflater LayoutInflater
     * @param container The ViewGroup that these ViewHolders are contained within.
     * @param savedInstanceState Bundle
     * @return rootView
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_ingredient, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mIngredientCallback = (OnIngredientsClickListener) context;
        } catch (ClassCastException ex){
            throw new ClassCastException(context.toString() + " must implement OnIngredientsClickListener");
        }
    }

    public void setIngredientModelArrayList(ArrayList<IngredientModel> ingredientModels){
        mIngredientModelArrayList = ingredientModels;

    }

    @OnClick(R.id.ingredients_container_button)

    void onClick(){
        mIngredientCallback.onIngredientsClicked(mIngredientModelArrayList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
