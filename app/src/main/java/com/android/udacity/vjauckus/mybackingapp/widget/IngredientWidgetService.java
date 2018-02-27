package com.android.udacity.vjauckus.mybackingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.android.udacity.vjauckus.mybackingapp.R;
import com.android.udacity.vjauckus.mybackingapp.activities.CakeDetailActivity;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 *  Created by veronika on 14.02.18.
 */
public class IngredientWidgetService extends IntentService {

    private static final String TAG = IngredientWidgetService.class.getSimpleName();
    public static final String ACTION_UPDATE_INGREDIENTS = "com.android.udacity.vjauckus.mybackingapp.widget.action.update_ingredients";

    //Constructor
    public IngredientWidgetService(){

        super("IngredientWidgetService");
    }
    /**
     * Starts this service to perform UpdateIngredientsList action with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionUpdateIngredientsList(Context context){
      //  Log.v(TAG, "I am starting Update Ingredients.");
        Intent intent = new Intent(context, IngredientWidgetService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENTS);
        context.startService(intent);

    }

    /**
     *
     * @param intent Handle intent to update ingredients
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
       // Log.v(TAG, "I am onHandle Intent.");
        if (intent != null){
            final String action = intent.getAction();
            if (ACTION_UPDATE_INGREDIENTS.equals(action)){
                handleActionUpdateIngredients();
            }
        }

    }
    /**
     * Handle action Update Ingredients ArrayList in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUpdateIngredients(){
       // Log.v(TAG, "I am in handleAction Update Ingredients." );

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new
                ComponentName(this, IngredientWidgetProvider.class));
        //Trigger data update to handle ListView widgets and force data refresh
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.ingredient_widget_list);
        IngredientWidgetProvider.updateIngredientsWidget(this, appWidgetManager, CakeDetailActivity.mCakeTitle, appWidgetIds);
    }
}
