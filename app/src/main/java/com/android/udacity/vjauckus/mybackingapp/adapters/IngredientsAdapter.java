package com.android.udacity.vjauckus.mybackingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.udacity.vjauckus.mybackingapp.R;
import com.android.udacity.vjauckus.mybackingapp.models.IngredientModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter class for Ingredients used in IngredientsDetailFragment
 * Created on 07.02.18.
 * Copyright (C) 2018 VJauckus
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder> {

    private final static String TAG = IngredientsAdapter.class.getSimpleName();

    private ArrayList<IngredientModel> mIngredientsList;

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        int layoutIdForListItem = R.layout.ingredient_item;
        View view = layoutInflater.inflate(layoutIdForListItem, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        Context context = holder.itemView.getContext();

      //  Log.v(TAG, "I am in onBindViewHolder at position: "+position);
        holder.ingredientTitle.setText(mIngredientsList.get(position).getIngredient());
        holder.measured.setText(mIngredientsList.get(position).getMeasure());
        StringBuilder quantityNumber = new StringBuilder(":  ");
        quantityNumber.append(String.valueOf(mIngredientsList.get(position).getQuantity()));
        quantityNumber.append(" ");
        holder.quantityNumber.setText(quantityNumber.toString());
        holder.quantityText.setText(context.getResources().getString(R.string.quantity_text));

    }

    /**
     * Cache of the children views for a ingredients list item.
     */
   public class IngredientViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredients_title) TextView ingredientTitle;
        @BindView(R.id.quantity_number) TextView quantityNumber;
        @BindView(R.id.ingredient_measured) TextView measured;
        @BindView(R.id.quantity_text) TextView quantityText;
       /**
        * Constructor
        * @param view The view that was clicked
        */

        public IngredientViewHolder(View view){

            super(view);
            ButterKnife.bind(this, view);
        }

    }

    @Override
    public int getItemCount() {

        if (mIngredientsList == null){ return 0;}
        return mIngredientsList.size();
    }

    public void setIngredientsList(ArrayList<IngredientModel> ingredientsList){

        mIngredientsList = ingredientsList;
        notifyDataSetChanged();
    }
}
