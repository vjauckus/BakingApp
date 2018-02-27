package com.android.udacity.vjauckus.mybackingapp.models;

import android.os.Parcel;

import java.io.Serializable;

/**
 * Created by veronika on 30.01.18.
 */

public class IngredientModel implements Serializable {

    private float quantity;
    private String measure;
    private String ingredient;


    public IngredientModel(Parcel in){

        this.ingredient = in.readString();
        this.measure = in.toString();
        this.quantity = in.readFloat();
    }


    @Override
    public String toString() {
        StringBuilder ingredientInfo = new StringBuilder("Ingredient: ");

        ingredientInfo.append(this.ingredient);
        ingredientInfo.append(", quantity:");
        ingredientInfo.append(this.quantity);
        ingredientInfo.append(" measured in: ");
        ingredientInfo.append(this.measure);

        return ingredientInfo.toString();
    }

    public String getIngredient(){
        return this.ingredient;
    }
    public String getMeasure(){
        return this.measure;
    }
    public float getQuantity(){
        return this.quantity;
    }




}
