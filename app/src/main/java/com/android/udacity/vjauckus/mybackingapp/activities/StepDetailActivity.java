package com.android.udacity.vjauckus.mybackingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.udacity.vjauckus.mybackingapp.R;
import com.android.udacity.vjauckus.mybackingapp.fragments.StepDetailsFragment;
import com.android.udacity.vjauckus.mybackingapp.models.StepsModel;

import java.util.ArrayList;

/**
 * This activity is responsible for displaying the cake details (ingredients, backing steps)
 * Created by veronika on 12.02.18.
 *  Copyright (C) 2018 VJauckus
 */

public class StepDetailActivity extends AppCompatActivity {
   // private static final String TAG = StepDetailActivity.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        if (savedInstanceState == null){
            Intent intentThatStartThisActivity = getIntent();

            Bundle bundle = intentThatStartThisActivity.getBundleExtra(Intent.EXTRA_TEXT);

            ArrayList<StepsModel> stepsModelArrayList = (ArrayList<StepsModel>) bundle.getSerializable("stepsList");
            int stepNumber = bundle.getInt("stepNumber");

            //StepsModel stepsModel = (StepsModel) bundle.getSerializable("step");

            StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
            //stepDetailsFragment.setStepModel(stepsModel);
            stepDetailsFragment.setStepsListAndPosition(stepsModelArrayList, stepNumber);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.step_detail_container, stepDetailsFragment)
                    .commit();

        }
    }
}
