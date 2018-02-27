package com.android.udacity.vjauckus.mybackingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.udacity.vjauckus.mybackingapp.R;
import com.android.udacity.vjauckus.mybackingapp.fragments.IngredientsDetailFragment;
import com.android.udacity.vjauckus.mybackingapp.models.IngredientModel;

import java.util.ArrayList;

/** IngredientDetailActivity
 * This activity is responsible for displaying the ingredients list
 * Created on 06.02.18.
 *  Copyright (C) 2018 VJauckus
 */

public class IngredientDetailActivity extends AppCompatActivity {

    private static final String TAG = IngredientDetailActivity.class.getSimpleName();

   // private ArrayList<IngredientModel> mIngredientsList;


    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ingredient_details);

        Intent intentThatStartThisActivity = getIntent();

        Bundle bundle = intentThatStartThisActivity.getExtras();

        ArrayList<IngredientModel>  IngredientsList = (ArrayList<IngredientModel>) bundle.getSerializable ("ingredients");
       // Log.v(TAG, "My Ingredients size is: "+ mIngredientsList.size());

        IngredientsDetailFragment ingredientsDetailFragment = new IngredientsDetailFragment();
        ingredientsDetailFragment.setIngredientsList(IngredientsList);

        getSupportFragmentManager().beginTransaction()
                        .add(R.id.ingredient_list_container, ingredientsDetailFragment)
                        .commit();


    }


}
