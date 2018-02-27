package com.android.udacity.vjauckus.mybackingapp.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by veronika on 30.01.18.
 * defines the Cake as object with fields
 */

public class CakeModel implements Serializable {

   // private int id;
    private String image;
    private String name;
    private ArrayList<IngredientModel> ingredients;
    private ArrayList<StepsModel> steps;
   // private int servings;


    public String getCakeImage(){
        return this.image;
    }
    public String getCakeName(){
        return this.name;
    }

    public ArrayList<IngredientModel> getIngredients() {
        return this.ingredients;
    }
    public ArrayList<StepsModel> getSteps() {
        return this.steps;
    }


  /*  public void setImage(String image){
        this.name = image;
    }
*/
   /* public void setId(int id){
        this.id = id;
    }
    */

    public void setIngredients(ArrayList <IngredientModel> cakeIngredients){
        this.ingredients = cakeIngredients;

    }

    @Override
    public String toString() {
        return this.name;
    }

}
