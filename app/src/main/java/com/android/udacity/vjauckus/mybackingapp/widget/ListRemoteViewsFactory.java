package com.android.udacity.vjauckus.mybackingapp.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.android.udacity.vjauckus.mybackingapp.R;
import com.android.udacity.vjauckus.mybackingapp.activities.CakeDetailActivity;
import com.android.udacity.vjauckus.mybackingapp.models.IngredientModel;

import java.util.ArrayList;

/**
 * Created by veronika on 14.02.18.
 */

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

    private static final String TAG = ListRemoteViewsFactory.class.getSimpleName();
    Context mContext;
    private ArrayList<IngredientModel> mIngredientModelArrayList = new ArrayList<>();

    //Constructor

    public ListRemoteViewsFactory(Context appContext){
        mContext = appContext;
    }

    @Override
    public void onCreate() {

    }
    //called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
      //  Log.v(TAG, "I am in OnData Set Changed");
        mIngredientModelArrayList = CakeDetailActivity.mIngredientsArrayList;
    }

    @Override
    public int getCount() {
        if(mIngredientModelArrayList == null) return 0;
        return mIngredientModelArrayList.size();
    }

    @Override
    public int getViewTypeCount() {
        return 1; // Treat all items in ListView the same
    }

    /**
     * This method acts like the onBindViewHolder method in an Adapter
     *
     * @param position The current position of the item in the ListView to be displayed
     * @return The RemoteViews object to display for the provided postion
     */
    @Override
    public RemoteViews getViewAt(int position) {
     //   Log.v(TAG, "I am in getViewAt at position: "+position);
        if (mIngredientModelArrayList == null) {
           // Log.v(TAG,"No Ingredients available.");
            return null;}

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_view_item);
        remoteViews.setTextViewText(R.id.widget_ingredient_title, mIngredientModelArrayList.get(position).getIngredient());
        remoteViews.setTextViewText(R.id.widget_quantity_text, mContext.getResources().getString(R.string.quantity_text));
        StringBuilder quantity_value = new StringBuilder("  ");
        quantity_value.append(mIngredientModelArrayList.get(position).getQuantity());
        quantity_value.append(" ");
        remoteViews.setTextViewText(R.id.widget_quantity_number, quantity_value.toString());
        remoteViews.setTextViewText(R.id.widget_ingredient_measured, mIngredientModelArrayList.get(position).getMeasure());
        // Always hide the emptyView in ListView mode
    //    remoteViews.setViewVisibility(R.id.empty_view, View.GONE);

        return remoteViews;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public void onDestroy() {

    }
}
