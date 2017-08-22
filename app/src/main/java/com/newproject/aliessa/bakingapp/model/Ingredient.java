package com.newproject.aliessa.bakingapp.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ali Essa on 5/8/2017
 */

public class Ingredient {

    private String ingredient;
    private String measure;
    private double quantity;
    public Ingredient(JSONObject ingredient_jason) {
        try {
            this.ingredient = ingredient_jason.optString("ingredient");
            this.measure = ingredient_jason.optString("measure");
            this.quantity = ingredient_jason.getDouble("quantity");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getIngredient() {

        return ingredient;
    }
    public String getMeasure() {
        return measure;
    }
    public double getQuantity() {
        return quantity;
    }

}
