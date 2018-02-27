package com.android.udacity.vjauckus.mybackingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.android.udacity.vjauckus.mybackingapp.R;

/**
 * Implementation of App Widget functionality.
 * Created by veronika on 14.02.18.
 */

public class IngredientWidgetProvider extends AppWidgetProvider {

    public static final String TAG = IngredientWidgetProvider.class.getSimpleName();

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, String cakeName, int appWidgetId){
       // Log.v(TAG, "I am in updateAppWidget, cake: "+cakeName);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget);
        StringBuilder title = new StringBuilder(cakeName);
        title.append(" - Ingredients");
        remoteViews.setTextViewText(R.id.widget_title_tv, title.toString());
        // Set the ListViewService intent to launch when clicked

        Intent intent = new Intent(context, ListViewService.class);
        remoteViews.setRemoteAdapter(R.id.ingredient_widget_list, intent);
        //Handle empty recipes
        remoteViews.setEmptyView(R.id.ingredient_widget_list, R.id.empty_view);

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }


    public static void updateIngredientsWidget(Context context, AppWidgetManager appWidgetManager, String cakeName, int[] appWidgetIds){

      //  Log.v(TAG, "I am in updateIngredientsWidget");
        for (int appWidgetId : appWidgetIds){
            updateAppWidget(context, appWidgetManager,cakeName, appWidgetId);
        }

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
}
