package com.android.udacity.vjauckus.mybackingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.android.udacity.vjauckus.mybackingapp.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by veronika on 16.02.18.
 * demos a user clicking on a RecyclerView item (cake) in MainActivity which opens up the
 * corresponding CakeDetailActivity
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {

    private static final String NUTELLA_CAKE = "Nutella Pie";

    private static final String  BROWNIES = "Brownies";

    private static final String TITLE = "Recipes";

    /**
     * The ActivityTestRule is a rule provided by Android used for functional testing of a single
     * activity. The activity that will be tested, MainActivity in this case, will be launched
     * before each test that's annotated with @Test and before methods annotated with @Before.
     *
     * The activity will be terminated after the test and methods annotated with @After are
     * complete. This rule allows you to directly access the activity during the test.
     */
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    //check the Title
    @Test
    public void cakeTitleCheck(){
        onView((withId(R.id.cake_recipe_list_title))).check(matches(withText(TITLE)));
    }

    @Test
    public void clickRecyclerViewItem_Nutella_OpensCakeDetailActivity(){

        onView(withId(R.id.cake_recycle__list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.tv_detail_title)).check(matches(withText(NUTELLA_CAKE)));

    }

    @Test
    public void clickRecyclerViewItem_Brownies_OpensCakeDetailActivity(){

        onView(withId(R.id.cake_recycle__list)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.tv_detail_title)).check(matches(withText(BROWNIES)));

    }


}
