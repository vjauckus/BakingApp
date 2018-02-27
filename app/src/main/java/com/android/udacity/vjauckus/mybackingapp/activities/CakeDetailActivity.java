package com.android.udacity.vjauckus.mybackingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.udacity.vjauckus.mybackingapp.R;
import com.android.udacity.vjauckus.mybackingapp.fragments.IngredientsDetailFragment;
import com.android.udacity.vjauckus.mybackingapp.fragments.RecipeIngredientFragment;
import com.android.udacity.vjauckus.mybackingapp.fragments.StepDetailsFragment;
import com.android.udacity.vjauckus.mybackingapp.fragments.StepsFragment;
import com.android.udacity.vjauckus.mybackingapp.models.CakeModel;
import com.android.udacity.vjauckus.mybackingapp.models.IngredientModel;
import com.android.udacity.vjauckus.mybackingapp.models.StepsModel;
import com.android.udacity.vjauckus.mybackingapp.widget.IngredientWidgetService;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * CakeDetailActivity
 * Created on 31.01.18.
 * Copyright (C) 2018 VJauckus
 */

public class CakeDetailActivity extends AppCompatActivity implements RecipeIngredientFragment.OnIngredientsClickListener,
StepsFragment.OnStepClickListener{


    private static final String TAG = CakeDetailActivity.class.getSimpleName();

    private CakeModel mCakeModel;
    private StepsModel mStepsModel;
    public static ArrayList<StepsModel> mStepsList = new ArrayList<>();
    public static ArrayList<IngredientModel> mIngredientsArrayList = new ArrayList<>();
    private Unbinder mUnbinder;
    private boolean mIngredientsClicked = true;
    // private boolean mStepItemClicked = false;
    private boolean mIsTablet;
    public static String mCakeTitle;
    private int mStepNumber = 0;


    @BindView(R.id.tv_detail_title) TextView mDetailTitle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cake_detail_all);
        mUnbinder = ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){

            mCakeModel = (CakeModel) bundle.getSerializable("cakeModel");
            mCakeTitle = mCakeModel.getCakeName();

            mStepsList = mCakeModel.getSteps();
            // mDetailTitle.setText(mCakeModel.getCakeName());
           // Log.v(TAG, "I am in DetailActivity at Cake: "+ mCakeModel.getCakeName());

            mIngredientsArrayList = mCakeModel.getIngredients();

        }
      /*  if(savedInstanceState != null) {
            // mCakeModel = (CakeModel) savedInstanceState.getSerializable("cakeModel");
            mCakeTitle = savedInstanceState.getString("cakeTitle");
         //   mDetailTitle.setText(mCakeModel.getCakeName());
        }*/
        mDetailTitle.setText(mCakeTitle);
        getSupportActionBar().setTitle(mCakeTitle);
        mIsTablet = getResources().getBoolean(R.bool.isTablet);

        FragmentManager fragmentManager = getSupportFragmentManager();

        RecipeIngredientFragment recipeIngredientFragment = new RecipeIngredientFragment();
        recipeIngredientFragment.setIngredientModelArrayList(mIngredientsArrayList);

        if (savedInstanceState == null){
            fragmentManager.beginTransaction()
                    .add(R.id.ingredients_container_button, recipeIngredientFragment)
                    .commit();
        }
        else{
            fragmentManager.beginTransaction()
                    .replace(R.id.ingredients_container_button, recipeIngredientFragment)
                    .commit();
        }

        StepsFragment stepsFragment = new StepsFragment();

        stepsFragment.setStepsList(mStepsList);

        fragmentManager.beginTransaction()
                .add(R.id.steps_list_container, stepsFragment)
                .commit();

        if (mIsTablet){
           // Log.v(TAG, "I am in onCreate(), in Tablet");
            IngredientsDetailFragment ingredientsDetailFragment = new IngredientsDetailFragment();
            ingredientsDetailFragment.setIngredientsList(mIngredientsArrayList);

          //  Log.v(TAG, "My Ingredients: "+ mIngredientsArrayList);
            if (savedInstanceState == null){

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.ingredients_detail_container, ingredientsDetailFragment)
                        .commit();
            }
            else {

                if (savedInstanceState != null){
                    Bundle restoreBundle = savedInstanceState.getBundle("newBundle");
                    mIngredientsClicked = restoreBundle.getBoolean("ingredientsSelected");
                    mCakeTitle = restoreBundle.getString("cake");
                }

                if (mIngredientsClicked){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.ingredients_detail_container, ingredientsDetailFragment)
                            .commit();
                }
                else {
                    StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();

                    if (mStepsList == null || mStepNumber == 0){
                        Bundle bundle1 = savedInstanceState.getBundle("newBundle");
                        mStepsList = (ArrayList<StepsModel>) bundle1.getSerializable("stepsList");
                        mStepNumber =  bundle1.getInt("stepPosition");
                    }
                    Log.v(TAG, "Step position: "+ mStepNumber + ", ArrayList of Steps: "+mStepsList);
                    stepDetailsFragment.setStepsListAndPosition(mStepsList, mStepNumber);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.ingredients_detail_container, stepDetailsFragment)
                            .commit();
                }



            }
        }


    }

    @Override
    public void onIngredientsClicked(ArrayList<IngredientModel> ingredientModels) {

        if (mIsTablet){
            //Ingredients Fragment
            mIngredientsClicked = true;
         //   Log.v(TAG, "I am in Tablet");
            IngredientsDetailFragment ingredientsDetailFragment = new IngredientsDetailFragment();
            ingredientsDetailFragment.setIngredientsList(ingredientModels);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ingredients_detail_container, ingredientsDetailFragment)
                    .commit();

        }
        else {
            //Phone
           // Log.v(TAG, "I am in phone");
            // onClick(mIngredientsButton);
            //Start IngredientDetailActivity
            Intent ingredientsIntent = new Intent( this, IngredientDetailActivity.class);
           // Log.v(TAG, "My Ingredients: "+ mIngredientsArrayList.size()+ " "+mIngredientsArrayList);
            Bundle bundle = new Bundle();

            bundle.putSerializable("ingredients", ingredientModels);
            ingredientsIntent.putExtras( bundle);
            startActivity(ingredientsIntent);

        }

    }

    @Override
    public void onStepItemClicked(ArrayList<StepsModel> stepsModelArrayList, int stepPosition) {
        if (mIsTablet){
            // Fragment
            mIngredientsClicked = false;
           // Log.v(TAG, "I am in Tablet");
            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            stepDetailsFragment.setStepsListAndPosition(stepsModelArrayList, stepPosition);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.ingredients_detail_container, stepDetailsFragment)
                    .commit();


        }
        else {
            //Phone
           // Log.v(TAG, "I am in phone");
            // onClick(mIngredientsButton);
            //Start IngredientDetailActivity
            Intent stepIntent = new Intent( this, StepDetailActivity.class);

            Bundle bundle = new Bundle();

            bundle.putSerializable("stepsList", mStepsList);
            bundle.putInt("stepNumber", stepPosition);
            stepIntent.putExtra(Intent.EXTRA_TEXT, bundle);
            startActivity(stepIntent);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_cake_detail_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.action_add_widget){

            mCakeTitle = mCakeModel.getCakeName();
            mIngredientsArrayList = mCakeModel.getIngredients();
         //   Log.v(TAG, "Menu was clicked, Cake: "+mCakeTitle+ " Ingredients:" +mIngredientsArrayList.toString());
            IngredientWidgetService.startActionUpdateIngredientsList(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle bundle = new Bundle();
       // bundle.putSerializable("stepModel", mStepsModel);
        bundle.putSerializable("stepsList", mStepsList);
        bundle.putInt("stepPosition", mStepNumber);
        //  bundle.putSerializable("cakeModel", mCakeModel);

        outState.putBoolean("ingredientsSelected", mIngredientsClicked);
        outState.putString("cake", mCakeTitle);
        outState.putBundle("newBundle",bundle);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

}
