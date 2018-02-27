package com.android.udacity.vjauckus.mybackingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.android.udacity.vjauckus.mybackingapp.activities.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by veronika on 19.02.18.
 *  * Usually Espresso syncs all view operations with the UI thread as well as AsyncTasks, but it can't
 * do so with custom resources (e.g. activity or service). For such cases, we can register the
 * custom resource and Espresso will wait for the resource to be idle before
 * executing a view operation.
 * This is to simulate potential
 * delay that could happen if this data were being retrieved from the web.
 */

@RunWith(AndroidJUnit4.class)
public class IdlingResourceMainActivityTest {
    /**
     * The ActivityTestRule is a rule provided by Android used for functional testing of a single
     * activity. The activity that will be tested, MenuActivity in this case, will be launched
     * before each test that's annotated with @Test and before methods annotated with @Before.
     *
     * The activity will be terminated after the test and methods annotated with @After are
     * complete. This rule allows us to directly access the activity during the test.
     */
    @Rule
    public ActivityTestRule<MainActivity> mMainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;

    //Registers any resource that needs to be synchronized with Espresso before the test is run.
    @Before
    public void registerIdlingResource(){

        mIdlingResource = mMainActivityActivityTestRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        Espresso.registerIdlingResources(mIdlingResource);
    }
    // Test that the RecyclerView with Recipe objects appears and we can click an item
    @Test
    public void idlingResourceTest(){
        onView(withId(R.id.cake_recycle__list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }
    // DONE Unregister resources when not needed to avoid malfunction
    @After
    public void unregisterIdlingResource(){

        if(mIdlingResource != null){
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }

}

